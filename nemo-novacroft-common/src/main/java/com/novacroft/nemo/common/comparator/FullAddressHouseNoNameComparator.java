package com.novacroft.nemo.common.comparator;

import static com.novacroft.nemo.common.constant.ComparatorConstant.SORT_AFTER;
import static com.novacroft.nemo.common.constant.ComparatorConstant.SORT_BEFORE;
import static com.novacroft.nemo.common.constant.ComparatorConstant.SORT_EQUALS;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.transfer.CommonAddressDTO;

/**
 * Comparator for sorting Full address by House No and Name
 */
@Component("fullAddressHouseNoNameComparator")
public class FullAddressHouseNoNameComparator implements Comparator<CommonAddressDTO> {
    private static final Pattern PATTERN = Pattern.compile("(\\D*)(\\d*)");
    @Override
    public int compare(CommonAddressDTO address1, CommonAddressDTO address2) {
        Matcher houseNameNumberMatcher1 = PATTERN.matcher(address1.getHouseNameNumber());
        Matcher houseNameNumberMatcher2 = PATTERN.matcher(address2.getHouseNameNumber());
        while (houseNameNumberMatcher1.find() && houseNameNumberMatcher2.find()) {
            int nonDigitCompare = houseNameNumberMatcher1.group(1).compareTo(houseNameNumberMatcher2.group(1));
            if (0 != nonDigitCompare) {
                return nonDigitCompare;
            }
            if (houseNameNumberMatcher1.group(2).isEmpty()) {
                return houseNameNumberMatcher2.group(2).isEmpty() ? SORT_EQUALS : SORT_BEFORE;
            } else if (houseNameNumberMatcher2.group(2).isEmpty()) {
                return SORT_AFTER;
            }
            BigInteger n1 = new BigInteger(houseNameNumberMatcher1.group(2));
            BigInteger n2 = new BigInteger(houseNameNumberMatcher2.group(2));
            int numberCompare = n1.compareTo(n2);
            if (0 != numberCompare) {
                return numberCompare;
            }
        }
        return houseNameNumberMatcher1.hitEnd() && houseNameNumberMatcher2.hitEnd() ? 0 :
            houseNameNumberMatcher1.hitEnd() ? SORT_BEFORE : SORT_AFTER;
    }

}