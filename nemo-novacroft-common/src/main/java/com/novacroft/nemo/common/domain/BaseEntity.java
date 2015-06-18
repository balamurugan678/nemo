package com.novacroft.nemo.common.domain;

import java.util.Date;

/**
 * <p>
 * Base specification for all domain classes.
 * <p/>
 * <p>
 * This specification effectively requires all entity classes to have the following attributes as a minimum:
 * </p>
 * <ul>
 * <li><code>id</code> - surrogate primary key</li>
 * <li><code>createdUserId</code> - who created the record</li>
 * <li><code>createdDateTime</code> - date and time record was created on</li>
 * <li><code>modifiedUserId</code> - who last modified the record</li>
 * <li><code>modifiedDateTime</code> - date and time record was last modified on</li>
 * </ul>
 */

public interface BaseEntity {
    Long getId();

    void setId(final Long id);

    String getCreatedUserId() ;
    
	void setCreatedUserId(String createdUserId);
	
    Date getCreatedDateTime();

	void setCreatedDateTime(Date createdDateTime) ;

	String getModifiedUserId();

	void setModifiedUserId(String modifiedUserId) ;
	
	Date getModifiedDateTime() ;

	void setModifiedDateTime(Date modifiedDateTime);
}
