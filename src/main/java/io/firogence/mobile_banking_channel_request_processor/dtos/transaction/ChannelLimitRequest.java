package io.firogence.mobile_banking_channel_request_processor.dtos.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Alex Kiburu
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelLimitRequest {
    private String limitsJson;
    private String channel;
    private String customerNationalId;
    private String serviceCode;
    private BigDecimal amount;
}
