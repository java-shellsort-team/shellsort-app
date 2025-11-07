package team.shellsort.strategy;

import java.util.Comparator;

public final class StrategyUtils {
    public static final Comparator<String> NULL_LAST
            = Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER);
}
