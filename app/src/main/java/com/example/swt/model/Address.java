package com.example.swt.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class Address implements Parcelable {
    private String street;
    private String city;
    private String zip; //3,2,1

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String toString(){
        return street + "\n" + zip + " " + city ;
    }


    public Address(Parcel in) {
        street = in.readString();
        city = in.readString();
        zip = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(zip);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}