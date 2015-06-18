package com.novacroft.nemo.common.comparator;

import java.util.Comparator;

import com.novacroft.nemo.common.transfer.SystemParameterDTO;

public class SystemParameterComparator implements Comparator<SystemParameterDTO> {

    @Override
    public int compare(SystemParameterDTO parameter1, SystemParameterDTO parameter2) {
        return (parameter1.getCode().toUpperCase()).compareTo(parameter2.getCode().toUpperCase());
    }

}
