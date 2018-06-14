package hami.hamibelit.Activity;

import java.util.ArrayList;
import java.util.List;

public class FilterList<E> {
    public <T> ArrayList filterList(List<T> originalList, FilterHotel filter, E text) {
        ArrayList<T> filterList = new ArrayList<T>();
        for (T object : originalList) {
            if (filter.isMatched(object, text)) {
                filterList.add(object);
            } else {
                continue;
            }
        }
        return filterList;
    }
    //-----------------------------------------------

}
