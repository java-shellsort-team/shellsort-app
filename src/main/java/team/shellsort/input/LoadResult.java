package team.shellsort.input;

import java.util.List;

public class LoadResult<T> {
    private final List<T> items;
    private final List<String> errors;

    public LoadResult(List<T> items, List<String> errors) {
        this.items = items;
        this.errors = errors;
    }

    public List<T> getItems() { return items; }
    public List<String> getErrors() { return errors; }
    public boolean hasErrors() { return !errors.isEmpty(); }
}
