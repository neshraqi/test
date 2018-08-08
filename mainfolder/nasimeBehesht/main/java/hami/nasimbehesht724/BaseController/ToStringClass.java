package hami.mainapp.BaseController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by renjer on 2018-02-14.
 */

public class ToStringClass {

    @Override
    public String toString() {
        try {
            Exclude ex = new Exclude();
            Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
            String jsonString = gson.toJson(this);
            return jsonString;
        } catch (Exception e) {
            e.getMessage();
        }
        return "";
    }
}
