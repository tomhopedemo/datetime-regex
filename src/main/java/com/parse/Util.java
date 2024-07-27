package com.parse;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.parse.date.M_Static.M_EMPTY;
import static java.lang.Integer.parseInt;
import static java.lang.Math.round;
import static java.lang.String.valueOf;

public class Util {
    public static final List<String> HYPHENS = Util.list("-", "\u2010", "\u2013", "\u2014", "\u002d");

    public static String substring(int startInclusive, int endExclusive, String input) {
        int length = input.length();
        return input.substring(Math.max(0, Math.min(startInclusive, length)), Math.min(endExclusive, length));
    }

    public static List<Integer> integerList(int length) {
        return integerList(0, length - 1);
    }

    public static List<Integer> integerList(int startInclusive, int endInclusive) {
        List<Integer> toReturn = new ArrayList<>();
        for (int i = startInclusive; i <= endInclusive; i++) {
            toReturn.add(i);
        }
        return toReturn;
    }

    public static Integer proximalIndex(String whole, int index, String searchTerm, int radius) {
        if (Util.empty(whole)) return null;
        if (Util.empty(searchTerm)) return null;
        int startIndex = Math.max(0, index - radius);
        int endIndex = Math.min(whole.length() - 1, index + radius);
        String substring = Util.substring(startIndex, endIndex, whole);
        int indexOfSearchTerm = substring.indexOf(searchTerm);
        if (indexOfSearchTerm == -1) return null;
        return indexOfSearchTerm + startIndex;
    }

    public static String substringRemoveLast(StringBuilder sb, Integer numToRemove) {
        if (sb.length() == 0) {
            return "";
        } else {
            if (numToRemove >= sb.length()) return "";
            return sb.substring(0, sb.length() - numToRemove);
        }
    }

    public static boolean empty(MultiList multiList) {
        return (multiList == null || empty(multiList.underlying));
    }

