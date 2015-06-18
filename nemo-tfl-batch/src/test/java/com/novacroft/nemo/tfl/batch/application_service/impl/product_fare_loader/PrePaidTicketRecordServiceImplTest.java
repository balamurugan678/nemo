package com.novacroft.nemo.tfl.batch.application_service.impl.product_fare_loader;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.novacroft.nemo.tfl.batch.domain.product_fare_loader.PrePaidTicketRecord;
import com.novacroft.nemo.tfl.batch.job.HibernateBatchJobUpdateStrategyServiceImplTest;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;

@RunWith(MockitoJUnitRunner.class)
public class PrePaidTicketRecordServiceImplTest {


    private static final String[] CSV_RECORD_OOD_PERIOD = "102,1-2 months Travelcard Zones 4 to 5,12/14/2002,No Discount,0,Zones 4 to 5,N,N,N,Y,Y,N,N,N,N,N,N,N,N,N,N,N,3-4 months,Y,  "
                    .split(",");
    private static final String[] CSV_RECORD_3_MONTH = "102,3 months Travelcard Zones 4 to 5,12/14/2002,No Discount,0,Zones 4 to 5,N,N,N,Y,Y,N,N,N,N,N,N,N,N,N,N,N,3 months,Y,1256.00"
                    .split(",");

    private static final String[] CSV_RECORD_7_DAY = "102,7 day Travelcard Zones 4 to 5,12/14/2002,No Discount,0,Zones 4 to 5,N,N,N,Y,Y,N,N,N,N,N,N,N,N,N,N,N,7 day,Y,1256.00"
                    .split(",");

    private static final String[] CSV_RECORD_1_MONTH = "102,1 Month Travelcard Zones 4 to 5,12/14/2002,No Discount,0,Zones 4 to 5,N,N,N,Y,Y,N,N,N,N,N,N,N,N,N,N,N,1 month,Y,1256.00"
                    .split(",");
    
    private static final String[] CSV_RECORD_BUS_PASS_1_MONTH = "102,1 Month Bus Pass Zones 4 to 5,12/14/2002,No Discount,0,Zones 4 to 5,N,N,N,Y,Y,N,N,N,N,N,N,N,N,N,N,N,1 month,Y,1256.00"
                    .split(",");
    private static final String[] CSV_RECORD_BUS_PASS_1_MONTH_NULL_DESCRIPTION = "102,,12/14/2002,No Discount,0,Zones 4 to 5,N,N,N,Y,Y,N,N,N,N,N,N,N,N,N,N,N,1 month,Y,1256.00"
            .split(",");

    @Test
    public void testCreatePrePaidRecordCreatesRecordForAnnual() {

        PrePaidTicketRecordServiceImpl prePaidTicketRecordServiceImpl = new PrePaidTicketRecordServiceImpl();

        PrePaidTicketRecord prePaidTicketRecord = prePaidTicketRecordServiceImpl
                        .createPrepaidRecord(HibernateBatchJobUpdateStrategyServiceImplTest.CSV_RECORD_1);

        assertTrue(prePaidTicketRecord != null);

    }

    @Test
    public void testCreatePrePaidRecordCreatesRecordForOddPeriod() {

        PrePaidTicketRecordServiceImpl prePaidTicketRecordServiceImpl = new PrePaidTicketRecordServiceImpl();

        PrePaidTicketRecord prePaidTicketRecord = prePaidTicketRecordServiceImpl.createPrepaidRecord(CSV_RECORD_OOD_PERIOD);

        assertTrue(prePaidTicketRecord != null);

    }

    @Test
    public void testCreatePrePaidRecordCreatesRecordForThreeMonthly() {

        PrePaidTicketRecordServiceImpl prePaidTicketRecordServiceImpl = new PrePaidTicketRecordServiceImpl();

        PrePaidTicketRecord prePaidTicketRecord = prePaidTicketRecordServiceImpl.createPrepaidRecord(CSV_RECORD_3_MONTH);

        assertTrue(prePaidTicketRecord != null);

    }

    @Test
    public void testCreatePrePaidRecordCreatesRecordFor7Day() {

        PrePaidTicketRecordServiceImpl prePaidTicketRecordServiceImpl = new PrePaidTicketRecordServiceImpl();

        PrePaidTicketRecord prePaidTicketRecord = prePaidTicketRecordServiceImpl.createPrepaidRecord(CSV_RECORD_7_DAY);

        assertTrue(prePaidTicketRecord != null);

    }

    @Test
    public void testCreatePrePaidRecordCreatesRecordForMonthly() {

        PrePaidTicketRecordServiceImpl prePaidTicketRecordServiceImpl = new PrePaidTicketRecordServiceImpl();

        PrePaidTicketRecord prePaidTicketRecord = prePaidTicketRecordServiceImpl.createPrepaidRecord(CSV_RECORD_1_MONTH);

        assertTrue(prePaidTicketRecord != null);

    }
    
    @Test
    public void testCreatePrePaidRecordCreatesRecordForMonthlyBusPass() {

        PrePaidTicketRecordServiceImpl prePaidTicketRecordServiceImpl = new PrePaidTicketRecordServiceImpl();

        PrePaidTicketRecord prePaidTicketRecord = prePaidTicketRecordServiceImpl.createPrepaidRecord(CSV_RECORD_BUS_PASS_1_MONTH);

        assertNotNull(prePaidTicketRecord);
        assertEquals(prePaidTicketRecord.getType(), ProductItemType.BUS_PASS.databaseCode());

    }
    
    @Test
    public void testCreatePrePaidRecordReturnsNullTicketType() {

        PrePaidTicketRecordServiceImpl prePaidTicketRecordServiceImpl = new PrePaidTicketRecordServiceImpl();

        PrePaidTicketRecord prePaidTicketRecord = prePaidTicketRecordServiceImpl.createPrepaidRecord(CSV_RECORD_BUS_PASS_1_MONTH_NULL_DESCRIPTION);

        assertNotNull(prePaidTicketRecord);
        assertEquals(null, prePaidTicketRecord.getType());

    }
    

}
