package lt.valaitis.lib.facebook.graph;

import com.facebook.GraphRequest;

import java.util.List;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public abstract class FbDataResponse<T> {
    private List<T> data;
    private GraphRequest nextPage;

    public List<T> getData() {
        return data;
    }

    public void setNextPage(GraphRequest graphRequest) {
        nextPage = graphRequest;
    }

    public GraphRequest getNextPage() {
        return nextPage;
    }
}
