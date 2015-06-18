package com.novacroft.nemo.tfl.services.constant;

import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

public enum SettlementType {
    AD_HOC_LOAD(AdHocLoadSettlementDTO.class), 
    WEB_CREDIT(WebCreditSettlementDTO.class), 
    PAYMENT_CARD(PaymentCardSettlementDTO.class), 
    BACS(BACSSettlementDTO.class), 
    CHEQUE(ChequeSettlementDTO.class), 
    AUTO_LOAD_CHANGE(AutoLoadChangeSettlementDTO.class);

    private Class<?> classType;

    private SettlementType(Class<?> classType) {
        this.classType = classType;
    }

    public static SettlementType getSettlementType(Class<?> classType) {
        if (classType != null) {
            for (SettlementType type : SettlementType.values()) {
                if (type.classType.equals(classType)) {
                    return type;
                }
            }
        }
        return null;
    }
}
