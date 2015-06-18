package com.novacroft.nemo.tfl.innovator.workflow;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

public class CustomGroupEntityManager extends GroupEntityManager {


    public CustomGroupEntityManager() {
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        return  new ArrayList<Group>();
    }

    @Override
    public Group createNewGroup(String groupId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertGroup(Group group) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public void updateGroup(GroupEntity updatedGroup) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteGroup(String groupId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GroupQuery createNewGroupQuery() {
        return new GroupQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutor());
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        if (query != null && query.getUserId() != null && !("").equals(query.getUserId())) {
            return findGroupsByUser(query.getUserId());
        } else {
            return new ArrayList<Group>();
        }
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        throw new UnsupportedOperationException();
    }
}
