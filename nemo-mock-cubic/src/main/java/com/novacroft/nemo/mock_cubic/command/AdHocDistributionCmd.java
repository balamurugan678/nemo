package com.novacroft.nemo.mock_cubic.command;

import java.util.List;

import com.novacroft.nemo.mock_cubic.domain.AdHocDistribution;

/**
 * Command file for Ad Hoc Distribution file.
 */
public class AdHocDistributionCmd {
    protected transient List<AdHocDistribution> currentActions;

    public final List<AdHocDistribution> getAdHocDistributions() {
        return currentActions;
    }

    public final void setAdHocDistributions(final List<AdHocDistribution> newCurrentActions) {
        this.currentActions = newCurrentActions;
    }
}
