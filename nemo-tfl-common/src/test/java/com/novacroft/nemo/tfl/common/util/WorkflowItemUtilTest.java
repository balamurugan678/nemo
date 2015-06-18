package com.novacroft.nemo.tfl.common.util;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.REASON;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.task.Task;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class WorkflowItemUtilTest {
    public static final String REASONS_AS_STRING = "Refund £1 above limit<br />Refund £1 above limit";
    WorkflowItemDTO workflowItem;

    @Before
    public void setUp() throws Exception {
        workflowItem = new WorkflowItemDTO();
        workflowItem.setTaskCreatedTime(new DateTime());
    }

    @Test
    public void shouldGetTimeOnQueueAsPeriod() {
        assertEquals("P0D", WorkflowItemUtil.getTimeOnQueueAsPeriod(workflowItem).toStandardDays().toString());
    }

    @Test
    public void shouldGetApprovalReasonsAsString() {
        List<String> list = new ArrayList<String>();
        list.add(REASON);
        list.add(REASON);
        assertEquals(REASONS_AS_STRING, WorkflowItemUtil.getApprovalReasonsAsString(list));
    }
    
    @Test
    public void shouldSetApprovalReasonsUpperCase() {
    	List<String> approvalReasons = new ArrayList<String>();
    	approvalReasons.add(REASON);
    	List<String> resultReasons = WorkflowItemUtil.setApprovalReasonsUpperCase(approvalReasons);
    	assertEquals(REASON.toUpperCase(), resultReasons.get(0));
    }
    
    @Test
    public void shouldGetTimeOnQueueAsString() {
    	assertEquals(String.class, WorkflowItemUtil.getTimeOnQueueAsString(workflowItem).getClass());
    }
    
    @Test
    public void shouldGetTimeOnQueueAsLong() {
    	assertEquals(Long.class, WorkflowItemUtil.calculateTimeOnQueueAsLong(workflowItem).getClass());
    }
    
    @Test
    public void shouldGetAmountAsString() {
    	String result = WorkflowItemUtil.getAmountAsPoundsAndPenceAsString(100L);
    	assertEquals(String.class, result.getClass());
    	assertEquals("&pound;1.00", result);
    }
}
