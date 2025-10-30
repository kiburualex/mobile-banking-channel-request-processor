package io.firogence.mobile_banking_channel_request_processor.services.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.dtos.charge.ChargeRequest;
import io.firogence.mobile_banking_channel_request_processor.entities.Tariff;
import io.firogence.mobile_banking_channel_request_processor.entities.TransactionServiceEntity;
import io.firogence.mobile_banking_channel_request_processor.enums.ExpenseType;
import io.firogence.mobile_banking_channel_request_processor.repositories.TransactionServiceRepository;
import io.firogence.mobile_banking_channel_request_processor.services.ChargeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.StreamSupport;

/**
 * @author Alex Kiburu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChargeImpl implements ChargeService {
    private final TransactionServiceRepository transactionServiceRepository;
    private final Gson gson = new Gson();


    @Override
    public GenericResponse fetchCharges(ChargeRequest request) {
        // fetch charge by service
        Optional<TransactionServiceEntity> transactionServiceOptional = transactionServiceRepository.findByCodeAndChannels_Name(request.serviceCode(), request.channel());
        if(transactionServiceOptional.isEmpty())
            throw new NoSuchElementException("Service not found");

        TransactionServiceEntity transactionService = transactionServiceOptional.get();
        if(!transactionService.isApplyCharge() || transactionService.getChargesData() == null || transactionService.getChargesData().isEmpty())
            throw new NoSuchElementException("No charge set for service : " + transactionService.getName());

        // calculate charge if expense type is CUSTOMER_EXPENSE
        var chargeAmount = BigDecimal.ZERO;
        if(transactionService.getExpenseType().equals(ExpenseType.CUSTOMER_EXPENSE)){
            try {
                var chargesData = transactionService.getChargesData();
                JsonArray chargesDataArray = gson.fromJson(chargesData, JsonArray.class);
                chargeAmount = calculateCharge(request.channel(), request.amount(), chargesDataArray);
            } catch (Exception e) {
                log.error(String.format("Error calculating charge for [%s] [%s] [%s]::", request.serviceCode(), request.channel(), request.amount()), e);
            }
        }

        // calculate tariff based on amount
        var tariffAmount = BigDecimal.ZERO;
        if(transactionService.getTariff() != null){
            Tariff tariff = transactionService.getTariff();
            if(tariff.isActive()){
                try {
                    var tariffRangeData = tariff.getRangeData();
                    JsonArray tariffDataArray = gson.fromJson(tariffRangeData, JsonArray.class);
                    tariffAmount = getTariffByAmount(request.amount(), tariffDataArray);
                } catch (Exception e) {
                    log.error(String.format("Error calculating tariff for %s %s %s::", request.serviceCode(), request.channel(), request.amount()), e);
                }
            }
        }

        var totalCharge = chargeAmount.add(tariffAmount);
        Map<Object, Object> dataObject = new HashMap<>();
        dataObject.put("totalCharge", totalCharge);
        dataObject.put("charge", chargeAmount);
        dataObject.put("tariff", tariffAmount);
        return GenericResponse.builder()
                .status("00")
                .message("success")
                .data(dataObject)
                .build();
    }

    // calculate charge
    public BigDecimal calculateCharge(String channel, BigDecimal amount, JsonArray configArray) {
        // find the configuration object matching the input channel
        for (JsonElement element : configArray) {
            JsonObject configObject = element.getAsJsonObject();
            JsonArray channelsArray = configObject.getAsJsonArray("channels");
            // convert JsonArray of strings to a Java List<String> for easy checking
            List<String> validChannels = StreamSupport.stream(channelsArray.spliterator(), false)
                    .map(JsonElement::getAsString)
                    .toList();

            if (validChannels.contains(channel)) {
                // channel match found, now calculate the charge
                JsonObject chargeConfig = configObject.getAsJsonObject("chargeConfig");
                String chargeType = chargeConfig.getAsJsonPrimitive("chargeType").getAsString();
                return calculateChargeByConfig(chargeType, chargeConfig, amount);
            }
        }

        log.info("No charge configuration found for channel: {}", channel);
        return BigDecimal.ZERO;
    }

    // calculate charge by configuration
    private BigDecimal calculateChargeByConfig(String chargeType, JsonObject chargeConfig, BigDecimal amount) {
        final BigDecimal HUNDRED = new BigDecimal("100");
        switch (chargeType) {
            case "fixed":
                return chargeConfig.getAsJsonPrimitive("amount").getAsBigDecimal();
            case "percentage":
                BigDecimal percentageRate = chargeConfig.getAsJsonPrimitive("amount").getAsBigDecimal();
                return amount.multiply(percentageRate).divide(HUNDRED, 2, java.math.RoundingMode.HALF_UP);
            case "range":
                JsonArray rangeRows = chargeConfig.getAsJsonArray("rangeRows");
                for (JsonElement rowElement : rangeRows) {
                    JsonObject row = rowElement.getAsJsonObject();
                    BigDecimal minAmount = row.getAsJsonPrimitive("minAmount").getAsBigDecimal();
                    BigDecimal maxAmount = row.getAsJsonPrimitive("maxAmount").getAsBigDecimal();

                    // check if the input amount falls within the range (inclusive of min, inclusive of max for simplicity)
                    if (amount.compareTo(minAmount) >= 0 && amount.compareTo(maxAmount) <= 0) {
                        String type = row.getAsJsonPrimitive("type").getAsString();
                        BigDecimal fee = row.getAsJsonPrimitive("amount").getAsBigDecimal();
                        if ("fixed".equals(type)) {
                            return fee;
                        } else if ("percentage".equals(type)) {
                            // percentage charge within range (e.g., 2% for amount 150)
                            return amount.multiply(fee).divide(HUNDRED, 2, java.math.RoundingMode.HALF_UP);
                        }
                    }
                }
                break;

            default:
                log.info("Unknown charge type: {}", chargeType);
                return BigDecimal.ZERO;
        }

        // fallback for range without a match
        return BigDecimal.ZERO;
    }


    public BigDecimal getTariffByAmount(BigDecimal amount, JsonArray configArray) {
        for (JsonElement element : configArray) {
            JsonObject tariffRange = element.getAsJsonObject();
            BigDecimal min = tariffRange.getAsJsonPrimitive("min").getAsBigDecimal();
            BigDecimal max = tariffRange.getAsJsonPrimitive("max").getAsBigDecimal();
            BigDecimal value = tariffRange.getAsJsonPrimitive("value").getAsBigDecimal();
            // check if amount is >= min AND amount <= max (inclusive range)
            if (amount.compareTo(min) >= 0 && amount.compareTo(max) <= 0) {
                // match found
                return value;
            }
        }

        // return default 0
        return BigDecimal.ZERO;
    }
}
