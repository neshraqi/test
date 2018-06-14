package hami.hamibelit.BaseController;

import android.support.v4.util.ArrayMap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by renjer on 2017-12-05.
 */

public class AppliedFilters implements Serializable {
    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();

    //-----------------------------------------------`

    public AppliedFilters() {
    }

    //-----------------------------------------------

    public AppliedFilters(ArrayMap<String, List<String>> applied_filters) {
        this.applied_filters = applied_filters;
    }

    //-----------------------------------------------

    public ArrayMap<String, List<String>> getApplied_filters() {
        return applied_filters;
    }

    //-----------------------------------------------

    public void setApplied_filters(ArrayMap<String, List<String>> applied_filters) {
        this.applied_filters = applied_filters;
    }
}
