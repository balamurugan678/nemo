package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.transfer.SelectListOptionDTO;

import java.util.List;

/**
 * Select list options data service spec
 */
public interface SelectListOptionDataService {
    List<SelectListOptionDTO> findOptionsByListId(Long listId);

    /**
     * @deprecated
     */
    List<SelectListOptionDTO> findOptionsWithMeaningsByListId(Long listId);
}
