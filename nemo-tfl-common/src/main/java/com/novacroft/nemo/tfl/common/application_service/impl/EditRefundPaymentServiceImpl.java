package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.RefundConstants.ADHOC_LOAD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.PAYMENT_CARD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.WEB_CREDIT;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.REFUND_EDIT_PAYMENT_INFO;

import org.activiti.engine.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.EditRefundPaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.constant.ContentCodeSuffix;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.PayeePaymentDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@Service("editRefundPaymentService")
public class EditRefundPaymentServiceImpl implements EditRefundPaymentService{
	protected static final String SEPARATOR = ".";
	@Autowired(required = false)
	protected TaskService taskService;
	@Autowired
    protected ContentDataService contentDataService;
    @Autowired
    protected LocationDataService locationDataService;
	
	@Override
	public PayeePaymentDTO getModifiedRefundPaymentFields(WorkflowItemDTO workflowItem, CartCmdImpl cartCmdImpl){
		PayeePaymentDTO payeePayment = new PayeePaymentDTO();
		
		if(PaymentType.WEB_ACCOUNT_CREDIT.code().equalsIgnoreCase(cartCmdImpl.getPaymentType()) ||
				PaymentType.AD_HOC_LOAD.code().equalsIgnoreCase(cartCmdImpl.getPaymentType()) ||
				PaymentType.PAYMENT_CARD.code().equalsIgnoreCase(cartCmdImpl.getPaymentType())){
			payeePayment.setPaymentType(getPaymentMethod(cartCmdImpl.getPaymentType()));
		} else {
			payeePayment.setPaymentType(cartCmdImpl.getPaymentType());
		}
		
		payeePayment.setTitle(cartCmdImpl.getTitle());
		payeePayment.setFirstName(cartCmdImpl.getFirstName());
		payeePayment.setInitials(cartCmdImpl.getInitials());
		payeePayment.setLastName(cartCmdImpl.getLastName());
		
		payeePayment.setPayeeAddress(cartCmdImpl.getPayeeAddress());
		payeePayment.setPayeeSortCode(cartCmdImpl.getPayeeSortCode());
		payeePayment.setPayeeAccountNumber(cartCmdImpl.getPayeeAccountNumber());
		payeePayment.setTargetCardNumber(cartCmdImpl.getTargetCardNumber());
		payeePayment.setStationId(cartCmdImpl.getStationId());
		
		payeePayment.setIsEdited(Boolean.TRUE);
		
		return payeePayment;
	}
	    @Override
	public WorkflowEditCmd updateWorkflowEditCmdWithModifiedEditRefundsDetails( WorkflowItemDTO workflowItem,
			WorkflowEditCmd workflowEditCmd, PayeePaymentDTO payeePayment) {
    	if(payeePayment.getIsEdited()){
	    	setPaymentType(workflowEditCmd, payeePayment.getPaymentType());
	    	if(ADHOC_LOAD.equalsIgnoreCase(payeePayment.getPaymentType())){
	    		setDetailsForAdhocLoad(workflowEditCmd,payeePayment);
	    	} else if(WEB_CREDIT.equalsIgnoreCase(payeePayment.getPaymentType())){
	    		setDetailsForWebAccountCredit(workflowItem, workflowEditCmd);
	    	} else if(PaymentType.BACS.code().equalsIgnoreCase(payeePayment.getPaymentType()) || 
	    			PaymentType.CHEQUE.code().equalsIgnoreCase(payeePayment.getPaymentType())){
	    		if(PaymentType.BACS.code().equalsIgnoreCase(payeePayment.getPaymentType())){
	    			setDetailsForBACS(workflowEditCmd,payeePayment);
	    		}
	    		setDetailsForBACSAndCheque(workflowEditCmd,payeePayment);
	    	}
    	}
    	return workflowEditCmd;
	}
    
    private void setPaymentType(WorkflowEditCmd workflowEditCmd, String paymentType){
    	workflowEditCmd.setPaymentMethod(paymentType);
        String paymentMethod = StringUtils.remove(paymentType, StringUtil.SPACE);
        paymentMethod = StringUtils.remove(paymentMethod, StringUtil.HYPHEN);
        workflowEditCmd.setPaymentType(PaymentType.lookUpPaymentType(paymentMethod));
    }
    
