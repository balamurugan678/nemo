package com.novacroft.nemo.tfl.common.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.data_service.ExternalUserDataService;
import com.novacroft.nemo.tfl.common.transfer.ExternalUserDTO;

/**
 * Spring security user details service implementation for Web Services.
 * <p/>
 */
public class TflWebServicesUserDetailsService implements UserDetailsService {
    protected static final Logger logger = LoggerFactory.getLogger(TflUserDetailsService.class);
    protected ExternalUserDataService dataService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ExternalUserDTO externalUser = dataService.findByUsername(username);
        if (externalUser == null) {
            String msg = String.format(PrivateError.USERNAME_NOT_FOUND.message(), username);
            logger.error(msg);
            throw new UsernameNotFoundException(msg);
        }

        List<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();
        userAuthorities.add(new SimpleGrantedAuthority(externalUser.getRole()));
        return new TflUser(externalUser.getUsername(), externalUser.getPassword(), externalUser.getSalt(), userAuthorities);
    }

    public void setDataService(ExternalUserDataService dataService) {
        this.dataService = dataService;
    }
}

