package com.novacroft.nemo.tfl.services.transfer;

import java.util.List;

public class ListResult<DTO> extends AbstractBase{

    private List<DTO> resultList;

    public List<DTO> getResultList() {
        return resultList;
    }

    public void setResultList(List<DTO> resultList) {
        this.resultList = resultList;
    }

}
