package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.WebCreditStatementService;
import com.novacroft.nemo.tfl.common.command.impl.WebCreditStatementCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.WebCreditStatementLineCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.WebCreditStatementLineDTO;

/**
 * Service implementation to assemble web credit statement
 */
@Service(value = "webCreditStatementService")
public class WebCreditStatementServiceImpl implements WebCreditStatementService {

    @Autowired
    protected WebCreditSettlementDataService webCreditSettlementDataService;
    @Autowired
    protected ApplicationContext applicationContext;

    @Override
    public WebCreditStatementCmdImpl getStatement(Long customerId) {
        WebCreditStatementCmdImpl cmd = new WebCreditStatementCmdImpl();
        cmd.setStatementLines(
                prepareStatementLines(this.webCreditSettlementDataService.findByCustomerId(customerId)));
        prepareCumulativeBalance(cmd);
        cmd.setCurrentBalance(calculateTotalBalance(cmd.getStatementLines()));
        return cmd;
    }

    protected List<WebCreditStatementLineCmdImpl> prepareStatementLines(
            List<WebCreditStatementLineDTO> statementLineDTOs) {
        List<WebCreditStatementLineCmdImpl> statementLines = new ArrayList<WebCreditStatementLineCmdImpl>();
        for (WebCreditStatementLineDTO settlement : statementLineDTOs) {
            statementLines.add(prepareStatementLine(settlement));
        }
        return statementLines;
    }

    protected WebCreditStatementLineCmdImpl prepareStatementLine(WebCreditStatementLineDTO statementLineDTO) {
        return new WebCreditStatementLineCmdImpl(statementLineDTO.getSettlementDate(),
                statementLineDTO.getSettlementAmount() >= 0 ?
                        getMessage(ContentCode.WEB_CREDIT_STATEMENT_ITEM_PURCHASE.textCode()) :
                        getMessage(ContentCode.WEB_CREDIT_STATEMENT_ITEM_REFUND.textCode()),
                statementLineDTO.getOrderNumber().toString(),
                statementLineDTO.getSettlementAmount() >= 0 ? null : statementLineDTO.getSettlementAmount(),
                statementLineDTO.getSettlementAmount() >= 0 ? statementLineDTO.getSettlementAmount() : null, null);
    }

    protected void prepareCumulativeBalance(WebCreditStatementCmdImpl cmd) {
        int cumulativeBalance = 0;
        Collections.reverse(cmd.getStatementLines());
        for (WebCreditStatementLineCmdImpl statementLine : cmd.getStatementLines()) {
            cumulativeBalance += calculateLineAmount(statementLine.getCreditAmount(), statementLine.getDebitAmount());
            statementLine.setCumulativeBalanceAmount(-cumulativeBalance);
        }
        Collections.reverse(cmd.getStatementLines());
    }

    protected int calculateTotalBalance(List<WebCreditStatementLineCmdImpl> statementLines) {
        int balance = 0;
        for (WebCreditStatementLineCmdImpl statementLine : statementLines) {
            balance += calculateLineAmount(statementLine.getCreditAmount(), statementLine.getDebitAmount());
        }
        return -balance;
    }

    protected int calculateLineAmount(Integer creditAmount, Integer debitAmount) {
        return nullToZero(creditAmount) + nullToZero(debitAmount);
    }

    protected int nullToZero(Integer amount) {
        return amount == null ? 0 : amount;
    }

    protected String getMessage(String code) {
        return this.applicationContext.getMessage(code, new String[]{}, null, null);
    }
}
