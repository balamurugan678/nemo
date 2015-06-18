package com.novacroft.nemo.mock_cubic.command;

import java.util.List;

import com.novacroft.nemo.mock_cubic.domain.CurrentAction;

/**
 * Command file for Current Action List file.
 */
public class CurrentActionListFileCmd {
    protected List<CurrentAction> currentActions;

    public final List<CurrentAction> getCurrentActions() {
        return currentActions;
    }

    public final void setCurrentActions(final List<CurrentAction> currentActions) {
        this.currentActions = currentActions;
    }
}
