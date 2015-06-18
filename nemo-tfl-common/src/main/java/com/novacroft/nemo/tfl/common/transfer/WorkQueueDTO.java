package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.tfl.common.domain.WorkQueue;

/**
* Workqueue   transfer implementation.
* Automatically created.
*/

public class WorkQueueDTO extends CommonWorkQueueDTO {

    public void setData(WorkQueue workQueue) {
        super.setCreatedDateTime(workQueue.getCreatedDateTime());
        super.setCreatedUserId(workQueue.getCreatedUserId());
        super.setDescription(workQueue.getDescription());
        super.setId(workQueue.getId());
        super.setModifiedDateTime(workQueue.getModifiedDateTime());
        super.setStage(workQueue.getStage());
        super.setStatus(workQueue.getStatus());
        
    }

    public WorkQueue getWorkQueue() {
      WorkQueue queueItem =  new WorkQueue();
      
      queueItem.setCreatedDateTime(super.getCreatedDateTime());
      queueItem.setCreatedUserId(super.getCreatedUserId());
      queueItem.setDescription(super.getDescription());
      queueItem.setId(super.getId());
      queueItem.setModifiedDateTime(super.getModifiedDateTime());
      queueItem.setModifiedUserId(super.getModifiedUserId());
      queueItem.setStage(super.getStage());
      queueItem.setStatus(super.getStatus());
      
        return queueItem;
    }

}
