package com.novacroft.nemo.mock_cubic.data_service.impl;

import static com.novacroft.nemo.common.utils.StringUtil.isNotBlank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;
import com.novacroft.nemo.mock_cubic.converter.OysterCardPendingConverterImpl;
import com.novacroft.nemo.mock_cubic.data_access.OysterCardPendingDAO;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPendingDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPending;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPendingDTO;

@Service(value = "OysterCardPendingDataService")
@Transactional(readOnly = true)
public class OysterCardPendingDataServiceImpl extends BaseDataServiceImpl<OysterCardPending, OysterCardPendingDTO> implements
                OysterCardPendingDataService {

    private static final String PAY_AS_YOU_GO_LIMIT_KEY = "payAsYouGo.limit";
    private static final String MAX_PENDING_ITEMS_LIMIT_KEY = "maxPendingItemsPerPrestigeId";
    private Long PAY_AS_YOU_GO_LIMIT = 90L;
    private Long MAX_PENDING_ITEMS_LIMIT = 4L;

    @Autowired
    protected SystemParameterDataService systemParameterService;

    @Autowired
    public void setDao(OysterCardPendingDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(OysterCardPendingConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public OysterCardPending getNewEntity() {
        return new OysterCardPending();
    }

    @Override
    public List<OysterCardPendingDTO> findAllBy(String test) {
        return null;
    }

    @Override
    public OysterCardPendingDTO findByTest(String test) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OysterCardPendingDTO> findByCardNumber(String cardNumber) {
        final String hsql = "from OysterCardPending c where c.prestigeId = ?";
        List<OysterCardPending> results = dao.findByQuery(hsql, cardNumber);
        if (results != null) {
            return convertListToDTO(results);
        }
        return null;
    }

    public List<OysterCardPendingDTO> convertListToDTO(List<OysterCardPending> entities) {
        List<OysterCardPendingDTO> resultsDTO = new ArrayList<OysterCardPendingDTO>();
        if (entities.iterator().hasNext()) {
            for (OysterCardPending oysterCardPending : entities) {
                resultsDTO.add(this.converter.convertEntityToDto(oysterCardPending));
            }
        }
        return resultsDTO;
    }

    @PostConstruct
    public void init() {
        SystemParameterDTO PAYGLimitDTO = systemParameterService.findByCode(PAY_AS_YOU_GO_LIMIT_KEY);
        PAY_AS_YOU_GO_LIMIT = Long.valueOf(PAYGLimitDTO.getValue());
        SystemParameterDTO maxPendingLimitDTO = systemParameterService.findByCode(MAX_PENDING_ITEMS_LIMIT_KEY);
        MAX_PENDING_ITEMS_LIMIT = Long.valueOf(maxPendingLimitDTO.getValue());
    }

    @Override
    public OysterCardPendingDTO findByRequestSequenceNumber(Long requestSequenceNumber) {
        return findByRequestSequenceNumber(requestSequenceNumber, null);
    }

    @Override
    public OysterCardPendingDTO findByRequestSequenceNumber(Long originalRequestSequenceNumber, String prestigeId) {
        OysterCardPending exampleEntity1 = createOysterCardPending();
        OysterCardPending exampleEntity2 = createOysterCardPending();
        OysterCardPending exampleEntity3 = createOysterCardPending();

        if (isNotBlank(prestigeId)) {
            exampleEntity1.setPrestigeId(prestigeId);
            exampleEntity2.setPrestigeId(prestigeId);
            exampleEntity3.setPrestigeId(prestigeId);
        }

        OysterCardPending result = dao.findByExampleUniqueResult(exampleEntity1);
        if (result == null) {
            result = dao.findByExampleUniqueResult(exampleEntity2);
            if (result == null) {
                result = dao.findByExampleUniqueResult(exampleEntity3);
            }
        }

        return (result != null) ? this.converter.convertEntityToDto(result) : null;
    }

    protected OysterCardPending createOysterCardPending() {
        return new OysterCardPending();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Boolean isLimitExceededForPendingItems(Long prestigeId) {

        Query sumQuery = dao.createSQLQuery("select count(*) from MOCK_OYSTERCARDPENDING where prestigeID=:prestigeId");
        sumQuery.setParameter("prestigeId", prestigeId);
        List<BigDecimal> sumResult = sumQuery.list();
        Long count = sumResult.get(0).longValue();

        return (count != null && count >= MAX_PENDING_ITEMS_LIMIT);

    }

    @Override
    @SuppressWarnings("unchecked")
    public Boolean isBalanceTotalExceededForPendingItems(Long prestigeId, Long additionalPPVValue) {

        Long sum = 0L;
        Query sumQuery = dao.createSQLQuery("select sum(c.prepayvalue) from MOCK_OYSTERCARDPENDING c where prestigeID=:prestigeId");
        sumQuery.setParameter("prestigeId", prestigeId);
        List<BigDecimal> sumResult = sumQuery.list();
        if (sumResult.size() > 0 && sumResult.get(0) != null) {
            sum = sumResult.get(0).longValue();
        }

        Query query = dao.createSQLQuery("select c.balance from MOCK_OYSTERCARDPREPAYVALUE c where prestigeID=:prestigeId");
        Long balance = 0L;
        query.setParameter("prestigeId", prestigeId);
        try {
            List<BigDecimal> result = query.list();
            if (result.size() > 0 && result.get(0) != null) {
                balance = result.get(0).longValue();
            }
        } catch (HibernateException e) {
            // TODO this catches an issue with the DB that needs investigating. DB throws a
            // invalid number error, seems to be a DB config thing?
        }
        return (sum + balance + additionalPPVValue > PAY_AS_YOU_GO_LIMIT);

    }
}