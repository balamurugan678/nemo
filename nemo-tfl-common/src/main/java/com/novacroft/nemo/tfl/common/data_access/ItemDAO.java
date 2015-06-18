package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.Item;
import org.springframework.stereotype.Repository;

/**
 * TfL item data access class implementation.
 */
@Repository("itemDAO")
public class ItemDAO extends BaseDAOImpl<Item> {

}
