package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.command.impl.LoginCmdImpl;

import static com.novacroft.nemo.test_support.WebAccountTestUtil.PASSWORD_1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;

/**
 * Utilities for tests that use LoginCmd
 */
public class LoginCmdTestUtil {

    public static LoginCmdImpl getTestLoginCmd1() {
        return getTestLoginCmd(USERNAME_1, PASSWORD_1);
    }

    public static LoginCmdImpl getTestLoginCmd(String username, String password) {
        LoginCmdImpl cmd = new LoginCmdImpl();
        cmd.setUsername(username);
        cmd.setPassword(password);
        return cmd;
    }
}
