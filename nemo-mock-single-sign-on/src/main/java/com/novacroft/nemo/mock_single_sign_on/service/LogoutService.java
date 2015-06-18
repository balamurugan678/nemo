package com.novacroft.nemo.mock_single_sign_on.service;

import java.util.List;

public interface LogoutService {
    void singleSignOut(List<String> logoutUrls, String token);
}
