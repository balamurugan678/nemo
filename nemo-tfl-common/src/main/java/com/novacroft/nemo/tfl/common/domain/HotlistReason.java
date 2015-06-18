package com.novacroft.nemo.tfl.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

@Entity
@Table(name="hotlistReason")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class HotlistReason extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = -1223339682112632943L;
    private String description;
    
    @Id
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="description")
    public String getDescription() { return description; }
    
}
