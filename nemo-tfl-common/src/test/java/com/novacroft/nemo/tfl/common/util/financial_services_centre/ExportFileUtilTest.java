package com.novacroft.nemo.tfl.common.util.financial_services_centre;

import com.novacroft.nemo.test_support.ChequeSettlementTestUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExportFileUtilTest {

    @Test
    public void shouldFormatDateForFinancialServicesCentreExport() {
        assertEquals(ChequeSettlementTestUtil.AUG_19_FSC_FORMAT_STRING,
                ExportFileUtil.formatDateForFinancialServicesCentreExport(DateTestUtil.getAug19()));
    }
}