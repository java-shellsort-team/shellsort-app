package team.shellsort.input;

import java.io.IOException;

public interface DataProvider {

    LoadResult load() throws IOException;
}
