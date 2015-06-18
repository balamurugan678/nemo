package com.novacroft.nemo.tfl.innovator.workflow;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.MembershipEntity;
import org.activiti.engine.impl.persistence.entity.MembershipEntityManager;

public class CustomMembershipEntityManager extends MembershipEntityManager {

    public CustomMembershipEntityManager() {
    }

    public void createMembership(String userId, String groupId) {
        MembershipEntity membershipEntity = new MembershipEntity();
        membershipEntity.setUserId(userId);
        membershipEntity.setGroupId(groupId);
        CustomUserGroupAndMembershipEntityImpl.membershipEntities.add(membershipEntity);
    }

    public void deleteMembership(String userId, String groupId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userId", userId);
        parameters.put("groupId", groupId);
        getDbSqlSession().delete("deleteMembership", parameters);

        ListIterator<MembershipEntity> listIterator = CustomUserGroupAndMembershipEntityImpl.membershipEntities.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next().getUserId().equalsIgnoreCase(userId) && listIterator.next().getGroupId().equalsIgnoreCase(groupId)) {
                listIterator.remove();
            }
        }
    }
}
