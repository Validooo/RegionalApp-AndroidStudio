package com.example.swt.model;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Company implements Parcelable {

    private String id;
    private String name;
    private Address address;
    private Location location; // 15
    private String description; // 8
    private String mail; // 6
    private String url; // 9
    private List<String> types;//enum 3 get, set
    private String owner; // 2
    private Openinghours openingHours; // 14 get set
    private boolean deliveryService;// 1
    private List<Organization> organizations; // 11 get set
    private String openingHoursComments; // 10
    private List<Message> messages; // 13
    private List<ProductGroup> productGroups; // 17 get set
    private String productsDescription; // 5
    private String geoHash; // 7
    private boolean isFavorite;



    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
    public boolean isFavorite() { return isFavorite; }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Openinghours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Openinghours openingHours) {
        this.openingHours = openingHours;
    }

    public boolean isDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(boolean deliveryService) {
        this.deliveryService = deliveryService;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public String getOpeningHoursComments() {
        return openingHoursComments;
    }

    public void setOpeningHoursComments(String openingHoursComments) {
        this.openingHoursComments = openingHoursComments;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<ProductGroup> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(List<ProductGroup> productGroups) {
        this.productGroups = productGroups;
    }

    public String getProductsDescription() {
        return productsDescription;
    }

    public void setProductsDescription(String productsDescription) {
        this.productsDescription = productsDescription;
    }

    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(String geoHash) {
        this.geoHash = geoHash;
    }




    public Company(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = (Address) in.readValue(Address.class.getClassLoader());
        location = (Location) in.readValue(Location.class.getClassLoader());
        description = in.readString();
        mail = in.readString();
        url = in.readString();
        if (in.readByte() == 0x01) {
            types = new ArrayList<String>();
            in.readList(types, String.class.getClassLoader());
        } else {
            types = null;
        }
        owner = in.readString();
        openingHours = (Openinghours) in.readValue(Openinghours.class.getClassLoader());
        deliveryService = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            organizations = new ArrayList<Organization>();
            in.readList(organizations, Organization.class.getClassLoader());
        } else {
            organizations = null;
        }
        openingHoursComments = in.readString();
        if (in.readByte() == 0x01) {
            messages = new ArrayList<Message>();
            in.readList(messages, Message.class.getClassLoader());
        } else {
            messages = null;
        }
        if (in.readByte() == 0x01) {
            productGroups = new ArrayList<ProductGroup>();
            in.readList(productGroups, ProductGroup.class.getClassLoader());
        } else {
            productGroups = null;
        }
        productsDescription = in.readString();
        geoHash = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeValue(address);
        dest.writeValue(location);
        dest.writeString(description);
        dest.writeString(mail);
        dest.writeString(url);
        if (types == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(types);
        }
        dest.writeString(owner);
        dest.writeValue(openingHours);
        dest.writeByte((byte) (deliveryService ? 0x01 : 0x00));
        if (organizations == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(organizations);
        }
        dest.writeString(openingHoursComments);
        if (messages == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(messages);
        }
        if (productGroups == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(productGroups);
        }
        dest.writeString(productsDescription);
        dest.writeString(geoHash);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Company> CREATOR = new Parcelable.Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };
}
