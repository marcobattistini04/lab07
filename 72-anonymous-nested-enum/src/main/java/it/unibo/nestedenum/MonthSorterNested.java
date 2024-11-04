package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Objects;


/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    public static enum Month {
        JANUARY(31), FEBRUARY(28), MARCH(31), APRIL(30), MAY(31), JUNE(30), 
        JULY(31), AUGUST(31), SEPTEMBER(30), OCTOBER(31), NOVEMBER(30), DECEMBER(31);

        private final int monthDays;

        private Month(final int n){
            this.monthDays = n;
        }
        
        public static Month fromString(String str) {
            Objects.requireNonNull(str);
            try {
                return valueOf(str);
            } catch (IllegalArgumentException e) {
                Month similarity = null;
                for(final Month montValue : values()) {
                    if(montValue.toString().toLowerCase().startsWith(str.toLowerCase())) {
                        if(similarity != null) {
                            throw new IllegalArgumentException("Ambiguous string passed as input.\n" + str + 
                            " could be both " + similarity.toString() + " and " + montValue.toString());
                        }

                        similarity = montValue;
                    }
                }
                if(similarity == null) {
                    throw new IllegalArgumentException("Impossible to find any similarity with any month with the string: " + str);
                }
                return similarity;
            }
        }
    }

    @Override
    public Comparator<String> sortByDays() {
       return new SortByDays();
    }

    private final static class SortByDays implements Comparator<String> {
        final static int POS = 1;
        final static int NEG = -1;
        final static int ZERO = 0;
        @Override
        public int compare(String o1, String o2) {
            final Month m1 = Month.fromString(o1);
            final Month m2 = Month.fromString(o2);
            if(m1.monthDays > m2.monthDays) {
                return POS;
            } else if(m1.monthDays < m2.monthDays) {
                return NEG;
            }
            return ZERO;
        }
        
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByOrder();
    }

    private final static class SortByOrder implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            final Month m1 = Month.fromString(o1);
            final Month m2 = Month.fromString(o2);
            return m1.compareTo(m2);
        }
    }

}
