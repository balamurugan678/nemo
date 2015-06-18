package com.novacroft.nemo.tfl.common.comparator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class ItemDTOComparatorTest {
    protected static Long ITEM_ID_1 = 10L;
    protected static Long ITEM_ID_2 = 20L;

    @Test
    public void shouldSortBefore() {
        ItemDTOComparator comparator = new ItemDTOComparator();
        assertTrue(comparator.compare(getTestItemDTO1(), getTestItemDTO2()) < 0);
    }
    
    @Test
    public void shouldSortBeforeIfIdNull() {
        ItemDTOComparator comparator = new ItemDTOComparator();
        assertTrue(comparator.compare(getTestItem(null), getTestItemDTO2()) < 0);
    }

    @Test
    public void shouldSortAfter() {
        ItemDTOComparator comparator = new ItemDTOComparator();
        assertTrue(comparator.compare(getTestItemDTO2(), getTestItemDTO1()) > 0);
    }

    protected ItemDTO getTestItemDTO1() {
        return getTestItem(ITEM_ID_1);
    }

    protected ItemDTO getTestItemDTO2() {
        return getTestItem(ITEM_ID_2);
    }

    protected ItemDTO getTestItem(Long id) {
        ItemDTO itemDTO = new ProductItemDTO();
        itemDTO.setId(id);
        return itemDTO;
    }
}
