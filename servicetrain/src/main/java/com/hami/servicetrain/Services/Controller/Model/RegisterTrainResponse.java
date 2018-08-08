package com.hami.servicetrain.Services.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-03-11.
 */

public class RegisterTrainResponse implements Parcelable {
    @SerializedName("id")
    private String ticketId;

    @SerializedName("msg")
    private String msg;

    @SerializedName("code")
    private int code;

    @SerializedName("viewdata")
    private ViewParamsTrain viewParamsTrain;
    //-----------------------------------------------

    public RegisterTrainResponse() {
    }

    //-----------------------------------------------

    protected RegisterTrainResponse(Parcel in) {
        ticketId = in.readString();
        msg = in.readString();
        code = in.readInt();
        viewParamsTrain = in.readParcelable(ViewParamsTrain.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ticketId);
        dest.writeString(msg);
        dest.writeInt(code);
        dest.writeParcelable(viewParamsTrain, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RegisterTrainResponse> CREATOR = new Creator<RegisterTrainResponse>() {
        @Override
        public RegisterTrainResponse createFromParcel(Parcel in) {
            return new RegisterTrainResponse(in);
        }

        @Override
        public RegisterTrainResponse[] newArray(int size) {
            return new RegisterTrainResponse[size];
        }
    };

    public ViewParamsTrain getViewParamsTrain() {
        return viewParamsTrain;
    }

    public int getCode() {
        return code;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getMsg() {
        return msg;
    }
}
