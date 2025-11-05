package team.shellsort.utils;

public final class Texts {
    private Texts() {}

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static String[] splitSafe(String text, String delimiter) {
        return text == null ? new String[0] : text.split(delimiter);
    }
}
