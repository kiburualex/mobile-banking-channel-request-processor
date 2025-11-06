package io.firogence.mobile_banking_channel_request_processor.entities;

import io.firogence.mobile_banking_channel_request_processor.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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
@Table(name = "account_types")
public class AccountType extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "account_code")
    private String accountCode;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "requires_minimum_deposit", columnDefinition = "boolean default false")
    private boolean requiresMinimumDeposit;

    @Column(name = "minimum_deposit")
    private BigDecimal minimumDeposit;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

    @Column(name = "deposit_allowed", columnDefinition = "boolean default true")
    private boolean depositAllowed;

    @Column(name = "withdrawal_allowed", columnDefinition = "boolean default true")
    private boolean withdrawalAllowed;

    @Lob
    @Column(name = "mandates")
    private String mandates;

    @ManyToMany
    @JoinTable(
            name = "account_type_channels",
            joinColumns = @JoinColumn(name = "account_type_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    private Set<Channel> channels;
}