    public static <A> boolean empty(A[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean empty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean empty(Multi multi) {
        if (multi == null) return true;
        if (multi.a == null && multi.b == null) return true;
        return false;
    }

    public static boolean empty(Collection collection) {
        return (collection == null || collection.size() == 0 || (collection.size() == 1 && collection.iterator().next() == null));
    }

    public static boolean empty(MapList mapList) {
        return (mapList == null || mapList.mapList == null || empty(mapList.mapList));
    }

    public static boolean empty(StringMutable stringMutable) {
        if (stringMutable == null) return true;
        return empty(stringMutable.string);
    }

    public static boolean empty(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static void addAll(MultiList existing, MultiList newElements) {
        if (existing == null || newElements == null) return;
        addAll(existing.underlying, newElements.underlying, null);
    }

    public static <T> void addAll(Collection<T> existing, Collection<T> newElements, Integer limit) {
        if (existing == null || newElements == null) return;
        if (limit != null) {
            int remaining = limit - existing.size();
            if (remaining <= 0) return;
            List<T> sublist = sublist(new ArrayList<T>(newElements), remaining);
            existing.addAll(sublist);
        } else {
            existing.addAll(newElements);
        }
    }

    public static <T> List<T> sublist(List<T> list, int num) {
        if (list == null) return null;
        return list.subList(0, Math.min(num, list.size()));
    }

    public static <T> List<T> sublist(List<T> list, int fromInc, int toExc) {
        if (list == null) return null;
        if (list.size() == 0 || fromInc >= list.size()) return list();
        return list.subList(fromInc, Math.min(toExc, list.size()));
    }

    public static boolean empty(Integer integer) {
        return (integer == null || integer.equals(0));
    }

    public static <T> T last(List<T> collection) {
        return collection.get(collection.size() - 1);
    }

    public static <E> HashSet<E> set(E... objects) {
        HashSet<E> objectsSet = new HashSet<>();
        for (E object : objects) {
            objectsSet.add(object);
        }
        return objectsSet;
    }

    public static boolean empty(NList collection) {
        return (collection == null || collection.size() == 0 || (collection.size() == 1 && collection.underlying.iterator().next() == null));
    }

    public static <T> void safeAdd(Collection<T> existing, Collection<T> newItems) {
        if (existing == null || newItems == null) return;
        existing.addAll(newItems);
    }

    public static Integer minDifferenceDirectional(List<Integer> collection, int value) {
        if (empty(collection)) return null;
        List<Integer> distancesBetween = list();
        for (Integer element : collection) {
            int difference = value - element;
            if (difference >= 0) distancesBetween.add(difference);
        }
        return min(distancesBetween);
    }

    public static Integer min(Collection<Integer> collection) {
        if (empty(collection)) return null;
        Integer min = null;
        for (Integer candidate : collection) {
            if (min == null || candidate < min) min = candidate;
        }
        return min;
    }

    public static Integer minDifference(List<Integer> collection, int value) {
        if (empty(collection)) return null;
        List<Integer> distancesBetween = list();
        collection.forEach(avoidIndex -> distancesBetween.add(Math.abs(value - avoidIndex)));
        return min(distancesBetween);
    }

    public static boolean overlap(Integer startA, Integer endA, Integer startB, Integer endB) {
        return !(startA > endB || startB > endA);
    }


    public static String trim(String string) {
        if (string == null) return null;
        return string.trim().replaceAll("^\u00A0", "").trim();
    }

    public static <E> ArrayList<E> list(E... objects) {
        ArrayList<E> objectsList = new ArrayList<>();
        for (E object : objects) {
            objectsList.add(object);
        }
        return objectsList;
    }

    public static String safeNull(String value) {
        return value == null ? "" : value;
    }

    public static String padleft(String input, Character pad, Integer maxChars) {
        if (input == null || pad == null) return null;
        if (input.length() >= maxChars) return input;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxChars - input.length(); i++) {
            sb.append(pad);
        }
        sb.append(input);
        return sb.toString();
    }


    public static <S, T> MultiList multilist(Multi<S, T> m) {
        MultiList<S, T> multilist = new MultiList<>();
        multilist.add(m);
        return multilist;
    }

    public static List<Integer> between(int startInclusive, int endInclusive) {
        List<Integer> toReturn = list();
        for (int i = startInclusive; i <= endInclusive; i++) {
            toReturn.add(i);
        }
        return toReturn;
    }

    public static NList split(String string, String delimiterRegex) {
        if (string == null) return null;
        List<String> list = new ArrayList<>(Arrays.asList(string.split(delimiterRegex)));
        NList nlist = new NList();
        nlist.underlying = list;
        nlist.splitDelimiter = delimiterRegex;
        return nlist;
    }

    public static <T> List<T> safeNull(List<T> a) {
        return empty(a) ? new ArrayList<T>() : a;
    }

    public static <T> T get(List<T> list, int index) {
        if (empty(list)) return null;
        if (index >= list.size()) return null;
        if (index == -1) return null;
        return list.get(index);
    }

    public static String string(Collection collection, String delimiter) {
        if (collection == null) return null;
        return out(collection, delimiter);
    }

    public static String substringRemoveLast(StringBuilder sb) {
        if (sb.length() == 0) {
            return "";
        } else {
            return sb.substring(0, sb.length() - 1);
        }
    }

    public static <T> String out(Collection<T> collection, String separator) {
        StringBuilder sb = new StringBuilder();
        for (T object : collection) {
            if (object != null) {
                String str = object.toString();
                if (str != null) {
                    sb.append(str);
                }
            }
            sb.append(separator);
        }
        return Util.substringRemoveLast(sb, separator.length());
    }

    public static <T> String string(T[] collection) {
        if (collection == null) return null;
        return out(Arrays.asList(collection), " ");
    }

    public static boolean between(int middle, int aInclusive, int bInclusive) {
        return middle >= Math.min(aInclusive, bInclusive) && middle <= Math.max(aInclusive, bInclusive);
    }


    public static <T> T getElement(List<T> set, T object) {
        return get(set, set.indexOf(object));
    }

    public static Matcher matcher(String regex, String text) {
        return Pattern.compile(regex).matcher(text);
    }

    public static <T> String string(Collection<T> collection) {
        if (collection == null) return null;
        return out(collection, " ");
    }

    public static String cleanInt(String raw) {
        return valueOf(parseInt(raw.trim()));
    }

    public static Integer diff(Integer a, Integer b) {
        if (a == null || b == null) return null;
        return Math.abs(a - b);
    }

    public static String opt(String input) {
        if (Util.empty(input) || M_EMPTY.equals(input)) {
            return input;
        }
        return input + "?";
    }

    public static class StringMutable {
        public String string;

        public StringMutable(String string) {
            this.string = string;
        }

        public void set(String string) {
            this.string = string;
        }
    }

    public static class NList {
        String splitDelimiter;
        List<String> underlying;

        public String get(int index) {
            if (underlying.size() < index + 1) {
                return null;
            }
            return underlying.get(index);
        }

        public String reconstruct(List<String> list, String joiner) {
            if (list == null) return null;
            if (joiner == null) joiner = splitDelimiter;
            if (joiner.equals("\\s+")) {
                return string(list, " ");
            } else if (joiner.equals("\\(")) {
                return string(list, "(");
            } else {
                return string(list, joiner);
            }
        }

        public void set(int index, String string) {
            underlying.set(index, string);
        }

        public int size() {
            if (underlying == null) return 0;
            return underlying.size();
        }
    }

    public enum Lang {ENG, DEU}

    public static class Multi<A, B> implements Serializable {
        public A a;
        public B b;

        public Multi(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Multi<?, ?> multi = (Multi<?, ?>) o;
            if (!Objects.equals(a, multi.a)) return false;
            return Objects.equals(b, multi.b);
        }

        public int hashCode() {
            int result = a != null ? a.hashCode() : 0;
            result = 31 * result + (b != null ? b.hashCode() : 0);
            return result;
        }
    }

    public static class MapList<K extends Comparable<? super K>, T> implements Serializable {
        public Map<K, ArrayList<T>> mapList;

        public MapList() {
            this.mapList = new HashMap<>();
        }

        public K getKey(T listelement) {
            for (K key : mapList.keySet()) {
                List<T> list = mapList.get(key);
                if (empty(list)) continue;
                if (list.contains(listelement)) return key;
            }
            return null;
        }

        public void addAll(K key, Collection<T> elements) {
            mapList.putIfAbsent(key, list());
            mapList.get(key).addAll(elements);
        }

        public List<T> get(K key) {
            return mapList.get(key);
        }

        public List<T> listvalues() {
            List<T> aggregated = list();
            for (K key : mapList.keySet()) {
                List<T> list = mapList.get(key);
                if (empty(list)) continue;
                aggregated.addAll(list);
            }
            return aggregated;
        }
    }

    public static class MultiList<S, T> {
        public List<Multi<S, T>> underlying = list();

        public MultiList() {
        }

        public void add(Multi<S, T> multi) {
            underlying.add(multi);
        }

        public void add(S a, T b) {
            underlying.add(new Multi<>(a, b));
        }

        public Multi<S, T> get(Integer index) {
            return underlying.get(index);
        }
    }
}