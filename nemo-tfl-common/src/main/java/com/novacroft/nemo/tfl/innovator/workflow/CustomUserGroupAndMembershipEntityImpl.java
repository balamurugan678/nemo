package com.novacroft.nemo.tfl.innovator.workflow;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.MembershipEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

public class CustomUserGroupAndMembershipEntityImpl {
    public static List<UserEntity> userEntities = new ArrayList<UserEntity>();
    public static List<GroupEntity> groupEntities = new ArrayList<GroupEntity>();
    public static List<MembershipEntity> membershipEntities = new ArrayList<MembershipEntity>();

    static {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("gurpreet.singh@novacroft.com");
        userEntity.setFirstName("Gurpreet");
        userEntity.setId("GS");
        userEntity.setLastName("Singh");
        userEntity.setPassword("testing");
        userEntity.setRevision(1);
        userEntities.add(userEntity);

        userEntity = new UserEntity();
        userEntity.setEmail("abu.sarang@novacroft.com");
        userEntity.setFirstName("Abu");
        userEntity.setId("AS");
        userEntity.setLastName("Sarang");
        userEntity.setPassword("testing");
        userEntity.setRevision(1);
        userEntities.add(userEntity);

        userEntity = new UserEntity();
        userEntity.setEmail("sean.simons@novacroft.com");
        userEntity.setFirstName("Sean");
        userEntity.setId("SS");
        userEntity.setLastName("Simons");
        userEntity.setPassword("testing");
        userEntity.setRevision(1);
        userEntities.add(userEntity);

        userEntity = new UserEntity();
        userEntity.setFirstName("First");
        userEntity.setLastName("Stage");
        userEntity.setId("First Stage");
        userEntities.add(userEntity);

        userEntity = new UserEntity();
        userEntity.setFirstName("Second");
        userEntity.setLastName("Stage");
        userEntity.setId("Second Stage");
        userEntities.add(userEntity);

        userEntity = new UserEntity();
        userEntity.setFirstName("Exceptions");
        userEntity.setLastName("Stage");
        userEntity.setId("Exceptions Stage");
        userEntities.add(userEntity);

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId("admin");
        groupEntity.setName("Admin");
        groupEntity.setRevision(1);
        groupEntity.setType("security-role");
        groupEntities.add(groupEntity);

        groupEntity = new GroupEntity();
        groupEntity.setId("NOVACROFT");
        groupEntity.setName("NOVACROFT");
        groupEntity.setRevision(1);
        groupEntity.setType("security-role");
        groupEntities.add(groupEntity);

        groupEntity = new GroupEntity();
        groupEntity.setId("FirstStageApprovers");
        groupEntity.setName("FirstStageApprovers");
        groupEntity.setRevision(1);
        groupEntity.setType("assignment");
        groupEntities.add(groupEntity);

        groupEntity = new GroupEntity();
        groupEntity.setId("SecondStageApprovers");
        groupEntity.setName("SecondStageApprovers");
        groupEntity.setRevision(1);
        groupEntity.setType("assignment");
        groupEntities.add(groupEntity);

        groupEntity = new GroupEntity();
        groupEntity.setId("ExceptionApprovers");
        groupEntity.setName("ExceptionApprovers");
        groupEntity.setRevision(1);
        groupEntity.setType("assignment");
        groupEntities.add(groupEntity);

        MembershipEntity membershipEntity = new MembershipEntity();
        membershipEntity.setGroupId("admin");
        membershipEntity.setUserId("GS");
        membershipEntities.add(membershipEntity);

        membershipEntity = new MembershipEntity();
        membershipEntity.setGroupId("FirstStageApprovers");
        membershipEntity.setUserId("First Stage");
        membershipEntities.add(membershipEntity);

        membershipEntity = new MembershipEntity();
        membershipEntity.setGroupId("SecondStageApprovers");
        membershipEntity.setUserId("Second Stage");
        membershipEntities.add(membershipEntity);

        membershipEntity = new MembershipEntity();
        membershipEntity.setGroupId("ExceptionApprovers");
        membershipEntity.setUserId("Exceptions Stage");
        membershipEntities.add(membershipEntity);
    }
}
