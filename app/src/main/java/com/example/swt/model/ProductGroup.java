package com.example.swt.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ProductGroup implements Parcelable {
    private String category; // 4
    private boolean rawProduct; // 2

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isRawProduct() {
        return rawProduct;
    }

    public void setRawProduct(boolean rawProduct) {
        this.rawProduct = rawProduct;
    }

    public double getProducer() {
        return producer;
    }

    public void setProducer(double producer) {
        this.producer = producer;
    }

    public List<String> getProductTags() {
        return productTags;
    }

    public void setProductTags(List<String> productTags) {
        this.productTags = productTags;
    }

    public List<String> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<String> seasons) {
        this.seasons = seasons;
    }

    private double producer; // 3
    private List<String> productTags;
    private List<String>  seasons; // 5 1


    @Override
    public String toString() {

        return
                "Category='" + category + '\n' +
                        "Product is Raw=" + rawProduct +  '\n'+
                        "Producer=" + producer +'\n'+
                        "Product Tags=" + productTags + '\n'+
                        "Seasons=" + seasons + '\n';
    }

    public ProductGroup(Parcel in) {
        category = in.readString();
        rawProduct = in.readByte() != 0x00;
        producer = in.readDouble();
        if (in.readByte() == 0x01) {
            productTags = new ArrayList<String>();
            in.readList(productTags, String.class.getClassLoader());
        } else {
            productTags = null;
        }
        if (in.readByte() == 0x01) {
            seasons = new ArrayList<String>();
            in.readList(seasons, String.class.getClassLoader());
        } else {
            seasons = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeByte((byte) (rawProduct ? 0x01 : 0x00));
        dest.writeDouble(producer);
        if (productTags == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(productTags);
        }
        if (seasons == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(seasons);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProductGroup> CREATOR = new Parcelable.Creator<ProductGroup>() {
        @Override
        public ProductGroup createFromParcel(Parcel in) {
            return new ProductGroup(in);
        }

        @Override
        public ProductGroup[] newArray(int size) {
            return new ProductGroup[size];
        }
    };
}