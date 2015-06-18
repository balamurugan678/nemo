package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.data_cache.CachingDataService;
import com.novacroft.nemo.common.domain.Content;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.common.transfer.ContentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Content data service implementation
 */
public class ContentDataServiceImpl extends BaseDataServiceImpl<Content, ContentDTO>
        implements ContentDataService, CachingDataService {
    static final Logger logger = LoggerFactory.getLogger(ContentDataServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public ContentDTO findByLocaleAndCode(String code, String locale) {

        final String hSql = "select c from Content c where locale = ? and lower(c.code) = ?";
        List<Content> results = dao.findByQuery(hSql, locale, code.toLowerCase());

        if (results.size() > 1) {
            String msg = String.format(CommonPrivateError.MORE_THAN_ONE_RECORD_FOR_LOCALE_CODE.message(), locale, code);
            logger.error(msg);
            throw new DataServiceException(msg);
        }
        if (results.iterator().hasNext()) {
            return this.converter.convertEntityToDto(results.iterator().next());
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Date findLatestCreatedDateTime() {
        final String hsql = "select max(c.createdDateTime) from Content c";
        return findLatest(hsql);
    }

    @Override
    @Transactional(readOnly = true)
    public Date findLatestModifiedDateTime() {
        final String hsql = "select max(c.modifiedDateTime) from Content c";
        return findLatest(hsql);
    }

    @SuppressWarnings("unchecked")
    protected Date findLatest(final String hsql) {
        List<Date> results = dao.findByQuery(hsql);
        if (results.iterator().hasNext()) {
            return (Date) results.iterator().next();
        } 
        return null;
    }

    @Override
    public Content getNewEntity() {
        return new Content();
    }
}
