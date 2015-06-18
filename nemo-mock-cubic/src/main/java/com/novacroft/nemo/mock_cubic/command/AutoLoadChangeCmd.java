package com.novacroft.nemo.mock_cubic.command;

import com.novacroft.nemo.mock_cubic.domain.AutoLoadChange;

import java.util.List;

/**
 * Command file for Auto Load Change file.
 */
public class AutoLoadChangeCmd {
    protected transient List<AutoLoadChange> currentActions;

    public final List<AutoLoadChange> getAutoLoadChanges() {
        return currentActions;
    }

    public final void setAutoLoadChanges(final List<AutoLoadChange> newCurrentActions) {
        this.currentActions = newCurrentActions;
    }
}
