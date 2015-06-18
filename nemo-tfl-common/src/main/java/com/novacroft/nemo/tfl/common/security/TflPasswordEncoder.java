package com.novacroft.nemo.tfl.common.security;

import com.novacroft.nemo.common.exception.ApplicationSecurityException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * TfL password hash implementation
 */
@Component(value = "passwordEncoder")
public class TflPasswordEncoder implements PasswordEncoder {
    protected static final Logger logger = LoggerFactory.getLogger(TflPasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationSecurityException(e.getMessage(), e);
        }
        byte[] rawPasswordAsBytes = rawPassword.toString().getBytes();
        byte[] hashedPassword = digest.digest(rawPasswordAsBytes);
        return Base64.encodeBase64String(hashedPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
