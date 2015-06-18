package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.Product;
import org.springframework.stereotype.Repository;

/**
 * TfL product data access class implementation.
 */
@Repository("productDAO")
public class ProductDAO extends BaseDAOImpl<Product> {

}
