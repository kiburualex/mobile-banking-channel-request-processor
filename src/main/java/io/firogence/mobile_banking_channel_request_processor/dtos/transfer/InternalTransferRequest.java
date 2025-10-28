package io.firogence.mobile_banking_channel_request_processor.dtos.transfer;

import io.firogence.mobile_banking_channel_request_processor.configurations.validators.account.ValidAccount;
import io.firogence.mobile_banking_channel_request_processor.configurations.validators.account.account_customer.ValidAccountCustomer;
import io.firogence.mobile_banking_channel_request_processor.configurations.validators.reference.ValidReference;
import io.firogence.mobile_banking_channel_request_processor.configurations.validators.service.service_channel.ValidServiceAndChannel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * @author Alex Kiburu
 */
@ValidServiceAndChannel(
        serviceCodeField = "serviceCode",
        channelField = "channel"
)

@ValidAccountCustomer(
        accountField = "account",
        nationalIdField = "nationalId"
)

public record InternalTransferRequest(
        @NotBlank(message = "Customer National is mandatory")
        String account,

        @NotBlank(message = "Customer National is mandatory")
        String nationalId,

        @NotBlank(message = "Channel is mandatory")
        String channel,

        @NotBlank(message = "Service Code is mandatory")
        String serviceCode,

        @ValidAccount(message = "Beneficiary Account is mandatory")
        String beneficiaryAccount,

        @NotNull(message = "Amount is mandatory")
        @DecimalMin(value = "5.0", message = "Amount must be minimum of 5 or higher")
        BigDecimal amount,

        @ValidReference(message = "Reference is mandatory")
        String reference
) {
}
