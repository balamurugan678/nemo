package com.novacroft.nemo.tfl.innovator.workflow;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.MembershipEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.novacroft.nemo.common.application_service.UserService;

public class CustomUserEntityManager extends UserEntityManager {
    @Autowired
    protected UserService userService;

    public CustomUserEntityManager() {
    }

    public UserEntity findUserByLastName(String userLastName) {
        for (UserEntity userEntity : CustomUserGroupAndMembershipEntityImpl.userEntities) {
            if (isUserLastNameEqual(userEntity, userLastName)) {
                return userEntity;
            }
        }
        return new UserEntity();
    }

    public List<User> findUsersByGroupId(String groupId) {
        List<User> users = new ArrayList<User>();
        for (MembershipEntity membershipEntity : CustomUserGroupAndMembershipEntityImpl.membershipEntities) {
            if (isGroupIdEqual(membershipEntity, groupId)) {
                users.add(findUserById(membershipEntity.getUserId()));
            }
        }
        return users;
    }

    public List<UserEntity> findUsersByGroupIdHardcoded(String groupId) {
        List<UserEntity> users = new ArrayList<UserEntity>();
        for (MembershipEntity membershipEntity : CustomUserGroupAndMembershipEntityImpl.membershipEntities) {
            if (isGroupIdEqual(membershipEntity, groupId)) {
                users.add(findUserByIdHardcoded(membershipEntity.getUserId()));
            }
        }
        return users;
    }

    protected UserEntity findUserByIdHardcoded(String userId) {
        for (UserEntity userEntity : CustomUserGroupAndMembershipEntityImpl.userEntities) {
            if (isUserIdEqual(userEntity, userId)) {
                return userEntity;
            }
        }
        return null;
    }

    @Override
    public UserEntity findUserById(String userId) {
        com.novacroft.nemo.common.domain.User user = userService.findUserById(userId);
        return userToActivitiUser(user);
    }

    private UserEntity userToActivitiUser(com.novacroft.nemo.common.domain.User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmailAddress());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setId(user.getId());
        userEntity.setLastName(user.getLastName());
        userEntity.setPassword(user.getPassword());
        userEntity.setRevision(1);
        return userEntity;
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        com.novacroft.nemo.common.domain.User user = userService.findUserById(userId);
        List<Group> groups = new ArrayList<Group>();

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(user.getTemplateUserId());
        groupEntity.setName(user.getTemplateUserId());
        groupEntity.setRevision(1);
        groupEntity.setType("admin");
        groups.add(groupEntity);

        return groups;
    }

    protected boolean isUserLastNameEqual(UserEntity userEntity, String userLastName) {
        return userEntity.getId().equalsIgnoreCase(userLastName);
    }

    protected boolean isUserIdEqual(UserEntity userEntity, String userId) {
        return userEntity.getId().equalsIgnoreCase(userId);
    }

    protected boolean isGroupIdEqual(MembershipEntity membershipEntity, String groupId) {
        return membershipEntity.getGroupId().equalsIgnoreCase(groupId);
    }

    @Override
    public User createNewUser(String userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertUser(User user) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public void updateUser(UserEntity updatedUser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(String userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserQuery createNewUserQuery() {
        return super.createNewUserQuery();
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        throw new UnsupportedOperationException();
    }
}
