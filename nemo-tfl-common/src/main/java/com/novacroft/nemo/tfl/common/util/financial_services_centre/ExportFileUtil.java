package com.novacroft.nemo.tfl.common.util.financial_services_centre;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.common.utils.DateUtil;

import java.util.Date;

/**
 * Export file utilities
 */
public final class ExportFileUtil {

    public static String formatDateForFinancialServicesCentreExport(Date value) {
        return DateUtil.formatDate(value, DateConstant.SHORT_DATE_PATTERN);
    }

    private ExportFileUtil() {
    }
}
