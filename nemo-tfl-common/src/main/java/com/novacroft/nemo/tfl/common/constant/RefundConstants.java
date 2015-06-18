package com.novacroft.nemo.tfl.common.constant;

public final class RefundConstants {
    
    public static enum  REFUND_REASON_KEY{START_DATE, END_DATE, REFUND_BASIS, USAGE_DURATION, EFFECTIVE_REFUND_DATE, CHARGE_PER_DAY, ADMIN_FEE, USAGE_PERIOD, CHARGEABLE_DAYS, ORIGINAL_TICKET_PRICE, FORMATTED_ORIGINAL_TICKET_PRICE, TRADED_TICKET_PRICE, FORMATTED_TRADED_TICKET_PRICE, ROUNDING};
    public enum UsageDuration {ONEMONTHORLESS, GREATERTHANAMONTH};
    public static final int TWELVE_MONTHS = 12;
    public static final int ONE_MONTH = 1;
    public static final int THREE_MONTHS = 3;
    public static final int SIX_MONTHS = 6;
    public static final String INTERMEDIATE_PERIOD_OF_NON_USE="Intermediate period of non-use";
    public static final int PREPAY_VALUE_INITIAL_ZERO=0;
    public static final int TFL_THIRTY_MULTIPLIER = 30;
    public static final double TFL_REFUND_MULTIPLIER = 3.84;
    public static final double TFL_ONE_FIFTH_MULTIPLIER = 0.2;
    public static final int DAYS_IN_WEEK = 7;
    public static final int DAYS_IN_TEMPLATE_MONTH = 30;
    public static final int DAYS_IN_YEAR = 365;
    public static final Integer OFFSETDAY = 1;
    public static final int ROUND_UP_DAYS_TO_WEEK_THRESHOLD = 4;
    public static final int HUNDRED_PRECISION_CONSTANT = 100;
    public static final int TEN_PRECISION_CONSTANT = 10;
    
    public static final String SEVENDAY="7Day";
    public static final String ONEMONTH="1Month";
    public static final String THREEMONTH="3Month";
    public static final String SIXMONTH="6Month";
    public static final String ANNUAL="Annual";
    public static final String OTHER="Other";
  
    public static final Integer SEVEN_DAYS = 7;
    public static final Integer SIX_WEEKS = 6;
    public static final Integer MAXIMUM_BACKDATEABLE_REFUND_PERIOD = SEVEN_DAYS * SIX_WEEKS;

    public static final String FAILED_CARD_REFUND_DISPLAY="Failed Card Refund";
    public static final String DESTROYED_CARD_REFUND_DISPLAY="Destroyed Card Refund";
    public static final String LOST_REFUND_DISPLAY="Lost Refund";
    public static final String STOLEN_REFUND_DISPLAY="Stolen Refund";
    public static final String CANCEL_AND_SURRENDER_REFUND_DISPLAY="Cancel And Surrender";
    public static final int TEN = 10;
    public static final float THIRTY_FLOAT = 30f;
    public static final String WEB_CREDIT = "Web Credit";
    public static final String ADHOC_LOAD = "Ad-hoc Load";
    public static final String PAYMENT_CARD = "Payment Card";
	public static final String ANONYMOUS_GOODWILL_REFUND_DISPLAY = "Anonymous Goodwill Refund";
	public static final String STANDALONE_GOODWILL_REFUND_DISPLAY = "StandAlone Goodwill Refund";
	
	public static final String USAGE_DURATION_MONTH = "Month";
    public static final String USAGE_DURATION_WEEK = "Week";
    public static final String USAGE_DURATION_DAY =  "Day";
    
	public static final String USAGE_DURATION_MONTHS = "Months";
	public static final String USAGE_DURATION_WEEKS = "Weeks";
	public static final String USAGE_DURATION_DAYS =  "Days";
	public static final int CUSTOM_PERIOD_OPTION_CODE = 7;
	
	public static final Long BACKDATED_OTHERREASON_VALUE = 2L;
	
    
    private RefundConstants(){
        
    }

}
