package io.firogence.mobile_banking_channel_request_processor.entities;

import io.firogence.mobile_banking_channel_request_processor.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "channels")
public class Channel extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

    @Column(name = "immutable", columnDefinition = "boolean default false")
    private boolean immutable;

}
