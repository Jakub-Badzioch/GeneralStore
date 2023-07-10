package com.general.store.model.dao.impl;

import com.general.store.model.dao.Basic;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name = "idx_name", columnList = "name", unique = true))
public class Template extends Basic {
    private String name;
    private String subject;
    @Lob
    private String body;
}
