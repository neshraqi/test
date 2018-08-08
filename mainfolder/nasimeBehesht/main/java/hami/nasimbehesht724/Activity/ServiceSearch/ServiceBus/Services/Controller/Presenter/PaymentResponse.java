package hami.mainapp.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter;

import com.google.gson.annotations.SerializedName;


public class PaymentResponse {
    @SerializedName("Success")
    private Boolean success;
    @SerializedName("status")
    private int status;
    @SerializedName("payment_status")
    private int paymentStatus;
    //-----------------------------------------------\

    public Boolean getSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    //-----------------------------------------------

}
