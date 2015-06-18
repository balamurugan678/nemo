package com.novacroft.nemo.common.application_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.FIVE_MINUTES_IN_SECONDS;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.comparator.SystemParameterComparator;
import com.novacroft.nemo.common.data_cache.CachingDataService;
import com.novacroft.nemo.common.data_cache.SystemParameterCache;
import com.novacroft.nemo.common.data_cache.impl.SystemParameterCacheImpl;
import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;


/**
 * <p>System Parameter application service implementation.</p>
 * <p>Service to make system parameters available in the application.  There are two potential sources for system parameters:
 * properties file or system parameters table.  The properties file has precedence over the database table.  Properties are
 * accessed via the application environment.</p>
 * <p>Expect this service to be configured as a bean in the Spring application context.</p>
 * <p>This service will cache the parameters if configured appropriately - see doc on mutators.  Note the cache only includes
 * parameter values (not purposes).</p>
 * <p>System parameter consumers in the application should inject this service and then use one of the public methods.</p>
 */
public class SystemParameterServiceImpl implements SystemParameterService {
    private static final Logger logger = LoggerFactory.getLogger(SystemParameterServiceImpl.class);

    protected Boolean useCache = Boolean.TRUE;
    protected Integer cacheRefreshIntervalInSeconds = FIVE_MINUTES_IN_SECONDS;
    protected SystemParameterDataService systemParameterDataService;
    protected SystemParameterCache cache;

    @Autowired(required = false)
    protected Properties applicationProperties;
    public SystemParameterServiceImpl() {
    }

    @PostConstruct
    public void initialiseCache() {
        this.cache =
                new SystemParameterCacheImpl(cacheRefreshIntervalInSeconds, (CachingDataService) systemParameterDataService);
    }

    @Override
    public String getParameterValue(String code) {
        logger.debug("Retrieving property : " + code);
        String value = null;
        if (isProperty(code)) {
            return getParameterValueFromProperties(code);
        }
        if (isCacheInUse()) {
            value = getParameterValueFromCache(code);
        }

        if (value == null) {
            value = getParameterValueFromDB(code);
        }
        if (value == null) {
            logger.error("Unable to find property : " + code);
        }
        return value;
    }

    @Override
    public Integer getIntegerParameterValue(String code) {
        String parameterValue = getParameterValue(code);
        return (parameterValue != null) ? Integer.valueOf(parameterValue) : null;
    }

    @Override
    public Float getFloatParameterValue(String code) {
        String parameterValue = getParameterValue(code);
        return (parameterValue != null) ? Float.valueOf(parameterValue) : null;
    }
    
    @Override
    public Boolean getBooleanParameterValue(String code) {
        String parameterValue = getParameterValue(code);
        Boolean booleanValue = BooleanUtils.toBooleanObject(parameterValue);
        return BooleanUtils.toBoolean(booleanValue);
    }

    @Override
    public String getParameterPurpose(String code) {
        return getSystemParameterFromDB(code).getPurpose();
    }

    protected boolean isProperty(String code) {
        return (this.applicationProperties != null) ? isNotBlank(this.applicationProperties.getProperty(code)) : null;
    }

    protected boolean isCacheInUse() {
        return this.useCache;
    }

    protected String getParameterValueFromProperties(String code) {
        return (this.applicationProperties != null) ? this.applicationProperties.getProperty(code) : null;
    }

    protected String getParameterValueFromCache(String code) {
        return this.cache.getValue(code);
    }

    protected String getParameterValueFromDB(String code) {
        return getSystemParameterFromDB(code).getValue();
    }

    protected SystemParameterDTO getSystemParameterFromDB(String code) {
        return this.systemParameterDataService.findByCode(code);
    }

    /**
     * Mutator to inject the System Parameter data service
     *
     * @param systemParameterDataService System Parameter data service
     */
    public void setSystemParameterDataService(SystemParameterDataService systemParameterDataService) {
        this.systemParameterDataService = systemParameterDataService;
    }

    /**
     * <p>Controls whether the parameters should be cached.</p>
     * <p>Each request will result in a separate database access if the cache is not enabled.</p>
     * <p>Recommend that this is only set to false in development environments!</p>
     *
     * @param useCache Set to <code>true</code> to cache the parameters.  Defaults to <code>true</code>.
     */
    public void setUseCache(Boolean useCache) {
        this.useCache = useCache;
    }

    /**
     * <p>Sets the length of time in seconds between cache refreshes.</p>
     *
     * @param cacheRefreshIntervalInSeconds Length of time between cache refreshes.  Defaults to five minutes.
     */
    public void setCacheRefreshIntervalInSeconds(Integer cacheRefreshIntervalInSeconds) {
        this.cacheRefreshIntervalInSeconds = cacheRefreshIntervalInSeconds;
    }
    
    @Override
    public List<SystemParameterDTO> getAllProperties() {
        final List<SystemParameterDTO> parameters = new ArrayList<SystemParameterDTO>();
        parameters.addAll(getFileProperties());
        parameters.addAll(getDbProperties());
        Collections.sort(parameters, new SystemParameterComparator());
        return parameters;
    }
    
    public List<SystemParameterDTO> getDbProperties() {
        return this.systemParameterDataService.findAll();
    }
    
    public List<SystemParameterDTO> getFileProperties() {
        Set<String> propertyNames = this.applicationProperties.stringPropertyNames();
        List<SystemParameterDTO> parameters = new ArrayList<SystemParameterDTO>();
        for (Iterator<String> iterator = propertyNames.iterator(); iterator.hasNext();) {
            String name = iterator.next();
            SystemParameterDTO property = new SystemParameterDTO(name, getParameterValue(name), "");
            parameters.add(property);
        }
        return parameters;
    }
    
    @Override
    public void saveProperties(List<SystemParameterDTO> parameters) {
        saveDbProperties(parameters);
    }
    
    public void saveDbProperties(List<SystemParameterDTO> parameters) {
        List<SystemParameterDTO> dbProperties = getDbProperties();
        List<SystemParameterDTO> savingProperties = new ArrayList<SystemParameterDTO>();
        for(SystemParameterDTO dbParameter : dbProperties) {
            for(SystemParameterDTO parameter : parameters) {
                if ( dbParameter.getCode().equals(parameter.getCode())) {
                    parameter.setId(dbParameter.getId());
                    parameter.setPurpose(dbParameter.getPurpose());
                    savingProperties.add(parameter);
                    break;
                }
            }
        }
        this.systemParameterDataService.createOrUpdateAll(savingProperties);
    }
}
