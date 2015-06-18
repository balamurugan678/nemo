package com.novacroft.nemo.mock_cubic.data_service.impl;

import com.novacroft.nemo.mock_cubic.domain.AutoLoadChange;
import com.novacroft.nemo.tfl.common.data_service.impl.AutoLoadChangeSettlementDataServiceImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * CUBIC Mock Auto Load Change Data Service
 */
@Service("autoLoadChangeDataService")
public class AutoLoadChangeDataServiceImpl extends AutoLoadChangeSettlementDataServiceImpl
        implements AutoLoadChangeDataService {

    protected static final int SEVEN_DAYS_IN_MILLISECONDS = 1000 * 60 * 60 * 24 * 7;

    @Override
    public List<AutoLoadChange> findRequestedAutoLoadChangeRecords() {
        String hsql = "select c.cardNumber, o.stationId, s.requestSequenceNumber, s.autoLoadState" +
                " from AutoLoadChangeSettlement s, Card c, Order o" +
                " where s.status = ? and c.id = s.cardId and o.id = s.orderId" +
                " order by c.cardNumber, s.requestSequenceNumber";
        List records = dao.findByQuery(hsql, "Requested");
        return records == null ? Collections.<AutoLoadChange>emptyList() : convertRecords(records);
    }

    protected List<AutoLoadChange> convertRecords(List records) {
        List<AutoLoadChange> autoLoadChanges = new ArrayList<AutoLoadChange>();
        for (Object record : records) {
            autoLoadChanges.add(convertRecord((Object[]) record));
        }
        return autoLoadChanges;
    }

    protected AutoLoadChange convertRecord(Object[] record) {
        return new AutoLoadChange((String) record[0], String.valueOf((long) record[1]), getSevenDaysInFuture(),
                String.valueOf((int) record[2]), "1", String.valueOf((int) record[3]), "OK", "0");
    }

    protected String getSevenDaysInFuture() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date now = new Date();
        Date sevenDaysInFuture = new Date();
        sevenDaysInFuture.setTime(now.getTime() + SEVEN_DAYS_IN_MILLISECONDS);
        return simpleDateFormat.format(sevenDaysInFuture);
    }
}
