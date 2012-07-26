package com.cbs.cbsmgr.utils;

/**
 * <p>Title: CONVERGENCE BUSINESS SYSTEM</p>
 * <p>Module: CBS CORE MODULE</p>
 * <p>Description: </p>
 * <p>Company: Road To Broadband Co., Ltd.</p>
 * @author not attributable
 *
 */

import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import org.apache.log4j.*;

public class TimeConvertor {

  public TimeConvertor() {}

  public static Timestamp getCurrentTimestamp() {
    Timestamp ts = new Timestamp(new Date().getTime());
    ts.setNanos(0);
    return ts;
  }

  public static java.sql.Date utilDate2sqlDate(java.util.Date date) {
    if (date != null){
      if (date.getYear() < 10) return null;
      else
      return new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
    }
    else return null;
  }

  public static java.sql.Timestamp utilDate2Timestamp(java.util.Date date){
    if (date != null){
      if (date.getYear() < 10) return null;
      else{
        return new java.sql.Timestamp(date.getYear(), date.getMonth(),
                                    date.getDate(), date.getHours(),
                                    date.getMinutes(), date.getSeconds(), 0);
      }
    }
    else return null;
  }

  public static java.sql.Date timestamp2Date(java.sql.Timestamp timestamp) {
    if ( timestamp != null)
    {
      return new java.sql.Date(timestamp.getYear(), timestamp.getMonth(),
                               timestamp.getDate());
    }
    else
      return null;
  }

  //Consider change its name to monthDiff
  public static double monthInterval(java.sql.Date fromDate,
                                     java.sql.Date toDate) {
    int symbol = 1;
    double partition = 0.0;
    if (toDate.getTime() < fromDate.getTime() ) {
      java.sql.Date tempDate = new java.sql.Date(fromDate.getTime());
      fromDate = toDate;
      toDate = tempDate;
      symbol = -1;
    }

    if (toDate.getYear() == fromDate.getYear() &&
        toDate.getMonth() == fromDate.getMonth()) {
      int iFromDate = fromDate.getDate();
      int iToDate = toDate.getDate();
      int DateTotal = getMonthDay(toDate);
      partition = (iToDate - iFromDate ) / ( (double) DateTotal);
    }
    else {
      int iFromDate = fromDate.getDate();
      int fromDateTotal = getMonthDay(fromDate);
      partition = (fromDateTotal - iFromDate ) / ( (double) fromDateTotal);

      int iToDate = toDate.getDate();
      int toDateTotal = getMonthDay(toDate);
      partition = partition + iToDate / ( (double) toDateTotal);
      int monthbetween = (toDate.getYear() * 12 + toDate.getMonth()) -
          (fromDate.getYear() * 12 + fromDate.getMonth() + 1);
      partition = partition + monthbetween;

    }

    return partition * symbol;

  }

  public static java.sql.Date setDate(java.sql.Date d, int day) {
    int totalDay = getMonthDay(d);
    java.sql.Date returnD = new java.sql.Date(d.getTime());
    if (day > totalDay) {
      returnD.setDate(totalDay);
    }
    else {
      returnD.setDate(day);
    }
    return returnD;
  }

  public static java.sql.Date advanceNMonths(java.sql.Date d, int months) {
    java.sql.Date tempD = new java.sql.Date(d.getTime());
    int totalDay = getMonthDay(tempD);
    int day = tempD.getDate();
    tempD.setDate(1);
    tempD.setMonth(tempD.getMonth() + months);
    if (totalDay == day) {
      day = 32;
    }
    return setDate(tempD, day);
  }

  public static java.sql.Date advanceNDays(Long days) {
    return new java.sql.Date(getCurrentDate().getTime() +
                             24 * 60 * 60 * 1000 * days.longValue());
  }

  public static java.sql.Date advanceNDays(java.sql.Date fromDate, Long days) {
    return new java.sql.Date(fromDate.getTime() +
                             24 * 60 * 60 * 1000 * days.longValue());
  }

