package com.novacroft.nemo.tfl.services.test_support;

import static com.novacroft.nemo.test_support.DeleteCustomerTestUtil.DELETED_DATE_TIME;
import static com.novacroft.nemo.test_support.DeleteCustomerTestUtil.DELETED_NOTE;
import static com.novacroft.nemo.test_support.DeleteCustomerTestUtil.DELETED_REASON_CODE;
import static com.novacroft.nemo.test_support.DeleteCustomerTestUtil.DELETED_REFERENCE_NUMBER;
import static com.novacroft.nemo.test_support.DeleteCustomerTestUtil.EXTERNAL_USER_ID;

import java.util.Date;

import com.novacroft.nemo.tfl.services.transfer.DeleteCustomer;

public final class DeleteCustomerServiceTestUtil {
    public static DeleteCustomer getDeleteCustomer(Long id, Date deletedDateTime, String deletedReasonCode, String deletedReferenceNumber, String deletedNote) {
        return new DeleteCustomer(id, deletedDateTime, deletedReasonCode, deletedReferenceNumber, deletedNote);
    }
    
    public static DeleteCustomer getTestDeleteCustomer1(){
        return getDeleteCustomer(EXTERNAL_USER_ID, DELETED_DATE_TIME, DELETED_REASON_CODE, DELETED_REFERENCE_NUMBER, DELETED_NOTE);
    }
    
   public static DeleteCustomer getTestDeleteCustomerWithError(){
       DeleteCustomer customer = new DeleteCustomer();
       customer.setErrors(ErrorResultTestUtil.getTestErrorResult1());
       return customer;
   }
}
