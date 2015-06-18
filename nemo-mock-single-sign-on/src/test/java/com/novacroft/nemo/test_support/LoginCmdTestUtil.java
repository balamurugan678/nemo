package com.novacroft.nemo.test_support;

import com.novacroft.nemo.mock_single_sign_on.command.LoginCmd;

public final class LoginCmdTestUtil {
    public static final String TEST_USER_NAME = "Test User Name";
    public static final String TEST_Password = "Test Password";
    public static final String TEST_APP = "Test App";
    public static final String TEST_APP_ID = "Test App ID";
    public static final String TEST_RETURN_URL = "Test Return URL";
    
    public static final String TEST_SESSION_ID = "Test Session ID";
    
    public static final String LOGIN_VIEW_NAME = "LoginView";
    
    public static LoginCmd createValidTestLoginCmd() {
        LoginCmd cmd = new LoginCmd();
        cmd.setUserName(TEST_USER_NAME);
        cmd.setPassword(TEST_Password);
        cmd.setApp(TEST_APP);
        return cmd;
    }
    
    private LoginCmdTestUtil() {}
}
