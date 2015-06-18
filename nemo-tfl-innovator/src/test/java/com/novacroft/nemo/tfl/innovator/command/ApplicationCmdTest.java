package com.novacroft.nemo.tfl.innovator.command;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApplicationCmdTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSetApplicationCmd() {
        ApplicationCmd cmd = new ApplicationCmd();
        final String testString = "TEST";
        Date today = new Date();
        cmd.setSecurityOption(testString);
        cmd.setSecurityPassword(testString);
        cmd.setDateOfBirth(today);
        cmd.setNotflserviceinfo(0);
        cmd.setOptouttoc(0);
        assertEquals(cmd.getSecurityOption(), testString);
        assertEquals(cmd.getSecurityPassword(), testString);
        assertEquals(cmd.getDateOfBirth(), today);
        assertTrue(cmd.getNotflserviceinfo() == 0);
        assertTrue(cmd.getOptouttoc() == 0);
    }

}
