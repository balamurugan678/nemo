package com.novacroft.nemo.common.application_service.impl;

import com.novacroft.nemo.common.application_service.UIDGenerator;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Unique identifier generator implementation
 */
@Service(value = "uidGenerator")
public class UIDGeneratorImpl implements UIDGenerator {
    @Override
    public UUID getId() {
        return UUID.randomUUID();
    }

    @Override
    public String getIdAsString() {
        return getId().toString();
    }
}
