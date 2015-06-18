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
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

/**
 * Spring security user details service implementation for TfL.
 * <p/>
 * TODO: change this spurious implementation to fit TfL requirements
 */
public class TflUserDetailsService implements UserDetailsService {
    protected static final Logger logger = LoggerFactory.getLogger(TflUserDetailsService.class);
    protected CustomerDataService dataService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerDTO customer = dataService.findByUsernameOrEmail(username);
        if (customer == null) {
            String msg = String.format(PrivateError.USERNAME_NOT_FOUND.message(), username);
            logger.error(msg);
            throw new UsernameNotFoundException(msg);
        }

        // TODO refactor when role requirements known
        List<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();
        userAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new TflUser(customer.getUsername(), customer.getPassword(), customer.getSalt(), userAuthorities);
    }

    public void setDataService(CustomerDataService dataService) {
        this.dataService = dataService;
    }
}

