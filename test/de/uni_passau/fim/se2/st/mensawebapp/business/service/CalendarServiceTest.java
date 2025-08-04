package de.uni_passau.fim.se2.st.mensawebapp.business.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalendarServiceTest {

    private final LocalDate today = LocalDate.now();

    @Test
    void test_getWeekNumber_today() {
        assertEquals(CalendarService.getWeekNumber(today), today.get(ChronoField.ALIGNED_WEEK_OF_YEAR));
    }

    @Test
    void test_getYear_today() {
        assertEquals(CalendarService.getYear(today), today.getYear());
    }
}
