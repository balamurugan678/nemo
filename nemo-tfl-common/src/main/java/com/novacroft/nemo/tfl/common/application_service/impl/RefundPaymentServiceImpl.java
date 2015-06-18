package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.*;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadRefundPaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.CARDS_LIST;

@Service("refundPaymentService")
public class RefundPaymentServiceImpl extends BaseService implements RefundPaymentService {
    static final Logger logger = LoggerFactory.getLogger(RefundPaymentServiceImpl.class);

    @Autowired
    protected CartDataService cartDataService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected WebAccountCreditRefundPaymentService webAccountCreditRefundPaymentService;
    @Autowired
    protected AdHocLoadRefundPaymentService adHocLoadRefundPaymentService;
    @Autowired
    protected BacsRefundPaymentService bacsRefundPaymentService;
    @Autowired
    protected ChequeRefundPaymentService chequeRefundPaymentService;
    @Autowired
    protected CardDataService cardDataService;

    @Override
    public void completeRefund(CartCmdImpl cmd) {
        callPaymentActionBasedOnPaymentType(cmd);
    }

    protected void callPaymentActionBasedOnPaymentType(CartCmdImpl cmd) {
        String paymentType = cmd.getPaymentType();
        if (isPaymentByWebCredit(paymentType)) {
            webAccountCreditRefundPaymentService.makePayment(cmd);
        } else if (isPaymentByAdHocLoad(paymentType)) {
            adHocLoadRefundPaymentService.makePayment(cmd);
        } else if (isPaymentByBACS(paymentType)) {
            bacsRefundPaymentService.makePayment(cmd);
        } else if (isPaymentByCheque(paymentType)) {
            chequeRefundPaymentService.makePayment(cmd);
        }
    }

    protected boolean isPaymentByWebCredit(String paymentType) {
        return (paymentType != null && paymentType.equals(PaymentType.WEB_ACCOUNT_CREDIT.code()));
    }

    protected boolean isPaymentByAdHocLoad(String paymentType) {
        return (paymentType != null && paymentType.equals(PaymentType.AD_HOC_LOAD.code()));
    }

    protected boolean isPaymentByBACS(String paymentType) {
        return (paymentType != null && paymentType.equals(PaymentType.BACS.code()));
    }

    protected boolean isPaymentByCheque(String paymentType) {
        return (paymentType != null && paymentType.equals(PaymentType.CHEQUE.code()));
    }

    @Override
    public SelectListDTO createCardsSelectListForAdHocLoad(String cardNumber) {
        SelectListDTO cardSelectList = new SelectListDTO();
        cardSelectList.setName(getContent(CARDS_LIST.textCode()));

        if (StringUtil.isNotBlank(cardNumber)) {
            List<CardDTO> cards = cardDataService.getAllCardsFromUserExceptCurrent(cardNumber);
            for (CardDTO card : cards) {
                if (!isCardHotlisted(card)) {
                    cardSelectList.getOptions().add(new SelectListOptionDTO(card.getCardNumber(), card.getCardNumber()));
                }
            }
        }
        return cardSelectList;
    }

    private boolean isCardHotlisted(CardDTO card) {
        return ((card.getHotlistReason() != null) && (card.getHotlistReason().getId() > 0));
    }
}
