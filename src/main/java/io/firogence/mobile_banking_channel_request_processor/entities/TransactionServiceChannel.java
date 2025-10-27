package io.firogence.mobile_banking_channel_request_processor.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Alex Kiburu
 */

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_service_channel")
public class TransactionServiceChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_service_id")
    private TransactionServiceEntity transactionService;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
}
