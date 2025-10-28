package io.firogence.mobile_banking_channel_request_processor.dtos.charge;

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
public record ChargeRequest(
        @NotBlank(message = "Channel is mandatory")
        String channel,

        @NotBlank(message = "Service Code is mandatory")
        String serviceCode,

        @NotNull(message = "Amount is mandatory")
        @DecimalMin(value = "0.0", message = "Amount must be minimum of 5 or higher")
        BigDecimal amount
) {
}