    protected String getPaymentMethod(String paymentMethod) {
    	StringBuilder paymentType = new StringBuilder();
    	paymentType.append(paymentMethod);
    	paymentType.append(SEPARATOR);
    	paymentType.append(ContentCodeSuffix.TEXT.code());
		return contentDataService.findByLocaleAndCode(paymentType.toString(), LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
	}
    
    private void setPayeeNameChanges(WorkflowEditCmd workflowEditCmd, PayeePaymentDTO payeePayment){
    	if(payeePayment.getTitle() != null){
    		workflowEditCmd.setPaymentTitle(payeePayment.getTitle());
    	}
    	if(payeePayment.getFirstName() != null){
    		workflowEditCmd.setPaymentFirstName(payeePayment.getFirstName());
    	}
    	if(payeePayment.getInitials() != null){
    		workflowEditCmd.setPaymentInitials(payeePayment.getInitials());
    	}
    	if(payeePayment.getLastName() != null){
    		workflowEditCmd.setPaymentLastName(payeePayment.getLastName());
    	}
    	workflowEditCmd.setPaymentName(getFullNameOfCustomer(payeePayment));
    }

    private void setPayeeAddressChanges(WorkflowEditCmd workflowEditCmd, PayeePaymentDTO payeePayment){
    	if(payeePayment.getPayeeAddress() != null){
    		workflowEditCmd.setPayeeAddress(payeePayment.getPayeeAddress());
    		workflowEditCmd.setPaymentHouseNameNumber(payeePayment.getPayeeAddress().getHouseNameNumber());
    		workflowEditCmd.setPaymentStreet(payeePayment.getPayeeAddress().getStreet());
    		workflowEditCmd.setPaymentTown(payeePayment.getPayeeAddress().getTown());
    		workflowEditCmd.setPaymentPostCode(payeePayment.getPayeeAddress().getPostcode());
    		workflowEditCmd.setPaymentCountry(payeePayment.getPayeeAddress().getCountry());
    	}
    }
    private void setDetailsForWebAccountCredit(WorkflowItemDTO workflowItem, WorkflowEditCmd workflowEditCmd){
    	workflowEditCmd.setStationId(null);
        workflowEditCmd.setStation(null);
    	workflowEditCmd.setTargetCardNumber(null);
    	workflowEditCmd.setPayeeAccountNumber(null);
    	workflowEditCmd.setPayeeSortCode(null);
    	workflowEditCmd.setPayeeAddress(null);
    	workflowEditCmd.setPaymentTitle(null);
		workflowEditCmd.setPaymentFirstName(null);
		workflowEditCmd.setPaymentInitials(null);
		workflowEditCmd.setPaymentLastName(null);
		workflowEditCmd.setPaymentHouseNameNumber(null);
		workflowEditCmd.setPaymentStreet(null);
		workflowEditCmd.setPaymentTown(null);
		workflowEditCmd.setPaymentPostCode(null);
		workflowEditCmd.setPaymentCountry(null);
		workflowEditCmd.setPaymentName(getFullNameOfCustomer(workflowItem.getRefundDetails()));
    }
    
    protected String getFullNameOfCustomer(RefundDetailDTO refundDetails) {

        StringBuffer fullName = new StringBuffer();
        CustomerDTO customerDto = refundDetails.getCustomer();

        return fullName.append(customerDto.getFirstName()).append(StringUtil.SPACE).append(customerDto.getInitials()).append(StringUtil.SPACE)
                        .append(customerDto.getLastName()).toString();
    }
    
    private void setDetailsForAdhocLoad(WorkflowEditCmd workflowEditCmd, PayeePaymentDTO payeePayment){
    	if(payeePayment.getStationId() != null){
    		workflowEditCmd.setStationId(payeePayment.getStationId());
            workflowEditCmd.setStation(locationDataService.getActiveLocationById(payeePayment.getStationId().intValue()).getName());
    	}
    	if(payeePayment.getTargetCardNumber() != null){
    		workflowEditCmd.setTargetCardNumber(payeePayment.getTargetCardNumber());
    	}
    	workflowEditCmd.setPayeeAccountNumber(null);
    	workflowEditCmd.setPayeeSortCode(null);
    	workflowEditCmd.setPayeeAddress(null);
    	workflowEditCmd.setPaymentTitle(null);
		workflowEditCmd.setPaymentFirstName(null);
		workflowEditCmd.setPaymentInitials(null);
		workflowEditCmd.setPaymentLastName(null);
		workflowEditCmd.setPaymentHouseNameNumber(null);
		workflowEditCmd.setPaymentPostCode(null);
		workflowEditCmd.setPaymentStreet(null);
		workflowEditCmd.setPaymentTown(null);
		workflowEditCmd.setPaymentCountry(null);
    }

    private void setDetailsForBACS(WorkflowEditCmd workflowEditCmd, PayeePaymentDTO payeePayment){
    	if(payeePayment.getPayeeAccountNumber() != null){
    		workflowEditCmd.setPayeeAccountNumber(payeePayment.getPayeeAccountNumber());
    	}
    	if(payeePayment.getPayeeSortCode() != null){
    		workflowEditCmd.setPayeeSortCode(payeePayment.getPayeeSortCode());
    	}
    	workflowEditCmd.setStationId(null);
        workflowEditCmd.setStation(null);
    	workflowEditCmd.setTargetCardNumber(null);
    }

    private void setDetailsForBACSAndCheque(WorkflowEditCmd workflowEditCmd, PayeePaymentDTO payeePayment){
    	setPayeeNameChanges(workflowEditCmd,payeePayment);
    	setPayeeAddressChanges(workflowEditCmd,payeePayment);
    }
    
    protected String getFullNameOfCustomer(PayeePaymentDTO payeePayment) {

        StringBuffer fullName = new StringBuffer();
        return fullName.append(payeePayment.getFirstName()).append(StringUtil.SPACE).append(payeePayment.getInitials())
                        .append(StringUtil.SPACE)
                        .append(payeePayment.getLastName()).toString();
    }
	@Override
	public CartCmdImpl populateEditedValuesInCartCmdImplFromPayeePayment(
		CartCmdImpl cartCmdImpl, PayeePaymentDTO payeePayment) {
		cartCmdImpl.setPaymentType(getPaymentType(payeePayment.getPaymentType()));
		
		cartCmdImpl.setFirstName(payeePayment.getFirstName());
		cartCmdImpl.setInitials(payeePayment.getInitials());
		cartCmdImpl.setLastName(payeePayment.getLastName());
	
		cartCmdImpl.setPayeeAccountNumber(payeePayment.getPayeeAccountNumber());
		cartCmdImpl.setPayeeSortCode(payeePayment.getPayeeSortCode());
	
		cartCmdImpl.setTargetCardNumber(payeePayment.getTargetCardNumber());	
		cartCmdImpl.setStationId(payeePayment.getStationId());
		
		cartCmdImpl.setPayeeAddress(payeePayment.getPayeeAddress());
		return cartCmdImpl;
	}
	
	protected String getPaymentType(String paymentType){
    	if(WEB_CREDIT.equalsIgnoreCase(paymentType)){
    		return PaymentType.WEB_ACCOUNT_CREDIT.code();
    	} else if(ADHOC_LOAD.equalsIgnoreCase(paymentType)){
    		return PaymentType.AD_HOC_LOAD.code();
    	} else if(PAYMENT_CARD.equalsIgnoreCase(paymentType)){
    		return PaymentType.PAYMENT_CARD.code();
    	}
    	
    	return paymentType;
    }
	
	@Override
	public void clearEditedPayeePaymentInfo(WorkflowItemDTO workflowItem){
		PayeePaymentDTO payeePayment = new PayeePaymentDTO();
		taskService.setVariableLocal(workflowItem.getTaskId(), REFUND_EDIT_PAYMENT_INFO , payeePayment);		
	}
}