package com.novacroft.nemo.tfl.batch.job;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobExecutionContextImpl;

import com.novacroft.nemo.tfl.common.application_service.MessageService;
import com.novacroft.nemo.tfl.common.application_service.impl.MessageServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.JobLogDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.JobLogDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

public class MessageSenderJobTest {

    private MessageSenderJob job;
    private MessageService mockMessageService;
    private JobLogDataService mockJobLogDataService;

    @Before
    public void setup() {
        job = new MessageSenderJob();
        mockMessageService = mock(MessageServiceImpl.class);
        mockJobLogDataService = mock(JobLogDataServiceImpl.class);
        job.jobLogDataService = mockJobLogDataService;
        job.messageService = mockMessageService;
    }

    @Test
    public void testExecuteInternal() throws JobExecutionException {
        JobExecutionContext jobExecutionContext = mock(JobExecutionContextImpl.class);
        job.executeInternal(jobExecutionContext);
        verify(mockMessageService).processMessages(any(JobLogDTO.class));
    }

}
