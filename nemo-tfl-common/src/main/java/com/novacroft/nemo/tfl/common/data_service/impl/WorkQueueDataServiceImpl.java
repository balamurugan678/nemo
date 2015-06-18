package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.WorkQueueConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.WorkQueueDAO;
import com.novacroft.nemo.tfl.common.data_service.WorkQueueDataService;
import com.novacroft.nemo.tfl.common.domain.WorkQueue;
import com.novacroft.nemo.tfl.common.transfer.WorkQueueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "workQueueDataService")
@Transactional(readOnly = true)
public class WorkQueueDataServiceImpl extends BaseDataServiceImpl<WorkQueue, WorkQueueDTO> implements WorkQueueDataService {

    @Autowired
    public void setDao(WorkQueueDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(WorkQueueConverterImpl converter) {
        this.converter = converter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WorkQueueDTO> findAllBy(String status) {
        List<WorkQueueDTO> workqueueDTO = new ArrayList<WorkQueueDTO>();

        WorkQueue exampleEntity = new WorkQueue();
        exampleEntity.setStatus(status);
        //    exampleEntity.setStage(1L);

        //
        //    final String hsql = "from workqueue w where w.Status = ?";

        //  List workQueueItems = dao.findByQuery(hsql, status);
        List<WorkQueue> workQueueItems = dao.findByExample(exampleEntity);
        for (WorkQueue workQueue : workQueueItems) {
            WorkQueueDTO dto = new WorkQueueDTO();
            dto.setData(workQueue);
            workqueueDTO.add(dto);
        }
        return workqueueDTO;
    }

    @Override
    public WorkQueueDTO findByTest(String test) {
        return null;
    }

    @Override
    public WorkQueue getNewEntity() {
        // TODO Auto-generated method stub
        return new WorkQueue();
    }
}
