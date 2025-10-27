package io.firogence.mobile_banking_channel_request_processor.dtos.transfer;

import io.firogence.mobile_banking_channel_request_processor.configurations.validators.account.ValidAccount;
import io.firogence.mobile_banking_channel_request_processor.configurations.validators.reference.ValidReference;
import io.firogence.mobile_banking_channel_request_processor.configurations.validators.service.ValidServiceAndChannel;
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
public record InternalTransferRequest(
        @ValidAccount(message = "Account Number is mandatory")
        String account,

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
