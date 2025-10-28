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
@NoArgsConstructor
@AllArgsConstructor
public class ChannelLimit {
    private String channel;
    private BigDecimal limitPerTransaction = new BigDecimal(0);
    private BigDecimal dailyTransactionLimit = new BigDecimal(0);
    private boolean validPerTransactionLimit = false;
    private boolean validDailyTransactionLimit = false;
    private boolean hasLimit = false;
}
