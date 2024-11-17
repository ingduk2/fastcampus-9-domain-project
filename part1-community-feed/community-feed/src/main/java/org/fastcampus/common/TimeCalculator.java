package org.fastcampus.common;

import java.time.LocalDate;

public class TimeCalculator {

    private TimeCalculator() {

    }

    public static LocalDate getDateDaysAgo(int daysAgo) {
        LocalDate now = LocalDate.now();
        return now.minusDays(daysAgo);
    }
}
