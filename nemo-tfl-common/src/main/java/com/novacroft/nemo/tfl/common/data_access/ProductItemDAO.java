package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.ProductItem;
import org.springframework.stereotype.Repository;

/**
 * TfL product item data access class implementation.
 */
@Repository("productItemDAO")
public class ProductItemDAO extends BaseDAOImpl<ProductItem> {

}
