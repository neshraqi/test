package hami.aniticket.Activity.Updates;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-04-10.
 */

public class UpdateResponse {
    @SerializedName("version")
    private int version;
    //-----------------------------------------------

    public UpdateResponse() {
    }
    //-----------------------------------------------

    public int getVersion() {
        return version;
    }
}
