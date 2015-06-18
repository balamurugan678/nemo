package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * TfL auto top-up item single table inheritance domain definition
 */
@Entity
@DiscriminatorValue("AutoTopUpPerformed")
public class AutoTopUpPerformedSettlement extends Settlement {
    private static final long serialVersionUID = 5496715858643282983L;

    protected Integer autoLoadState;
   
    public Integer getAutoLoadState() {
        return autoLoadState;
    }

    public void setAutoLoadState(Integer autoLoadState) {
        this.autoLoadState = autoLoadState;
    }


}
