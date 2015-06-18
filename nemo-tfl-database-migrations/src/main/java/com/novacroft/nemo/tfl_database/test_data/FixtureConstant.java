package com.novacroft.nemo.tfl_database.test_data;

public final class FixtureConstant {

    public static final int USER_NAME_INDEX = 0;
    public static final int TITLE_INDEX = 1;
    public static final int FIRST_NAME_INDEX = 2;
    public static final int INITIALS_INDEX = 3;
    public static final int LAST_NAME_INDEX = 4;
    public static final int HOUSE_NAME_NUMBER_INDEX = 5;
    public static final int STREET_INDEX = 6;
    public static final int TOWN_INDEX = 7;
    public static final int POSTCODE_INDEX = 8;
    public static final int CARD_INDEX = 9;
    public static final int HOME_PHONE_INDEX = 10;
    public static final int MOBILE_PHONE_INDEX = 11;

    public static final String[][] FIXED_TEST_USER_DATA = new String[][]{
            {"nemo", "Mr", "Billy", "T", "Fish", "Unit 10, Cirrus Park", "Lower Farm Road", "Moulton Park", "NN3 6UR",
                    "030000000515", "01604111111", "07555111111"},
            {"007", "Mr", "James", "", "Bond", "85", "Albert Embankment", "London", "SE1 7TP", "002000000109,030000000139",
                    "01604222222", "07555222222"},
            {"john", "Mr", "John", "E", "Turner", "7", "Drapery", "Northampton", "NN1 2ET", "002000000228,030000000258",
                    "01604333333", "07555333333"},
            {"kylie", "Ms", "Kylie", "", "Minogue", "1", "King's Rd", "London", "SW1W 8AB", "002000000347,030000000377",
                    "01604444444", "07555444444"},
            {"richard", "Mr", "Richard", "", "Whittington", "The Mansion House", "", "London", "EC4N 8BH", "030000000991",
                    "01604555555", "07555555555"},
            {"charles", "Mr", "Charles", "", "Babbage", "Salisbury Court", "Fleet Street", "London", "EC4Y 8AA", "030000001010",
                    "01604666666", "07555666666"},
            {"michael", "Mr", "Michael", "", "Faraday", "Paddington Station", "Praed Street", "London", "W2 6AA",
                    "030000001129", "01604777777", "07555777777"},
            {"samuel", "Mr", "Samuel", "", "Pepys", "Kings Cross Station", "Pancras Rd", "London", "N1C 4TB", "030000001248",
                    "01604888888", "07555888888"}};

    public static final String[] TITLES = new String[]{"Mr", "Mrs", "Ms"};
    public static final Integer WEB_CREDIT_VALUE = -12345;
    public static final String WEB_CREDIT_SETTLEMENT_METHOD = "WebAccountCredit";
    protected static final String USER_ID = "Installer";
}
