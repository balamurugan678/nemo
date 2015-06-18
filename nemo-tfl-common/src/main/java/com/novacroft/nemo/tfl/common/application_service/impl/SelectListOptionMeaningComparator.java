package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * Comparator for sorting Select List Options by meaning
 */
@Component("selectListOptionMeaningComparator")
public class SelectListOptionMeaningComparator implements Comparator<SelectListOptionDTO> {
    @Override
    public int compare(SelectListOptionDTO option1, SelectListOptionDTO option2) {
        return option1.getMeaning().compareTo(option2.getMeaning());
    }
}
