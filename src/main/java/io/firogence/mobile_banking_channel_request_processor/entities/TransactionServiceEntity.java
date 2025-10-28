package io.firogence.mobile_banking_channel_request_processor.entities;

import io.firogence.mobile_banking_channel_request_processor.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * @author Alex Kiburu
 */

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_services")
public class TransactionServiceEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "cbs_account_number")
    private String cbsAccountNumber;

    @Column(name = "cbs_agency_code")
    private String cbsAgencyCode;

    @Column(name = "cbs_terminal_id")
    private String cbsTerminalId;

    @Column(name = "cbs_user")
    private String cbsUser;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

    @Column(name = "async", columnDefinition = "boolean default false")
    private boolean async;

    @Column(name = "online", columnDefinition = "boolean default false")
    private boolean online;

    @Column(name = "apply_limit", columnDefinition = "boolean default false")
    private boolean applyLimit;

    @Column(name = "apply_commission", columnDefinition = "boolean default false")
    private boolean applyCommission;

    @Lob
    @Column(name = "limits_data")
    private String limitsData;

    @Lob
    @Column(name = "charges_data")
    private String chargesData;

    @Lob
    @Column(name = "commissions_data")
    private String commissionsData;

    // Defines the inverse side of the relationship.
    // 'mappedBy' references the 'service' field in the Tariff entity.
    @OneToOne(mappedBy = "transactionService")
    private Tariff tariff;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "transaction_service_channel",
            joinColumns = @JoinColumn(name = "transaction_service_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    private Set<Channel> channels; // <-- The field name used for 'mappedBy' in Channel
}
