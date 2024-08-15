package parse;

import parse.date.*;

import java.util.*;
import java.util.regex.Matcher;

import static parse.Util.*;
import static parse.date.Weekdays.WEEKDAYS_ORDER;
import static java.util.Collections.sort;

public abstract class DateMatcher {
    protected abstract DateMeta matchInternal(String text);

    public static List<parse.date.Calendar.Date> constructIntermediateDates(parse.date.Calendar.Date parsedSimpleFrom, parse.date.Calendar.Date parsedSimpleTo) {
        return constructIntermediateDates(parsedSimpleFrom, parsedSimpleTo, false);
    }

    public static List<parse.date.Calendar.Date> constructIntermediateDates(parse.date.Calendar.Date parsedSimpleFrom, parse.date.Calendar.Date parsedSimpleTo, boolean withWeekday) {
        List<parse.date.Calendar.Date> dates = list();
        for (parse.date.Calendar.CalendarDate calendarDate : listDates(parsedSimpleFrom, parsedSimpleTo)) {
            parse.date.Calendar.Date date = new parse.date.Calendar.Date(calendarDate.dateDay, calendarDate.dateMonth, calendarDate.dateYear);
            if (withWeekday) {
                date.dayOfWeek = calendarDate.dayOfWeek;
            }
            dates.add(date);
        }
        sort(dates);
        return dates;
    }

    static List<parse.date.Calendar.CalendarDate> listDates(parse.date.Calendar.Date from, parse.date.Calendar.Date to) {
        List<parse.date.Calendar.CalendarDate> dates = list();
        if (parse.date.Calendar.indexOf(from) == -1 || parse.date.Calendar.indexOf(to) == -1) return dates;
        if (parse.date.Calendar.indexOf(from) <= parse.date.Calendar.indexOf(to)) {
            for (int i = parse.date.Calendar.indexOf(from); i < parse.date.Calendar.indexOf(to) + 1; i++) {
                dates.add(parse.date.Calendar.get(i));
            }
        }
        return dates;
    }

    public static void getIndexPairs(String text, DateMeta meta, Matcher matcher) {
        getIndexPairs(text, meta, matcher.start(), matcher.end());
    }

    public static void getIndexPairs(String text, DateMeta dateMeta, Integer startIndex, Integer endIndex) {
        dateMeta.indexPairsInclExcl.add(new Util.Multi<>(startIndex, endIndex));
        addProximalDateIndex(text, dateMeta, startIndex);
    }

    public static String cleanText(String text) {
        return text.replaceAll("\u2021", " ").replaceAll("\u00A0", " ");
    }

    static void addProximalDateIndex(String text, DateMeta meta, int startIndex) {
        Integer dateIndex = Util.proximalIndex(text, startIndex, "date", 10);
        if (dateIndex != null) {
            meta.indexPairsInclExcl.add(new Util.Multi<>(dateIndex, dateIndex + 4));
        }
    }

    public abstract static class DayTimeMatcher {
        public abstract Map<String, Time> match(String text);

        public static List<Integer> weekdayBetweenIndices(String weekdayOne, String weekdayTwo) {
            List<Integer> weekdayIndices;
            if (WEEKDAYS_ORDER.indexOf(weekdayOne) < 0 || WEEKDAYS_ORDER.indexOf(weekdayTwo) < 0) return null;
            if (WEEKDAYS_ORDER.indexOf(weekdayTwo) >= WEEKDAYS_ORDER.indexOf(weekdayOne)) {
                weekdayIndices = Util.between(WEEKDAYS_ORDER.indexOf(weekdayOne), WEEKDAYS_ORDER.indexOf(weekdayTwo));
            } else {
                weekdayIndices = Util.between(0, WEEKDAYS_ORDER.indexOf(weekdayTwo));
                weekdayIndices.addAll(Util.between(WEEKDAYS_ORDER.indexOf(weekdayOne), 6));
            }
            return weekdayIndices;
        }
    }
}