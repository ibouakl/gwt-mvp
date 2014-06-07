package com.gwt.ui.client;

import java.util.Arrays;
import java.util.List;

/**
 * Date locale support for the {@link SimpleDateParser}. You are encouraged to
 * extend this class and provide implementations for other locales. 
 * @author <a href="mailto:g.georgovassilis@gmail.com">George Georgovassilis</a>
 *
 */
public class DateLocale {
  public final static String TOKEN_DAY_OF_WEEK = "E";

  public final static String TOKEN_DAY_OF_MONTH = "d";

  public final static String TOKEN_MONTH = "M";

  public final static String TOKEN_YEAR = "y";

  public final static String TOKEN_HOUR_12 = "h";

  public final static String TOKEN_HOUR_24 = "H";

  public final static String TOKEN_MINUTE = "m";

  public final static String TOKEN_SECOND = "s";
  
  public final static String TOKEN_MILLISECOND = "S";
  
  public final static String TOKEN_AM_PM = "a";

  public final static String AM = "AM";

  public final static String PM = "PM";

  public final static List<String> SUPPORTED_DF_TOKENS = Arrays.asList(new String[] {
          TOKEN_DAY_OF_WEEK, TOKEN_DAY_OF_MONTH, TOKEN_MONTH, TOKEN_YEAR,
          TOKEN_HOUR_12, TOKEN_HOUR_24, TOKEN_MINUTE, TOKEN_SECOND,
          TOKEN_AM_PM });

  public String[] MONTH_LONG = { "January", "February", "March", "April",
          "May", "June", "July", "August", "September", "October",
          "November", "December" };

  public String[] MONTH_SHORT = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
          "Jul", "Aug", "Sept", "Oct", "Nov", "Dec" };

  public String[] WEEKDAY_LONG = { "Sunday", "Monday", "Tuesday",
          "Wednesday", "Thursday", "Friday", "Saturday" };

  public String[] WEEKDAY_SHORT = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri",
          "Sat" };

  public static String getAM() {
      return AM;
    }

  public static String getPM() {
      return PM;
    }

  public String[] getWEEKDAY_LONG() {
    return WEEKDAY_LONG;
  }

  public String[] getWEEKDAY_SHORT() {
    return WEEKDAY_SHORT;
  }

}
