package com.parse.date;

import com.parse.Context;
import com.parse.Util;

import java.util.List;

import static com.parse.Util.list;

public abstract class DateTimeMatcher {
    abstract Util.MultiList<Calendar.Date, Time> matchInternal(String text);

    public static Util.MultiList<Calendar.Date, Time> match(String text, Context.TimeContext timeCtx, Context.DateTimeContext datetimeCtx) {
        Util.MultiList<Calendar.Date, Time> date_times = new Util.MultiList<>();
        List<DateTimeMatcher> matchers = matchers(timeCtx, datetimeCtx);
        for (DateTimeMatcher dateTimeMatcher : matchers) {
            Util.addAll(date_times, dateTimeMatcher.matchInternal(text));
        }
        return date_times;
    }

    static List<DateTimeMatcher> matchers(Context.TimeContext timeCtx, Context.DateTimeContext datetimeCtx) {
        List<DateTimeMatcher> dateTimeMatchers = list();
        if (datetimeCtx.datetimeReverse()) {
            dateTimeMatchers.add(new SingleTimeSingleDate());
        } else {
            dateTimeMatchers.add(new SingleDateSingleTime(timeCtx.timePm(), timeCtx.timeDoors()));
            dateTimeMatchers.add(new SingleDateSingleTimeAmPm());
            dateTimeMatchers.add(new SlashDotDateSingleTime());
        }
        return dateTimeMatchers;
    }
}