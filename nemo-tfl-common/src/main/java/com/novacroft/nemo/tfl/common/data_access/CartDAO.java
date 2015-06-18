package com.novacroft.nemo.tfl.common.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.Cart;

/**
 * TfL cart data access class implementation.
 */
@Repository("cartDAO")
public class CartDAO extends BaseDAOImpl<Cart> {
    public Long getNextApprovalId() {
        return getNextSequenceNumber("approval_id_seq");
    }
    
    @Override
    public Cart findById(Long id) {
	final String hsql = "from Cart cart where cart.id = ?";
	return this.findByQueryUniqueResult(hsql, id);
    }
	
}
