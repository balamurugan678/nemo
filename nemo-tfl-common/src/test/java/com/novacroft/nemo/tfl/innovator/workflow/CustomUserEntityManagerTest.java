package com.novacroft.nemo.tfl.innovator.workflow;

import static com.novacroft.nemo.test_support.CustomUserEntityManagerTestUtil.GROUP_ID;
import static com.novacroft.nemo.test_support.CustomUserEntityManagerTestUtil.USER_ID;
import static com.novacroft.nemo.test_support.CustomUserEntityManagerTestUtil.USER_LAST_NAME;
import static com.novacroft.nemo.test_support.CustomUserEntityManagerTestUtil.getUser;
import static com.novacroft.nemo.test_support.CustomUserEntityManagerTestUtil.getUserEntity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.MembershipEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.UserService;

public class CustomUserEntityManagerTest {
    private CustomUserEntityManager customUserEntityManager;
    private CustomUserEntityManager mockCustomUserEntityManager;
    private UserService mockUserService;
    private UserEntity user;
    
    @Before
    public void setUp() {
        customUserEntityManager = new CustomUserEntityManager();
        mockCustomUserEntityManager = mock(CustomUserEntityManager.class);
        mockUserService = mock(UserService.class);
        user = getUserEntity();

        customUserEntityManager.userService = mockUserService;
        mockCustomUserEntityManager.userService = mockUserService;
        
        when(mockUserService.findUserById(anyString())).thenReturn(getUser());
    }

    @Test
    public void shouldFindUserByLastName() {
        doCallRealMethod().when(mockCustomUserEntityManager).findUserByLastName(anyString());
        when(mockCustomUserEntityManager.isUserLastNameEqual(any(UserEntity.class), anyString())).thenReturn(Boolean.TRUE);
        mockCustomUserEntityManager.findUserByLastName(USER_LAST_NAME);
        verify(mockCustomUserEntityManager).isUserLastNameEqual(any(UserEntity.class), anyString());
    }

    @Test
    public void shouldFindUsersByGroupId() {
        doCallRealMethod().when(mockCustomUserEntityManager).findUsersByGroupId(anyString());
        when(mockCustomUserEntityManager.findUserById(anyString())).thenReturn(user);
        when(mockCustomUserEntityManager.isGroupIdEqual(any(MembershipEntity.class), anyString())).thenReturn(Boolean.TRUE);
        List<User> result = mockCustomUserEntityManager.findUsersByGroupId(GROUP_ID);
        assertTrue(result.size() > 0);
        verify(mockCustomUserEntityManager, atLeastOnce()).findUserById(anyString());
        verify(mockCustomUserEntityManager, atLeastOnce()).isGroupIdEqual(any(MembershipEntity.class), anyString());
    }

    @Test
    public void shouldFindUserById() {
        UserEntity result = customUserEntityManager.findUserById(GROUP_ID);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void shouldFindUsersByGroupIdHardCoded() {
        doCallRealMethod().when(mockCustomUserEntityManager).findUsersByGroupIdHardcoded(anyString());
        when(mockCustomUserEntityManager.findUserByIdHardcoded(anyString())).thenReturn(user);
        when(mockCustomUserEntityManager.isGroupIdEqual(any(MembershipEntity.class), anyString())).thenReturn(Boolean.TRUE);
        List<UserEntity> result = mockCustomUserEntityManager.findUsersByGroupIdHardcoded(GROUP_ID);
        assertTrue(result.size() > 0);
        verify(mockCustomUserEntityManager, atLeastOnce()).findUserByIdHardcoded(anyString());
        verify(mockCustomUserEntityManager, atLeastOnce()).isGroupIdEqual(any(MembershipEntity.class), anyString());
    }

    @Test
    public void shouldFindUserByIdHardCoded() {
        doCallRealMethod().when(mockCustomUserEntityManager).findUserByIdHardcoded(anyString());
        when(mockCustomUserEntityManager.isUserIdEqual(any(UserEntity.class), anyString())).thenReturn(Boolean.TRUE);
        UserEntity result = mockCustomUserEntityManager.findUserByIdHardcoded(USER_ID);
        assertTrue(result != null);
        verify(mockCustomUserEntityManager).isUserIdEqual(any(UserEntity.class), anyString());
    }

    @Test
    public void shouldFindGroupsByUser() {
        customUserEntityManager.findGroupsByUser(USER_ID);
        verify(mockUserService).findUserById(anyString());
    }

}
