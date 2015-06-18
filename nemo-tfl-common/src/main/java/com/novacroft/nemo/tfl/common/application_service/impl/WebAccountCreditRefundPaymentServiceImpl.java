package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.tfl.common.application_service.CommonRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.WebAccountCreditRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

@Service("webAccountCreditRefundPaymentService")
public class WebAccountCreditRefundPaymentServiceImpl implements WebAccountCreditRefundPaymentService {
    static final Logger logger = LoggerFactory.getLogger(WebAccountCreditRefundPaymentServiceImpl.class);

    @Autowired
    protected WebCreditService webCreditService;

    @Autowired
    protected WebCreditSettlementDataService webCreditSettlementDataService;

    @Autowired
    protected CommonRefundPaymentService commonRefundPaymentService;

    @Override
    @Transactional
    public void makePayment(CartCmdImpl cmd) {
        commonRefundPaymentService.updateToPayAmount(cmd);
        commonRefundPaymentService.createOrderFromCart(cmd);
        commonRefundPaymentService.updateOrderStatusToPaid(cmd);
        commonRefundPaymentService.updateCartItemsWithOrderId(cmd);
        processWebCredit(cmd);
        updateWebCreditSettlement(cmd.getCartDTO());
        commonRefundPaymentService.createEvent(cmd);
    }

    protected void processWebCredit(CartCmdImpl cmd) {
        cmd.setToPayAmount(-cmd.getCartDTO().getCartRefundTotal());
        this.webCreditService.applyWebCreditToOrder(cmd.getCartDTO().getOrder().getId(), -cmd.getCartDTO().getCartRefundTotal());
    }

    protected void updateWebCreditSettlement(CartDTO cart) {
        List<WebCreditSettlementDTO> webCreditSettlements = this.webCreditSettlementDataService.findByOrderId(cart.getOrder().getId());
        if (CollectionUtils.isNotEmpty(webCreditSettlements)) {
            for (SettlementDTO settlementDTO : webCreditSettlements) {
                settlementDTO.setStatus(SettlementStatus.COMPLETE.code());
                this.webCreditSettlementDataService.createOrUpdate((WebCreditSettlementDTO) settlementDTO);
            }
        }
    }
}
