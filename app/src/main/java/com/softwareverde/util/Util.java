package com.softwareverde.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static Integer parseInt(String numberString) {
        try {
            return NumberFormat.getNumberInstance(java.util.Locale.US).parse(numberString.trim()).intValue();
        }
        catch (Exception e) {
            System.out.println("com.softwareverde.utopia.util :: ParseInt :: Invalid integer value: "+ numberString);
            return 0;
        }
    }
    public static Long parseLong(String numberString) {
        try {
            return NumberFormat.getNumberInstance(java.util.Locale.US).parse(numberString.trim()).longValue();
        }
        catch (Exception e) {
            System.out.println("com.softwareverde.utopia.util :: ParseLong :: Invalid long value: "+ numberString);
            return 0L;
        }
    }
    public static Float parseFloat(String numberString) {
        try {
            return NumberFormat.getNumberInstance(java.util.Locale.US).parse(numberString.trim()).floatValue();
        }
        catch (Exception e) {
            System.out.println("com.softwareverde.utopia.util :: ParseFloat :: Invalid float value: "+ numberString);
            return 0.0f;
        }
    }
    public static String formatNumberString(Integer number) {
        if (number == null) return "";

        return NumberFormat.getInstance().format(number);
    }
    public static String formatPercentString(Float percent) {
        if (percent == null) return "";

        return String.format("%.2f", percent);
    }
    // Truncates the minutes from an epoch timestamp.
    // Expects milliseconds (i.e. NOT currentTimeMillis / 1000L)
    public static long truncateMinutes(Long currentSeconds) {
        return (long) (((long) (currentSeconds / 60.0d / 60.0d / 1000.0d)) * 60.0d * 60.0d * 1000.0d);
    }

    public static int computeLevenshteinDistance (String s0, String s1) {
        int len0 = s0.length() + 1;
        int len1 = s1.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for(int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert  = cost[i] + 1;
                int cost_delete  = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost; cost = newcost; newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

    public static Integer[] initializeIntegerArray(int size) {
        Integer[] array = new Integer[size];
        for (int i=0; i<size; i++) {
            array[i] = new Integer(0);
        }
        return array;
    }

    public static String coalesce(String string) {
        return Util.coalesce(string, "");
    }
    public static Integer coalesce(Integer number) {
        return Util.coalesce(number, 0);
    }
    public static <T> T coalesce(T value, T defaultValue) {
        return (value != null ? value : defaultValue);
    }

    public interface AndroidIdCallback {
        void run(String androidId);
    }

    public static String parseValueBetweenTokens(String string, String startToken, String endToken) {
        final Integer end;
        final Integer begin;
        if ((begin = string.indexOf(startToken)) >= 0 && (end = string.indexOf(endToken, begin)) >= 0) {
            return string.substring(begin + startToken.length(), end);
        }

        return "";
    }

    public static String md5(final String s) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(s.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        }
        catch (java.security.NoSuchAlgorithmException e) { }
        return null;
    }

    // NOTE: Does not returns time with milliseconds..
    public static Long datetimeToTimestamp(final String datetime) {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            final Date date = dateFormat.parse(datetime);
            return date.getTime() / 1000L;
        }
        catch (Exception e) {
            return 0L;
        }
    }

    public static List<String> pregMatch(final String regex, final String haystack) {
        List<String> matches = new ArrayList<String>();
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(haystack);
        if (matcher.find()) {
            for (Integer i=0; i<matcher.groupCount(); ++i) {
                matches.add(matcher.group(i+1));
            }
        }
        else {
            // System.out.println("No Match: "+ regex +" :: "+ haystack);
        }
        return matches;
    }

    public static String unescapeString(final String input) {
        final StringBuilder stringBuilder = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch == '\\') {
                char nextChar = (i == input.length() - 1) ? '\\' : input.charAt(i + 1);
                // Octal escape?
                if (nextChar >= '0' && nextChar <= '7') {
                    String code = "" + nextChar;
                    i++;
                    if ((i < input.length() - 1) && input.charAt(i + 1) >= '0' && input.charAt(i + 1) <= '7') {
                        code += input.charAt(i + 1);
                        i++;
                        if ((i < input.length() - 1) && input.charAt(i + 1) >= '0' && input.charAt(i + 1) <= '7') {
                            code += input.charAt(i + 1);
                            i++;
                        }
                    }
                    stringBuilder.append((char) Integer.parseInt(code, 8));
                    continue;
                }
                switch (nextChar) {
                    case '\\':
                        ch = '\\';
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    case '\"':
                        ch = '\"';
                        break;
                    case '\'':
                        ch = '\'';
                        break;
                    // Hex Unicode: u????
                    case 'u':
                        if (i >= input.length() - 5) {
                            ch = 'u';
                            break;
                        }
                        int code = Integer.parseInt("" + input.charAt(i + 2) + input.charAt(i + 3) + input.charAt(i + 4) + input.charAt(i + 5), 16);
                        stringBuilder.append(Character.toChars(code));
                        i += 5;
                        continue;
                }
                i++;
            }
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }

    public static String limitConsecutiveNewlines(final String input) {
        return input.replaceAll("\\n[ \\t\\n]*\\n[ \\t]*", "\n\n"); // Limit the number of consecutive newlines to 2.
    }

    public static String streamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
