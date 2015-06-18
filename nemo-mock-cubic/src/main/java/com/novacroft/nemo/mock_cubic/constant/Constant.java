package com.novacroft.nemo.mock_cubic.constant;

import static com.novacroft.nemo.tfl.common.constant.PageCommand.CMD_SUFFIX;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.TARGET_ACTION;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.NAME_VALUE_SEPARATOR;
import static com.novacroft.nemo.tfl.common.constant.PageUrl.URL_SUFFIX;
import static com.novacroft.nemo.tfl.common.constant.PageView.VIEW_SUFFIX;

/**
 * General constants.
 */
public final class Constant {
    public static final int NUM_ROWS = 8;
    public static final String ENCODING = "UTF-8";
    public static final String PRESTIGE_ID = "PrestigeID";

    // Pages
    public static final String HOME_PAGE = "Home";
    public static final String AUTO_LOAD_CHANGE_PAGE = "AutoLoadChange";
    public static final String AUTO_LOAD_PERFORMED_PAGE = "AutoLoadPerformed";
    public static final String ADD_CARD_PREPAY_TICKET_RESPONSE_PAGE = "AddCardPrePayTicketResponse";
    public static final String ADD_CARD_UPDATE_PAGE = "AddCardUpdate";

    // Views
    public static final String HOME_VIEW = HOME_PAGE + VIEW_SUFFIX;
    public static final String AUTO_LOAD_CHANGE_VIEW = AUTO_LOAD_CHANGE_PAGE + VIEW_SUFFIX;
    public static final String AUTO_LOAD_PERFORMED_VIEW = AUTO_LOAD_PERFORMED_PAGE + VIEW_SUFFIX;
    public static final String ADD_CARD_PREPAY_TICKET_RESPONSE_VIEW = ADD_CARD_PREPAY_TICKET_RESPONSE_PAGE + VIEW_SUFFIX;
    public static final String ADD_CARD_UPDATE_VIEW = ADD_CARD_UPDATE_PAGE + VIEW_SUFFIX;

    // Page URLs
    public static final String HOME_URL = HOME_PAGE + URL_SUFFIX;
    public static final String AUTO_LOAD_CHANGE_URL = AUTO_LOAD_CHANGE_PAGE + URL_SUFFIX;
    public static final String AUTO_LOAD_PERFORMED_URL = AUTO_LOAD_PERFORMED_PAGE + URL_SUFFIX;
    public static final String ADD_CARD_PREPAY_TICKET_RESPONSE_URL = ADD_CARD_PREPAY_TICKET_RESPONSE_PAGE + URL_SUFFIX;
    public static final String ADD_CARD_UPDATE_URL = ADD_CARD_UPDATE_PAGE + URL_SUFFIX;

    // Command Classes
    public static final String DEFAULT_COMMAND_NAME = CMD_SUFFIX;

    // Target Actions
    public static final String TARGET_ACTION_ADD_ROWS = TARGET_ACTION + NAME_VALUE_SEPARATOR + "addRows";
    public static final String TARGET_ACTION_GENERATE_ROWS = TARGET_ACTION + NAME_VALUE_SEPARATOR + "generateRows";
    public static final String TARGET_ACTION_GET_CSV = TARGET_ACTION + NAME_VALUE_SEPARATOR + "getCsv";

    // Mime Type
    public static final String CSV_MIME_TYPE = "text/csv;charset=utf-8";

    public static final String CUBIC_DATE_PATTERN = "dd/MM/yyyy";
    public static final String CUBIC_DATE_AND_TIME_PATTERN = "dd/MM/yyyy hh:mm:ss";
    public static final String GET_CARD_MAPPING_WITH_EMPTY_TAGS =
            "classpath:nemo-tfl-common-castor-mapping-get-card-with-empty-tags.xml";

    private Constant() {
    }
}
