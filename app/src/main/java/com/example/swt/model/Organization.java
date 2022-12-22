package com.example.swt.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Organization implements Parcelable {
    private double id; // 2
    private String name;
    private String url; // 1 3

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setId(double id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return   name + '\n'+
                "Id=" + id  + '\n'
                + url + '\n' + '\n';
    }

    public Organization(Parcel in) {
        id = in.readDouble();
        name = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(id);
        dest.writeString(name);
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Organization> CREATOR = new Parcelable.Creator<Organization>() {
        @Override
        public Organization createFromParcel(Parcel in) {
            return new Organization(in);
        }

        @Override
        public Organization[] newArray(int size) {
            return new Organization[size];
        }
    };
}
