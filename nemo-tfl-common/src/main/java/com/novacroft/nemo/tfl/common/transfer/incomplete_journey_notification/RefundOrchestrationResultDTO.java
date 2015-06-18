package com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification;

import com.novacroft.nemo.tfl.common.constant.incomplete_journey.AutoFillSSRNotificationStatus;

public class RefundOrchestrationResultDTO {

	protected AutoFillSSRNotificationStatus autoFillSelfServiceRefundNotificationStatus;
	
	protected Integer refundAmount;

	public RefundOrchestrationResultDTO(AutoFillSSRNotificationStatus autoFillSelfServiceRefundNotificationStatus, Integer refundAmount) {
		this.autoFillSelfServiceRefundNotificationStatus = autoFillSelfServiceRefundNotificationStatus;
		this.refundAmount = refundAmount;
	}

	public AutoFillSSRNotificationStatus getAutoFillSelfServiceRefundNotificationStatus() {
		return autoFillSelfServiceRefundNotificationStatus;
	}

	public void setAutoFillSelfServiceRefundNotificationStatus(AutoFillSSRNotificationStatus autoFillSelfServiceRefundNotificationStatus) {
		this.autoFillSelfServiceRefundNotificationStatus = autoFillSelfServiceRefundNotificationStatus;
	}

	public Integer getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Integer refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	
}
