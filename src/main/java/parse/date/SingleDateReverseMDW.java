package parse.date;

import parse.DateMatcher;
import parse.Util;

import java.util.List;
import java.util.regex.Matcher;

import static parse.Util.list;
import static parse.date.Calendar.defaultYearFull;
import static parse.date.M_Month.M_MONTH_ENG;
import static parse.date.M_Static.M_DAY;
import static parse.date.M_Static.SPACES;
import static parse.date.M_Weekday.M_WEEKDAY_ENG;
import static parse.date.Months.MONTHS_STANDARD_ENG;

public class SingleDateReverseMDW extends DateMatcher {
    List<Calendar.Date> dates = list();

    public DateMeta matchInternal(String text) {
        text = cleanText(text);
        String month_day_weekday = "^" + M_MONTH_ENG + SPACES + M_DAY + SPACES + M_WEEKDAY_ENG + SPACES;
        DateMeta date_internal = new DateMeta();
        Matcher matcher = Util.matcher(month_day_weekday, text);
        while (matcher.find()) {
            getIndexPairs(text, date_internal, matcher);
            Calendar.Date date = new Calendar.Date();
            date.dateDay = matcher.group(2);
            date.dateMonth = MONTHS_STANDARD_ENG.get(matcher.group(1));
            date.dateYear = defaultYearFull(date.dateMonth);
            date.indexPairs.add(new Util.Multi<>(matcher.start(), matcher.end()));
            date.note = this.getClass().getSimpleName();
            dates.add(date);
        }
        date_internal.dateList = dates;
        return date_internal;
    }
}