  public static int getMonthDay(java.sql.Date d) {
    java.sql.Date tempDate = new java.sql.Date(d.getTime());
    tempDate.setDate(1);
    tempDate.setMonth(tempDate.getMonth() + 1);
    tempDate.setDate(tempDate.getDate() - 1);
    return tempDate.getDate();

  }

  public static java.sql.Date getTomorrow() {
    Date currDate = getCurrentDate();
    return new java.sql.Date(new Date(currDate.getYear(), currDate.getMonth(), currDate.getDate()).getTime() + 24 * 60 * 60 * 1000);

  }

  public static java.sql.Date getCurrentDate() {
    Date newDate = new Date();
    java.sql.Date currentDate = new java.sql.Date(newDate.getYear() , newDate.getMonth(),newDate.getDate() );
    return currentDate;
  }

  public static java.sql.Date getNextInvoiceDay(Long invoicePeriod) {

    java.sql.Date currentDate = new java.sql.Date( (new Date()).getTime());
    return getNextInvoiceDay(currentDate, invoicePeriod);

  }

  public static java.sql.Date getNextInvoiceDay(java.sql.Date currentDate,
                                                Long invoicePeriod) {

    Logger logger = Logger.getLogger("com.rtb.cbs.core.utils.TimeConvertor");

    Date curDate = new Date(currentDate.getYear(), currentDate.getMonth(),
                            currentDate.getDate());
    Date NID = null;
    int curYear = curDate.getYear();
    int curMonth = curDate.getMonth() + 1;

    logger.debug("Generating Next Invoice Day with " + (int) (curYear + 1900) +
                 "/" + (int) (curMonth) + "/" +
                 curDate.getDate());
    // current date is earlier than 25th day
    if (curDate.getDate() < 25) {

      logger.debug("Original date is earlier than 25th of the month");
      NID = getFirstDayOfNextMonth(curYear, curMonth);
      logger.debug("The NID is " + NID.toString());

    }
    else {
      logger.debug("Original date is later than 25th of the month");

      Date nextDay = getFirstDayOfNextMonth(curYear, curMonth);
      NID = addByMonth(nextDay, invoicePeriod.intValue());
      logger.debug("The NID is " + NID.toString());
    }

    return new java.sql.Date(NID.getTime());

  }

  public static java.sql.Date getEndDayOfPeriod(java.sql.Date NID,
                                                Long invoicePeriod) {

    java.sql.Date newNID = advanceNID(NID, invoicePeriod);
    java.sql.Date endDay = new java.sql.Date(newNID.getTime() -
                                             24 * 60 * 60 * 1000);
    return endDay;
  }

  public static java.sql.Date getOneDayBeforeNID(java.sql.Date NID) {
    java.sql.Date d = new java.sql.Date(NID.getTime() - 24 * 60 * 60 * 1000);
    return d;
  }

  public static java.sql.Date advanceNID(java.sql.Date NID, Long invoicePeriod) {

    Date newNID = addByMonth(NID, invoicePeriod.intValue());
    return new java.sql.Date(newNID.getTime());
  }

  public static long DayDiffer(java.sql.Date date1, java.sql.Date date2) {

    long differ = date1.getTime() - date2.getTime();
    return differ / (long) (24 * 60 * 60 * 1000);

  }

  private static Date getFirstDayOfNextMonth(int year, int month) {

    Date resultDay = null;

    if (month < 12) {
      Date temp = new Date();
      // Set temp to the fist day of the next month
      temp.setYear(year);
      temp.setMonth(month + 1 - 1);
      temp.setDate(1);
      resultDay = temp;
    }

    if (month == 12) {
      resultDay = new Date(year + 1, 1 - 1, 1);
    }

    return resultDay;
  }

  private static Date addByMonth(Date date, int monthAdd) {
    int year = date.getYear();
    int month = date.getMonth() + 1;

    int newMonth = month + monthAdd;
    int newYear = year + ( (newMonth - 1) / 12);
    newMonth = ( (newMonth - 1) % 12) + 1;

    return new Date(newYear, newMonth - 1, date.getDate());
  }

}
