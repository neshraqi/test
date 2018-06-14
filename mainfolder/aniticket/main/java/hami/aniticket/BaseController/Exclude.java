package hami.hamibelit.BaseController;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-14.
 */

public class Exclude implements ExclusionStrategy {

    @Override
    public boolean shouldSkipClass(Class<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        SerializedName ns = field.getAnnotation(SerializedName.class);
        if (ns != null)
            return false;
        return true;
    }
}