package com.novacroft.nemo.tfl.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring security authentication provider implementation for TfL.
 * <p/>
 * TODO: change this spurious implementation to fit TfL requirements
 */
public class TflAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    protected static final Logger logger = LoggerFactory.getLogger(TflAuthenticationProvider.class);
    protected UserDetailsService userDetailsService;
    protected PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        TflUser tflUser = (TflUser) userDetails;
        String presentedPassword = authentication.getCredentials().toString();
        String saltedPassword = tflUser.getSalt() + presentedPassword;
        if (!passwordEncoder.matches(saltedPassword, tflUser.getPassword())) {
            throw new BadCredentialsException(messages.getMessage("security.badCredentials", "Bad credentials"));
        }
    }

    @Override
    public UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken token) throws AuthenticationException {
        return this.userDetailsService.loadUserByUsername(username);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
