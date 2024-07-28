package com.parse;

import java.util.List;
import java.util.Map;

import static com.parse.Util.*;

public class Context {

    public static class LanguageContext {
        List<Util.Lang> languages;
        String country;

        public static Map<String, List<Util.Lang>> countryLanguages;

        static {
            countryLanguages = Map.of("germany", list(Util.Lang.DEU));
        }

        public LanguageContext(String country, List<Util.Lang> languages) {
            this.country = country;
            this.languages = languages;
        }

        public boolean lang(Util.Lang language) {
            return languages.contains(language) || languagesForCountry().contains(language);
        }

        List<Util.Lang> languagesForCountry() {
            return safeNull(countryLanguages.get(country));
        }
    }

    public static class TimeContext {
        boolean timePm;
        boolean timeDoors;

        public TimeContext(boolean timePm, boolean timeDoors) {
            this.timePm = timePm;
            this.timeDoors = timeDoors;
        }

        public boolean timePm() {
            return timePm;
        }

        public boolean timeDoors() {
            return timeDoors;
        }
    }

    public static class DateTimeContext {
        boolean datetimeReverse;

        DateTimeContext(boolean datetimeReverse) {
            this.datetimeReverse = datetimeReverse;
        }

        public boolean datetimeReverse() {
            return datetimeReverse;
        }
    }
}
