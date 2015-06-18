package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithoutCurrencySymbol;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.getJSONInput;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class WorkflowItemDTOSerializerTest {
    private static final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(WorkflowItemDTO.class, new WorkflowItemDTOSerializer());
    private static final Gson gson = gsonBuilder.create();

    @Test
    public void shouldSerialize() {
        WorkflowItemDTO workflowItem = getJSONInput();

        String jsonResult = gson.toJson(workflowItem);
        String[] resultList = jsonResult.split(",");

        assertEquals("{\"caseNumber\":\"" + workflowItem.getCaseNumber() + "\"", resultList[0]);
        assertEquals("\"createdTime\":\"" + workflowItem.getCreatedTime() + "\"", resultList[1]);
        assertEquals("\"amount\":\"\\u0026pound;" + formatPenceWithoutCurrencySymbol(workflowItem.getAmount().intValue()) + "\"", resultList[3]);
        assertEquals("\"agent\":\"" + workflowItem.getAgent() + "\"", resultList[7]);
        assertEquals("\"paymentMethod\":\"" + workflowItem.getPaymentMethod() + "\"", resultList[8]);
        assertEquals("\"status\":\"" + workflowItem.getStatus() + "\"}", resultList[9]);
    }
}
