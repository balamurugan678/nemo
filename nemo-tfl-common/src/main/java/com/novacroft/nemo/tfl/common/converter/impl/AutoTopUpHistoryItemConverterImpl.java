package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.common.constant.DateConstant.TIME_TO_MINUTE_PATTERN;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.constant.AutoTopUpActivityType;
import com.novacroft.nemo.tfl.common.constant.AutoTopUpStatusType;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpHistoryItemDTO;

/**
 * Transform between auto top-up history item entity and DTO.
 */
@Component(value = "autoTopUpHistoryItemConverter")
public class AutoTopUpHistoryItemConverterImpl {
    @Autowired
    protected LocationDataService locationDataService;
    
    public List<AutoTopUpHistoryItemDTO> convertEntitiesToDTOs(List<Object[]> rows) {
        List<AutoTopUpHistoryItemDTO> autoLoads = new ArrayList<AutoTopUpHistoryItemDTO>();
        SimpleDateFormat sdf = new SimpleDateFormat(SHORT_DATE_PATTERN + " " + TIME_TO_MINUTE_PATTERN);
        for (Object[] row : rows) {
            String activity = (String)row[0];
            String location = locationDataService.getActiveLocationById(((BigDecimal)row[1]).intValue()).getName();
            String activityDateTime = sdf.format(((Timestamp)row[2]));
            Integer amount = ((BigDecimal)row[3]).intValue();
            String settlementDateTime = sdf.format(((Timestamp)row[4]));
            String autoLoadStatus = getAutoLoadStatus((String)row[5], activity);
            AutoTopUpHistoryItemDTO item = new AutoTopUpHistoryItemDTO(activity, location, activityDateTime, amount, settlementDateTime, autoLoadStatus);
            autoLoads.add(item);
        }
        return autoLoads;
    }

    protected String getAutoLoadStatus(String settlementStatus, String autoTopUpActivity) {
        if (autoTopUpActivity.equalsIgnoreCase(AutoTopUpActivityType.SET_UP.getCode())) {
            return AutoTopUpStatusType.AUTO_TOP_UP_ADDED.getCode();
        } else if (autoTopUpActivity.equalsIgnoreCase(AutoTopUpActivityType.CONFIG_CHANGE.getCode())) {
            return AutoTopUpStatusType.AUTO_TOP_UP_CHANGED.getCode();
        } else if (autoTopUpActivity.equalsIgnoreCase(AutoTopUpActivityType.AUTOLOAD.getCode()) && (settlementStatus.equalsIgnoreCase(SettlementStatus.ACCEPTED.code()))) {
            return AutoTopUpStatusType.AUTO_TOP_UP_SUCCEEDED.getCode();
        } else if (autoTopUpActivity.equalsIgnoreCase(AutoTopUpActivityType.AUTOLOAD.getCode()) && (settlementStatus.equalsIgnoreCase(SettlementStatus.FAILED.code()))) {
            return AutoTopUpStatusType.AUTO_TOP_UP_FAILED.getCode();
        } else {
            return AutoTopUpStatusType.AUTO_TOP_UP_CANCELLED.getCode();
        }
    }

}
