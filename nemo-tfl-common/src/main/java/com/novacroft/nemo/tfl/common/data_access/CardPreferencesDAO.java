package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.CardPreferences;
import org.springframework.stereotype.Repository;

/**
 * TfL Card Preferences DAO implementation
 */
@Repository("cardPreferencesDAO")
public class CardPreferencesDAO extends BaseDAOImpl<CardPreferences> {
}
