package hami.aniticket.BaseController;

/**
 * Created by renjer on 2018-03-08.
 */

public class NameValueBoolean {
    private String name;
    private Boolean value;
    //-----------------------------------------------

    public NameValueBoolean(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }
    //-----------------------------------------------

    public NameValueBoolean() {
    }
    //-----------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
