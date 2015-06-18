package com.novacroft.nemo.tfl.common.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Spring security user implementation for TfL.
 * <p/>
 * TODO: change this spurious implementation to fit TfL requirements
 */
public class TflUser extends User {
	private static final long serialVersionUID = -1546423360684518826L;
	protected String salt;

    public TflUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public TflUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public TflUser(String username, String password, String salt, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }
}
