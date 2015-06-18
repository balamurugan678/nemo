package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.CommonContact;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * TfL contact implementation
 */

@Audited
@Entity
public class Contact extends CommonContact {
    @Transient
    private static final long serialVersionUID = 3440125307047430710L;

    public Contact() {
        super();
    }

    @SequenceGenerator(name = "CONTACT_SEQ", sequenceName = "CONTACT_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

}
