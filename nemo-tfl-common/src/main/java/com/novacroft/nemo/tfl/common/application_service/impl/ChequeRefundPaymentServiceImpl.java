package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.ChequeRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.CommonRefundPaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

@Service("chequeRefundPaymentService")
public class ChequeRefundPaymentServiceImpl implements ChequeRefundPaymentService {
    static final Logger logger = LoggerFactory.getLogger(ChequeRefundPaymentServiceImpl.class);

    @Autowired
    protected CommonRefundPaymentService commonRefundPaymentService;

    @Autowired
    protected ChequeSettlementDataService chequeSettlementDataService;

    @Autowired
    protected AddressDataService addressDataService;

    @Autowired
    protected CustomerDataService customerDataService;

    @Override
    @Transactional
    public void makePayment(CartCmdImpl cmd) {
        commonRefundPaymentService.updateToPayAmount(cmd);
        commonRefundPaymentService.createOrderFromCart(cmd);
        commonRefundPaymentService.updateOrderStatusToPaid(cmd);
        commonRefundPaymentService.updateCartItemsWithOrderId(cmd);
        makePaymentByCheque(cmd);
        commonRefundPaymentService.createEvent(cmd);
    }

    protected void makePaymentByCheque(CartCmdImpl cmd) {
        chequeSettlementDataService.createOrUpdate(createChequeSettlementDTOFromCartCmdImpl(cmd));
    }

    protected ChequeSettlementDTO createChequeSettlementDTOFromCartCmdImpl(CartCmdImpl cmd) {
        AddressDTO addressDTO = commonRefundPaymentService.overwriteOrCreateNewAddress(cmd);
        Integer amount = -cmd.getToPayAmount();
        Date settlementDate = cmd.getCartDTO().getDateOfRefund();
        Long orderId = cmd.getCartDTO().getOrder().getId();
        String payeeName = cmd.getFirstName() + StringUtil.SPACE + cmd.getInitials() + StringUtil.SPACE + cmd.getLastName();
        String status = SettlementStatus.NEW.code();

        return new ChequeSettlementDTO(addressDTO, amount, settlementDate, orderId, payeeName, status);
    }
}
