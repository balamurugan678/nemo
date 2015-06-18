package com.novacroft.nemo.mock_cubic.command;

import com.novacroft.nemo.mock_cubic.domain.AutoLoadPerformed;

import java.util.List;

/**
 * Command file for Auto Load Performed file.
 */
public class AutoLoadPerformedCmd {
    protected List<AutoLoadPerformed> autoLoadsPerformed;

    public List<AutoLoadPerformed> getAutoLoadsPerformed() {
        return autoLoadsPerformed;
    }

    public void setAutoLoadsPerformed(List<AutoLoadPerformed> autoLoadsPerformed) {
        this.autoLoadsPerformed = autoLoadsPerformed;
    }
}
