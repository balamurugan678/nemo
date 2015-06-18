package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.tfl.common.constant.ConfigurationFile.TFL_COMMON_CASTOR_MAPPING;
import static com.novacroft.nemo.tfl.common.constant.ConfigurationFile.TFL_COMMON_CASTOR_MAPPING_CARD_UPDATE_PPT;
import static com.novacroft.nemo.tfl.common.constant.ConfigurationFile.TFL_COMMON_CASTOR_MAPPING_CARD_UPDATE_PPV;
import static com.novacroft.nemo.tfl.common.constant.ConfigurationFile.TFL_COMMON_CASTOR_MAPPING_GET_CARD;
import static com.novacroft.nemo.tfl.common.constant.ConfigurationFile.TFL_COMMON_CASTOR_MAPPING_CARD_REMOVE_UPDATE;
import static com.novacroft.nemo.tfl.common.constant.ConfigurationFile.TFL_COMMON_CASTOR_MAPPING_CARD_REMOVE_UPDATE_RESPONSE;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.novacroft.nemo.common.converter.impl.BaseXmlModelConverter;
import com.novacroft.nemo.common.exception.ApplicationServiceException;

/**
 * XML (as String) / model object converter implementation using Castor with Nemo TfL Common mapping
 */
public abstract class BaseTflCommonXmlModelConverter<MODEL> extends BaseXmlModelConverter<MODEL> {
    private static final Logger logger = LoggerFactory.getLogger(BaseTflCommonXmlModelConverter.class);
    @Autowired
    protected ApplicationContext applicationContext;

    @PostConstruct
    public void initialiseMapping() {
        try {
            List<URL> mappings = new ArrayList<URL>();
            mappings.add(applicationContext.getResource(TFL_COMMON_CASTOR_MAPPING).getURL());
            mappings.add(applicationContext.getResource(TFL_COMMON_CASTOR_MAPPING_CARD_UPDATE_PPT).getURL());
            mappings.add(applicationContext.getResource(TFL_COMMON_CASTOR_MAPPING_CARD_UPDATE_PPV).getURL());
            mappings.add(applicationContext.getResource(TFL_COMMON_CASTOR_MAPPING_GET_CARD).getURL());
            mappings.add(applicationContext.getResource(TFL_COMMON_CASTOR_MAPPING_CARD_REMOVE_UPDATE).getURL());
            mappings.add(applicationContext.getResource(TFL_COMMON_CASTOR_MAPPING_CARD_REMOVE_UPDATE_RESPONSE).getURL());
            this.setCastorMappingFileUrl(mappings);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }
}
