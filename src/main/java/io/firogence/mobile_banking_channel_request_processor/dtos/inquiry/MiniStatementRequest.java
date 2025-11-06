package io.firogence.mobile_banking_channel_request_processor.dtos.inquiry;

import io.firogence.mobile_banking_channel_request_processor.configurations.validators.account.account_customer.ValidAccountCustomer;
import io.firogence.mobile_banking_channel_request_processor.configurations.validators.reference.ValidReference;
import io.firogence.mobile_banking_channel_request_processor.configurations.validators.service.service_channel.ValidServiceAndChannel;
import jakarta.validation.constraints.NotBlank;

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

public record MiniStatementRequest(
        @NotBlank(message = "Customer National is mandatory")
        String account,

        @NotBlank(message = "Customer National is mandatory")
        String nationalId,

        @NotBlank(message = "Channel is mandatory")
        String channel,

        @NotBlank(message = "Service Code is mandatory")
        String serviceCode,

        @ValidReference(message = "Reference is mandatory")
        String reference,

        @NotBlank(message = "Start Date is mandatory")
        String startDate,

        @NotBlank(message = "End Date is mandatory")
        String endDate
) {
}
