package com.novacroft.nemo.tfl_database.test_data;

import com.novacroft.nemo.common.utils.OysterCardNumberUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

import static com.novacroft.nemo.tfl_database.test_data.FixtureConstant.TITLES;

public class RandomFixtureGenerator {
    private Random random = new Random();

    public String getRandomUserName(int i) {
        return String.format("%s-%s", RandomStringUtils.randomAlphabetic(8), i).toLowerCase();
    }

    public String getRandomTitle() {
        return TITLES[getRandomTitleIndex()];
    }

    public String getRandomFirstName() {
        return StringUtil.initCap(RandomStringUtils.randomAlphabetic(8));
    }

    public String getRandomInitials() {
        return RandomStringUtils.randomAlphabetic(1).toUpperCase();
    }

    public String getRandomLastName(int i) {
        return StringUtil.initCap(String.format("%s-%s", RandomStringUtils.randomAlphabetic(16), i));
    }

    public String getRandomHouseNameNumber() {
        return RandomStringUtils.randomNumeric(3);
    }

    public String getRandomStreet() {
        return StringUtil.initCap(RandomStringUtils.randomAlphabetic(16));
    }

    public String getRandomTown() {
        return StringUtil.initCap(RandomStringUtils.randomAlphabetic(16));
    }

    public String getRandomPostcode() {
        return String.format("%s%s %s%s", RandomStringUtils.randomAlphabetic(2), RandomStringUtils.randomNumeric(2),
                RandomStringUtils.randomNumeric(1), RandomStringUtils.randomAlphabetic(2)).toUpperCase();
    }

    public String getRandomPhoneNumber() {
        return RandomStringUtils.randomNumeric(11);
    }

    public String[] getRandomOysterCardNumbers(int i) {
        return new String[]{OysterCardNumberUtil.getFullCardNumber(400000001L + i)};
    }

    private int getRandomTitleIndex() {
        return this.random.nextInt(TITLES.length);
    }
}
