package com.example.swt.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Openinghours implements Parcelable {
    private List<TimeInterval> sunday;
    private List<TimeInterval> saturday;
    private List<TimeInterval> tuesday;
    private List<TimeInterval> wednesday;
    private List<TimeInterval> friday;
    private List<TimeInterval> thursday;
    private List<TimeInterval> monday;

    public List<TimeInterval> getSunday() {
        return sunday;
    }

    public void setSunday(List<TimeInterval> sunday) {
        this.sunday = sunday;
    }

    public List<TimeInterval> getSaturday() {
        return saturday;
    }

    public void setSaturday(List<TimeInterval> saturday) {
        this.saturday = saturday;
    }

    public List<TimeInterval> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<TimeInterval> tuesday) {
        this.tuesday = tuesday;
    }

    public List<TimeInterval> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<TimeInterval> wednesday) {
        this.wednesday = wednesday;
    }

    public List<TimeInterval> getFriday() {
        return friday;
    }

    public void setFriday(List<TimeInterval> friday) {
        this.friday = friday;
    }

    public List<TimeInterval> getThursday() {
        return thursday;
    }

    public void setThursday(List<TimeInterval> thursday) {
        this.thursday = thursday;
    }

    public List<TimeInterval> getMonday() {
        return monday;
    }

    public void setMonday(List<TimeInterval> monday) {
        this.monday = monday;
    }

    public List<TimeInterval> getCurrentDay(){
        String currentDay = LocalDateTime.now().getDayOfWeek().toString();
        if(currentDay.toLowerCase().equals("monday"))return getMonday();
        else if(currentDay.toLowerCase().equals("tuesday"))return getTuesday();
        else if(currentDay.toLowerCase().equals("wednesday"))return getWednesday();
        else if(currentDay.toLowerCase().equals("thursday"))return getThursday();
        else if(currentDay.toLowerCase().equals("friday"))return getFriday();
        else if(currentDay.toLowerCase().equals("saturday"))return getSaturday();
        else return getSunday();
    }

    public String toString(){return "Monday: " + monday.toString() + "\nTuesday: " + tuesday.toString() + "\nWednesday: " + wednesday.toString() +
            "\nThursday: " + thursday.toString() + "\nFriday: " + friday.toString() + "\nSaturday: " + saturday.toString() + "\nSunday: " + sunday.toString();}

    public Openinghours(Parcel in) {
        if (in.readByte() == 0x01) {
            sunday = new ArrayList<TimeInterval>();
            in.readList(sunday, TimeInterval.class.getClassLoader());
        } else {
            sunday = null;
        }
        if (in.readByte() == 0x01) {
            saturday = new ArrayList<TimeInterval>();
            in.readList(saturday, TimeInterval.class.getClassLoader());
        } else {
            saturday = null;
        }
        if (in.readByte() == 0x01) {
            tuesday = new ArrayList<TimeInterval>();
            in.readList(tuesday, TimeInterval.class.getClassLoader());
        } else {
            tuesday = null;
        }
        if (in.readByte() == 0x01) {
            wednesday = new ArrayList<TimeInterval>();
            in.readList(wednesday, TimeInterval.class.getClassLoader());
        } else {
            wednesday = null;
        }
        if (in.readByte() == 0x01) {
            friday = new ArrayList<TimeInterval>();
            in.readList(friday, TimeInterval.class.getClassLoader());
        } else {
            friday = null;
        }
        if (in.readByte() == 0x01) {
            thursday = new ArrayList<TimeInterval>();
            in.readList(thursday, TimeInterval.class.getClassLoader());
        } else {
            thursday = null;
        }
        if (in.readByte() == 0x01) {
            monday = new ArrayList<TimeInterval>();
            in.readList(monday, TimeInterval.class.getClassLoader());
        } else {
            monday = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public void writeToParcel(Parcel dest, int flags) {
        if (sunday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(sunday);
        }
        if (saturday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(saturday);
        }
        if (tuesday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(tuesday);
        }
        if (wednesday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(wednesday);
        }
        if (friday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(friday);
        }
        if (thursday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(thursday);
        }
        if (monday == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(monday);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Openinghours> CREATOR = new Parcelable.Creator<Openinghours>() {
        @Override
        public Openinghours createFromParcel(Parcel in) {
            return new Openinghours(in);
        }

        @Override
        public Openinghours[] newArray(int size) {
            return new Openinghours[size];
        }
    };
}