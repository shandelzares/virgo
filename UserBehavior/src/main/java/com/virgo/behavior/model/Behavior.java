package com.virgo.behavior.model;


import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Behavior {
    @Id
    private Long id;

}
