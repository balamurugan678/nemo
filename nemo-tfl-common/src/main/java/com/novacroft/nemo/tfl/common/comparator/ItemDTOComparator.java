package com.novacroft.nemo.tfl.common.comparator;

import java.util.Comparator;

import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

public class ItemDTOComparator implements Comparator<ItemDTO> {

    @Override
    public int compare(ItemDTO itemDTO1, ItemDTO itemDTO2) {
	if (itemDTO1.getId() != null) { 
	    return itemDTO1.getId().compareTo(itemDTO2.getId());
	} else {
	    return -1;
	}
    }
}
