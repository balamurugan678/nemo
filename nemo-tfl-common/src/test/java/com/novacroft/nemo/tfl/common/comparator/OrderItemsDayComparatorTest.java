package com.novacroft.nemo.tfl.common.comparator;

import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug20;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.transfer.OrderItemsDayDTO;

public class OrderItemsDayComparatorTest {

	 @Test
	    public void shouldSortBefore() {
		 OrderItemsDayComparator comparator = new OrderItemsDayComparator();
	        assertTrue(comparator.compare(getTestOrderItemsDayOnAug20(), getTestOrderItemsDayOnAug19())<0);
	    }

	    @Test
	    public void shouldSortAfter() {
	        OrderItemsDayComparator comparator = new OrderItemsDayComparator();
	        assertTrue(comparator.compare(getTestOrderItemsDayOnAug19(), getTestOrderItemsDayOnAug20())>0);
	    }

	    protected OrderItemsDayDTO getTestOrderItemsDayOnAug19() {
	        return getTestOrderItemsDay(getAug19());
	    }

	    protected OrderItemsDayDTO getTestOrderItemsDayOnAug20() {
	        return getTestOrderItemsDay(getAug20());
	    }

	    protected OrderItemsDayDTO getTestOrderItemsDay(Date orderDate) {
	    	OrderItemsDayDTO orderDay = new OrderItemsDayDTO(orderDate, null);
	        orderDay.setOrderDate(orderDate);
	        return orderDay;
	    }
}
