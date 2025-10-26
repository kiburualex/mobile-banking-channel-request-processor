package io.firogence.mobile_banking_channel_request_processor.dtos.transfer;

import io.firogence.mobile_banking_channel_request_processor.configurations.validators.account.ValidAccount;
import io.firogence.mobile_banking_channel_request_processor.configurations.validators.channel.ValidChannel;
import io.firogence.mobile_banking_channel_request_processor.configurations.validators.reference.ValidReference;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * @author Alex Kiburu
 */
public record InternalTransferRequest(
        @ValidAccount(message = "Account Number is mandatory")
        String account,

        @ValidChannel(message = "Channel is mandatory")
        String channel,

        @ValidAccount(message = "Beneficiary Account is mandatory")
        String beneficiaryAccount,

        @NotNull(message = "Amount is mandatory")
        @DecimalMin(value = "0.0", message = "Amount must be zero or positive")
        BigDecimal amount,

        @ValidReference(message = "Reference is mandatory")
        String reference
) {
}
