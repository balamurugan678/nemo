package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.application_service.BacsRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.CommonRefundPaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
import com.novacroft.nemo.tfl.common.util.AddressFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("bacsRefundPaymentService")
public class BacsRefundPaymentServiceImpl implements BacsRefundPaymentService {
    static final Logger logger = LoggerFactory.getLogger(BacsRefundPaymentServiceImpl.class);

    @Autowired
    protected CommonRefundPaymentService commonRefundPaymentService;

    @Autowired
    protected BACSSettlementDataService bacsSettlementDataService;

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
        makePaymentByBACS(cmd);
        commonRefundPaymentService.createEvent(cmd);
    }

    protected void makePaymentByBACS(CartCmdImpl cmd) {
        bacsSettlementDataService.createOrUpdate(createBACSSettlementDTOFromCartCmdImpl(cmd));
    }

    protected BACSSettlementDTO createBACSSettlementDTOFromCartCmdImpl(CartCmdImpl cmd) {
        AddressDTO addressDTO = commonRefundPaymentService.overwriteOrCreateNewAddress(cmd);
        Integer amount = -cmd.getToPayAmount();
        Date settlementDate =
                cmd.getCartDTO().getDateOfRefund() == null ? cmd.getDateOfRefund() : cmd.getCartDTO().getDateOfRefund();
        Long orderId = cmd.getCartDTO().getOrder().getId();
        String payeeName = AddressFormatUtil.formatName(cmd.getFirstName(), cmd.getInitials(), cmd.getLastName());
        String status = SettlementStatus.NEW.code();
        String bankAccount = cmd.getPayeeAccountNumber();
        String sortCode = cmd.getPayeeSortCode();

        return new BACSSettlementDTO(addressDTO, amount, settlementDate, orderId, payeeName, status, bankAccount, sortCode);
    }
}
