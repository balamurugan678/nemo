package com.novacroft.nemo.common.data_service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.novacroft.nemo.common.converter.PAFAddressConverter;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.common.transfer.CommonAddressDTO;
import com.novacroft.phoenix.service.paf.bean.PAFFullAddress;
import com.novacroft.phoenix.service.paf.client.Search;
import com.novacroft.phoenix.service.paf.client.SearchServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

import static com.novacroft.nemo.common.utils.StringUtil.field;

/**
 * Royal Mail Postcode Address File (PAF) data service wrapper implementation
 *
 * @see <a href="http://www.royalmail.com/sites/default/files/docs/pdf/programmers_guide_edition_7_v5.pdf">programmers_guide_edition_7_v5.pdf</a>
 */
public class PAFDataServiceImpl implements PAFDataService {
    static final Logger logger = LoggerFactory.getLogger(PAFDataServiceImpl.class);
    protected String searchServiceEndpoint;
    @Autowired
    protected PAFAddressConverter pafAddressConverter;

    @Cacheable(cacheName = "findAddressesForPostcode")
    @Override
    public List<CommonAddressDTO> findAddressesForPostcode(String postcode) {
        SearchServiceLocator searchServiceLocator = null;
        Search searchService = null;
        PAFFullAddress[] addresses = null;
        try {
            searchServiceLocator = new SearchServiceLocator();
            searchServiceLocator.setSearchEndpointAddress(this.searchServiceEndpoint);
            searchService = searchServiceLocator.getSearch();
            addresses = searchService.fullAddressSearch(getOutwardCode(postcode), getInwardCode(postcode));
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            throw new DataServiceException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new DataServiceException(e.getMessage(), e);
        }
        if (addresses != null) {
            return pafAddressConverter.convertEntitiesToDtos(addresses);
        }
        return Collections.<CommonAddressDTO>emptyList();
    }

    @Override
    public void setSearchServiceEndpoint(String endpoint) {
        this.searchServiceEndpoint = endpoint;
    }

    protected String getInwardCode(String postcode) {
        return field(postcode, ' ', 2);
    }

    protected String getOutwardCode(String postcode) {
        return field(postcode, ' ', 1);
    }
}
