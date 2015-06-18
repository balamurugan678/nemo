package com.novacroft.nemo.tfl.common.transfer.financial_services_centre;

import java.util.Date;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;

public class PayeeSettlementDTO extends SettlementDTO {

	protected String payeeName;
	protected Long fileExportLogId;
	protected AddressDTO addressDTO;
	protected Long paymentReference;

	public PayeeSettlementDTO() {
		super();
	}

	public PayeeSettlementDTO(Long orderId, String status, Date settlementDate,
			Integer amount) {
		super(orderId, status, settlementDate, amount);
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public Long getFileExportLogId() {
		return fileExportLogId;
	}

	public void setFileExportLogId(Long fileExportLogId) {
		this.fileExportLogId = fileExportLogId;
	}

	public AddressDTO getAddressDTO() {
		return addressDTO;
	}

	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}

	public Long getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(Long paymentReference) {
		this.paymentReference = paymentReference;
	}

}