package com.novacroft.nemo.tfl.common.command;

/**
 * PickUp Location command specification
 */
public interface PickUpLocationCmd extends SelectStationCmd {
    Long getCardId();
}
