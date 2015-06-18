package com.novacroft.nemo.common.application_service;

/**
 * Token generator specification
 */
public interface TokenGenerator {
    String createToken(int tokenLength);

    String createUrlSafeToken(int tokenLength);

    String createSalt(int saltLength);
}
