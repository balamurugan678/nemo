package com.novacroft.nemo.mock_cubic.domain.card;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

import javax.persistence.*;

/**
 * Store the cubic card response.
 */
@Entity
@Table(name = "MOCK_CUBIC_CARD_RESPONSE")
public class CubicCardResponse extends AbstractBaseEntity {
    @Transient
    private static final long serialVersionUID = 8334719412237128078L;
    protected String prestigeId;
    protected String action;
    protected String response;

    public CubicCardResponse() {
        super();
    }

    @SequenceGenerator(name = "CUBIC_CARD_RESPONSE_SEQ", sequenceName = "MOCK_CUBIC_CARD_RESPONSE_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUBIC_CARD_RESPONSE_SEQ")
    @Column(name = "ID")
    @Override
    public final Long getId() {
        return id;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(final String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public String getResponse() {
        return response;
    }
    public void setResponse(final String response) {
        this.response = response;
    }
}
