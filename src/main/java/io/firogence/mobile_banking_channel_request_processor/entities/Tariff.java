package io.firogence.mobile_banking_channel_request_processor.entities;

import io.firogence.mobile_banking_channel_request_processor.common.BaseEntity;
import io.firogence.mobile_banking_channel_request_processor.enums.ExpenseType;
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
@Table(name = "tariffs")
public class Tariff extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "transaction_service_id", referencedColumnName = "id")
    private TransactionServiceEntity transactionService;

    @Lob
    @Column(name = "range_data")
    private String rangeData;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_type", nullable = true)
    private ExpenseType expenseType;
}
