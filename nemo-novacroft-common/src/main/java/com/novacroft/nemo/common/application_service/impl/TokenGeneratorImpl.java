package com.novacroft.nemo.common.application_service.impl;

import com.novacroft.nemo.common.application_service.TokenGenerator;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * <p>Token generator implementation</p>
 * <p>This class provides facilities for generating "tokens", i.e. random sequences of characters. A password salt is an example
 * token use case.</p>
 * <p>Tokens may be "raw" or "encoded".  Raw tokens may include non-printable characters.  Encoded tokens are base-64 encoded,
 * i.e. they only contain printable characters, or URL safe base-64 encoded, i.e. only contain printable characters that are
 * safe to use in a URL.</p>
 */
@Service("tokenGenerator")
public class TokenGeneratorImpl implements TokenGenerator {
    @Override
    public String createToken(int tokenLength) {
        return encodeToken(createRawToken(tokenLength));
    }

    @Override
    public String createUrlSafeToken(int tokenLength) {
        return urlSafeEncodeToken(createRawToken(tokenLength));
    }

    @Override
    public String createSalt(int saltLength) {
        return createRawToken(saltLength).toString();
    }

    protected byte[] createRawToken(int tokenLength) {
        SecureRandom random = new SecureRandom();
        byte[] rawToken = new byte[tokenLength];
        random.nextBytes(rawToken);
        return rawToken;
    }

    protected String encodeToken(byte[] rawToken) {
        return Base64.encodeBase64String(rawToken);
    }

    protected String urlSafeEncodeToken(byte[] rawToken) {
        return Base64.encodeBase64URLSafeString(rawToken);
    }
}
