package hami.mainapp.Util.CurrentLocation;

import android.location.Address;

/**
 * Created by renjer on 2017-08-14.
 */

public interface GetAddressByLocationListener {
    public void getAddress(Address address);
    public void tryGetAddress();
}
