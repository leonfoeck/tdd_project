package de.uni_passau.fim.se2.st.mensawebapp.business.service;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

/** A service for calendar computations. */
public final class CalendarService {

  private CalendarService() {}

  /**
   * Provides the week number for a given date.
   *
   * @param pDate The date
   * @return The week number for that date
   */
  public static int getWeekNumber(final LocalDate pDate) {
    return pDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
  }

  /**
   * Provides the year for a given date.
   *
   * @param pDate The date
   * @return The year of the date
   */
  public static int getYear(final LocalDate pDate) {
    return pDate.getYear();
  }
}
