package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.tfl.common.util.WorkflowItemUtil.calculateTimeOnQueueAsLong;
import static com.novacroft.nemo.tfl.common.util.WorkflowItemUtil.getAmountAsPoundsAndPenceAsString;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class WorkflowItemDTOSerializer implements JsonSerializer<WorkflowItemDTO> {
    @Override
    public JsonElement serialize(WorkflowItemDTO workflowItem, Type type, JsonSerializationContext context) {
        JsonObject root = new JsonObject();
        root.addProperty("caseNumber", workflowItem.getCaseNumber());
        root.addProperty("createdTime", workflowItem.getCreatedTime());
        root.addProperty("approvalReasons", workflowItem.getApprovalReasons());
        root.addProperty("amount", getAmountAsPoundsAndPenceAsString(workflowItem.getAmount()));
        root.addProperty("amountInPence", workflowItem.getAmount());
        root.addProperty("timeOnQueue", workflowItem.getTimeOnQueue());
        root.addProperty("timeOnQueueAsLong", calculateTimeOnQueueAsLong(workflowItem));
        root.addProperty("agent", (workflowItem.getAgent() != null) ? workflowItem.getAgent() : " ");
        root.addProperty("paymentMethod", workflowItem.getPaymentMethod());
        root.addProperty("status", workflowItem.getStatus());

        return root;
    }
}
