package com.novacroft.nemo.common.application_service;

import java.util.UUID;

/**
 * Unique identifier generator specification
 */
public interface UIDGenerator {
    UUID getId();

    String getIdAsString();
}
