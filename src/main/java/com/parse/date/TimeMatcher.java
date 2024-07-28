package com.parse.date;

import com.parse.Context;
import com.parse.Util;

import java.util.List;

import static com.parse.date.M_Static.SPACESO;

public abstract class TimeMatcher {
    abstract List<Time> match(Util.StringMutable clean);

    List<Time> match(Util.StringMutable clean, boolean exact_match) {
        return match(clean);
    }

    static final String M_TIME = "([0-9]{1,2})" + "[:|\\.]" + "([0-5][0|5])";
    static final String M_TIME_AM = M_TIME + SPACESO + "(am|pm|p\\.m\\.)";
    static final String M_TIME_AMO = M_TIME + SPACESO + "(am|pm)?";
}