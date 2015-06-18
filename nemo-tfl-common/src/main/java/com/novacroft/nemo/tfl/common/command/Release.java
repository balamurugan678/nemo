package com.novacroft.nemo.tfl.common.command;

import java.util.List;

import org.activiti.engine.task.Task;

public interface Release{

    String taskId = "";

    void setStage(String string);

    String getStage();

    List<Task> getTasks();

    void setTasks(List<Task> tasks);

    void setTaskId(String taskId);

    String getTaskId();

    String getApproval();
    
    void setApproval(String approval);

    void setFormLocation(String approval);
    
    String getFormLocation();
    
    List<Task> getTasks2();

    void setTasks2(List<Task> tasks);
    
    List<Task> getTasks3();

    void setTasks3(List<Task> tasks);
    
    List<Task> getTasks4();

    void setTasks4(List<Task> tasks);
    
    List<Task> getTasks5();

    void setTasks5(List<Task> tasks);
    
    List<Task> getTasks6();

    void setTasks6(List<Task> tasks);
    
    
}
