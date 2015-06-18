package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.converter.impl.AdHocLoadSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.BACSSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.ChequeSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.converter.impl.SettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.SettlementDAO;
import com.novacroft.nemo.tfl.common.data_service.SettlementDataService;
import com.novacroft.nemo.tfl.common.domain.AdHocLoadSettlement;
import com.novacroft.nemo.tfl.common.domain.AutoLoadChangeSettlement;
import com.novacroft.nemo.tfl.common.domain.BACSSettlement;
import com.novacroft.nemo.tfl.common.domain.ChequeSettlement;
import com.novacroft.nemo.tfl.common.domain.PaymentCardSettlement;
import com.novacroft.nemo.tfl.common.domain.Settlement;
import com.novacroft.nemo.tfl.common.domain.WebAccountCreditSettlement;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

/**
 * Settlement data service implementation
 */
@Service(value = "settlementDataService")
@Transactional(readOnly = true)
public class SettlementDataServiceImpl extends BaseDataServiceImpl<Settlement, SettlementDTO> implements SettlementDataService {

    @Autowired
    protected AdHocLoadSettlementConverterImpl adHocLoadSettlementCovverter;
    @Autowired
    protected BACSSettlementConverterImpl bacsSettlementConverter;
    @Autowired
    protected ChequeSettlementConverterImpl chequeSettlementConverter;

    @Autowired
    public void setDao(SettlementDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(SettlementConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Settlement getNewEntity() {
        return new Settlement();
    }

    @Override
    public SettlementDTO create(SettlementDTO settlementDTO) {
        settlementDTO.setId(((SettlementDAO) dao).getNextSequenceNumber("SETTLEMENT_SEQ"));
        return createOrUpdate(settlementDTO);
    }

    @Override
    public SettlementDTO findBySettlementId(Long settlementId) {
        final int firstItem = 0;
        Settlement settlement = new Settlement();
        settlement.setOrderId(settlementId);
        List<Settlement> settlements = dao.findByExample(settlement);
        return settlements.isEmpty() ? null : convert(settlements.get(firstItem)).get(firstItem);
    }

    @Override
    public List<SettlementDTO> findByOrderId(Long orderNumber) {
        Settlement settlement = new Settlement();
        settlement.setOrderId(orderNumber);
        List<Settlement> settlements = dao.findByExample(settlement);
        if (settlements.size() > 0) {
            return convert(settlements);
        } else {
            return null;
        }
    }

    @Override
    public List<SettlementDTO> findPolymorphicChildTypeSettlementByOrderId(Long orderId) {
        Settlement settlement = new Settlement();
        settlement.setOrderId(orderId);
        List<Settlement> settlements = dao.findByExample(settlement);
        List<SettlementDTO> childSettlements = new ArrayList<SettlementDTO>();
        if (settlements.size() > 0) {
            for (Settlement settlementItem : settlements) {
                childSettlements.add(convertSettlement(settlementItem));
            }

            return childSettlements;
        } else {
            return null;
        }
    }

    protected SettlementDTO convertSettlement(Settlement settlement) {
        if (settlement instanceof WebAccountCreditSettlement) {
            return convert((WebAccountCreditSettlement) settlement);
        }
        if (settlement instanceof AdHocLoadSettlement) {
            return convert((AdHocLoadSettlement) settlement);
        }
        if (settlement instanceof PaymentCardSettlement) {
            return convert((PaymentCardSettlement) settlement);
        }
        if (settlement instanceof BACSSettlement) {
            return convert((BACSSettlement) settlement);
        }
        if (settlement instanceof ChequeSettlement) {
            return convert((ChequeSettlement) settlement);
        }
        if (settlement instanceof AutoLoadChangeSettlement) {
            return convert((AutoLoadChangeSettlement) settlement);
        }
        return null;
    }

    protected ChequeSettlementDTO convert(ChequeSettlement settlement) {
        return (ChequeSettlementDTO) chequeSettlementConverter.convertEntityToDto(settlement);
    }

    protected BACSSettlementDTO convert(BACSSettlement settlement) {
        return (BACSSettlementDTO) bacsSettlementConverter.convertEntityToDto(settlement);
    }

    protected PaymentCardSettlementDTO convert(PaymentCardSettlement settlement) {
        PaymentCardSettlementDTO paymentCardSettlementDTO = new PaymentCardSettlementDTO();
        return (PaymentCardSettlementDTO) Converter.convert(settlement, paymentCardSettlementDTO);
    }

    protected WebCreditSettlementDTO convert(WebAccountCreditSettlement settlement) {
        WebCreditSettlementDTO webAccountCreditSettlementDTO = new WebCreditSettlementDTO();
        return (WebCreditSettlementDTO) Converter.convert(settlement, webAccountCreditSettlementDTO);
    }

    protected AutoLoadChangeSettlementDTO convert(AutoLoadChangeSettlement settlement) {
        AutoLoadChangeSettlementDTO autoLoadChangeSettlement = new AutoLoadChangeSettlementDTO();
        return (AutoLoadChangeSettlementDTO) Converter.convert(settlement, autoLoadChangeSettlement);
    }

    protected AdHocLoadSettlementDTO convert(AdHocLoadSettlement settlement) {
        return (AdHocLoadSettlementDTO) adHocLoadSettlementCovverter.convertEntityToDto(settlement);
    }

    @Override
    public List<SettlementDTO> findAllRefundSettlementsInPast12Months(Long customerId) {
        final String hsql = "select co.* from Customer cu , Order co ,settlement Se where se.id = co.customerId AND  se.settlementDate >= ADD_MONTHS(to_date(SYSDATE,'dd-mon-yy'),-12)and se.amount < 0 and co.cardId = ? and cu.id = ?";

        return convert(dao.findByQuery(hsql, customerId));
    }
}
