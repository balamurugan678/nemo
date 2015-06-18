package com.novacroft.nemo.tfl.common.util.cyber_source;

import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourceDecision;
import com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostTransactionType;

import java.util.Arrays;
import java.util.List;

import static com.novacroft.nemo.tfl.common.constant.cyber_source.CyberSourcePostTransactionType.getTransactionType;

/**
 * CyberSource payment gateway utilities
 */
public final class CyberSourceUtil {
    protected static final List<CyberSourcePostTransactionType> CREATE_PAYMENT_TOKEN_TRANSACTION_TYPES = Arrays.asList(
            new CyberSourcePostTransactionType[]{CyberSourcePostTransactionType.AUTHORIZATION_AND_CREATE_PAYMENT_TOKEN,
                    CyberSourcePostTransactionType.CREATE_PAYMENT_TOKEN,
                    CyberSourcePostTransactionType.SALE_AND_CREATE_PAYMENT_TOKEN}
    );
    protected static final List<CyberSourceDecision> INCOMPLETE_DECISIONS = Arrays.asList(
            new CyberSourceDecision[]{CyberSourceDecision.REVIEW, CyberSourceDecision.DECLINE, CyberSourceDecision.ERROR,
                    CyberSourceDecision.REJECT}
    );

    public static Boolean isAccepted(String decision) {
        return CyberSourceDecision.ACCEPT.code().equalsIgnoreCase(decision);
    }

    public static Boolean isCancelled(String decision) {
        return CyberSourceDecision.CANCEL.code().equalsIgnoreCase(decision);
    }

    public static Boolean isIncomplete(String decision) {
        return INCOMPLETE_DECISIONS.contains(CyberSourceDecision.getDecision(decision));
    }

    public static Boolean isCreatePaymentToken(String transactionType) {
        return CREATE_PAYMENT_TOKEN_TRANSACTION_TYPES.contains(getTransactionType(transactionType));
    }

    public static EventName resolveEventNameForDecision(String decision) {
        if (isAccepted(decision)) {
            return EventName.PAYMENT_RESOLVED;
        } else if (isCancelled(decision)) {
            return EventName.PAYMENT_CANCELLED;
        } else if (isIncomplete(decision)) {
            return EventName.PAYMENT_INCOMPLETE;
        }
        throw new ApplicationServiceException(String.format(PrivateError.INVALID_PAYMENT_GATEWAY_DECISION.message(), decision));
    }

    private CyberSourceUtil() {
    }
}
