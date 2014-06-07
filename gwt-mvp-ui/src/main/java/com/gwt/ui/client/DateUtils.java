package com.gwt.ui.client;

import java.util.Date;

/**
 * 
 * @author ibouakl
 *
 */
public class DateUtils {

	/**
     * Compares two date with a precision of one second.
     * 
     * @param baseDate
     *            The base date
     * @param afterDate
     *            The date supposed to be after.
     * @return True if the afterDate is indeed after the baseDate.
     */
    public static boolean after(final Date baseDate, final Date afterDate) {
        if ((baseDate == null) || (afterDate == null)) {
            throw new IllegalArgumentException(
                    "Can't compare the dates, at least one of them is null");
        }

        final long baseTime = baseDate.getTime() / 1000;
        final long afterTime = afterDate.getTime() / 1000;
        return baseTime < afterTime;
    }
    
    /**
     * Compares two date with a precision of one second.
     * 
     * @param baseDate
     *            The base date
     * @param beforeDate
     *            The date supposed to be before.
     * @return True if the beforeDate is indeed before the baseDate.
     */
    public static boolean before(final Date baseDate, final Date beforeDate) {
        if ((baseDate == null) || (beforeDate == null)) {
            throw new IllegalArgumentException(
                    "Can't compare the dates, at least one of them is null");
        }

        final long baseTime = baseDate.getTime() / 1000;
        final long beforeTime = beforeDate.getTime() / 1000;
        return beforeTime < baseTime;
    }

    /**
     * Compares two date with a precision of one second.
     * 
     * @param baseDate
     *            The base date
     * @param otherDate
     *            The other date supposed to be equals.
     * @return True if both dates are equals.
     */
    public static boolean equals(final Date baseDate, final Date otherDate) {
        if ((baseDate == null) || (otherDate == null)) {
            throw new IllegalArgumentException(
                    "Can't compare the dates, at least one of them is null");
        }

        final long baseTime = baseDate.getTime() / 1000;
        final long otherTime = otherDate.getTime() / 1000;
        return otherTime == baseTime;
    }
	
}
