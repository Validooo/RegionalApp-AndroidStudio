package com.example.swt;

import android.os.Parcel;

import com.example.swt.model.Address;
import com.example.swt.model.Company;
import com.example.swt.model.Location;
import com.example.swt.model.Message;
import com.example.swt.model.Openinghours;
import com.example.swt.model.Organization;
import com.example.swt.model.ProductGroup;
import com.example.swt.model.TimeInterval;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class Singletonclass {
    private final String TAG = "CompanyBase";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private Singletonclass(){
    }

    private ArrayList<Company> companies= new ArrayList<>();
    private static Singletonclass mSingletonclass = new Singletonclass();

    public static Singletonclass get() {
        if (mSingletonclass == null){
            mSingletonclass = new Singletonclass();
        }
        return mSingletonclass;

    }

    private void setCompanies(ArrayList<Company> companies) {
        this.companies = companies;
    }



    public ArrayList<Company> getCompanies() {
        return companies;
    }



    public void readCompanies(){

        db.collection("companies").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for(DocumentSnapshot d : queryDocumentSnapshots.getDocuments()){
                        Company company = new Company(Parcel.obtain());
                        company.setId(d.getId());
                        company.setName(d.getData().get("name").toString());
                        Address address = new Address(Parcel.obtain());
                        address.setCity(((Map)d.getData().get("address")).get("city").toString());
                        address.setStreet(((Map)d.getData().get("address")).get("street").toString());
                        address.setZip(((Map)d.getData().get("address")).get("zip").toString());
                        company.setAddress(address);
                        Location location = new Location(Parcel.obtain());
                        location.setLat((Double) ((Map)d.getData().get("location")).get("lat"));
                        location.setLon((Double)((Map)d.getData().get("location")).get("lon"));
                        company.setLocation(location);
                        company.setDescription(d.getData().get("description").toString());
                        if(d.getData().get("mail") != null){
                            company.setMail(d.getData().get("mail").toString());
                        }
                        if(d.getData().get("url") != null){
                            company.setUrl(d.getData().get("url").toString());
                        }
                        company.setTypes((ArrayList<String>)d.getData().get("types"));
                        if(d.getData().get("owner") != null){
                            company.setOwner(d.getData().get("owner").toString());
                        }
                        Openinghours openinghours = new Openinghours(Parcel.obtain());
                        ArrayList<TimeInterval> monday = new ArrayList<>();
                        if(((Map)d.getData().get("openingHours")).get("monday") != null){
                            for(Map map : ((ArrayList<Map>)((Map)d.getData().get("openingHours")).get("monday"))){
                                TimeInterval mondayInterval = new TimeInterval(Parcel.obtain());
                                mondayInterval.setStart(map.get("start").toString());
                                mondayInterval.setEnd(map.get("end").toString());
                                monday.add(mondayInterval);
                            }
                        }
                        openinghours.setMonday(monday);
                        ArrayList<TimeInterval> tuesday = new ArrayList<>();
                        if(((Map)d.getData().get("openingHours")).get("tuesday") !=null){
                            for(Map map : ((ArrayList<Map>)((Map)d.getData().get("openingHours")).get("tuesday"))){
                                TimeInterval tuesdayInterval = new TimeInterval(Parcel.obtain());
                                tuesdayInterval.setStart(map.get("start").toString());
                                tuesdayInterval.setEnd(map.get("end").toString());
                                tuesday.add(tuesdayInterval);
                            }
                        }
                        openinghours.setTuesday(tuesday);
                        ArrayList<TimeInterval> wednesday = new ArrayList<>();
                        if(((Map)d.getData().get("openingHours")).get("wednesday") != null){
                            for(Map map : ((ArrayList<Map>)((Map)d.getData().get("openingHours")).get("wednesday"))){
                                TimeInterval wednesdayInterval = new TimeInterval(Parcel.obtain());
                                wednesdayInterval.setStart(map.get("start").toString());
                                wednesdayInterval.setEnd(map.get("end").toString());
                                wednesday.add(wednesdayInterval);
                            }
                        }
                        openinghours.setWednesday(wednesday);
                        ArrayList<TimeInterval> thursday = new ArrayList<>();
                        if(((Map)d.getData().get("openingHours")).get("thursday") != null){
                            for(Map map : ((ArrayList<Map>)((Map)d.getData().get("openingHours")).get("thursday"))){
                                TimeInterval thursdayInterval = new TimeInterval(Parcel.obtain());
                                thursdayInterval.setStart(map.get("start").toString());
                                thursdayInterval.setEnd(map.get("end").toString());
                                thursday.add(thursdayInterval);
                            }
                        }
                        openinghours.setThursday(thursday);
                        ArrayList<TimeInterval> friday = new ArrayList<>();
                        if(((Map)d.getData().get("openingHours")).get("friday") != null){
                            for(Map map : ((ArrayList<Map>)((Map)d.getData().get("openingHours")).get("friday"))){
                                TimeInterval fridayInterval = new TimeInterval(Parcel.obtain());
                                fridayInterval.setStart(map.get("start").toString());
                                fridayInterval.setEnd(map.get("end").toString());
                                friday.add(fridayInterval);
                            }
                        }
                        openinghours.setFriday(friday);
                        ArrayList<TimeInterval> saturday = new ArrayList<>();
                        if(((Map)d.getData().get("openingHours")).get("saturday") != null){
                            for(Map map : ((ArrayList<Map>)((Map)d.getData().get("openingHours")).get("saturday"))){
                                TimeInterval saturdayInterval = new TimeInterval(Parcel.obtain());
                                saturdayInterval.setStart(map.get("start").toString());
                                saturdayInterval.setEnd(map.get("end").toString());
                                saturday.add(saturdayInterval);
                            }
                        }
                        openinghours.setSaturday(saturday);
                        ArrayList<TimeInterval> sunday = new ArrayList<>();
                        if(((Map)d.getData().get("openingHours")).get("sunday") != null){
                            for(Map map : ((ArrayList<Map>)((Map)d.getData().get("openingHours")).get("sunday"))){
                                TimeInterval sundayInterval = new TimeInterval(Parcel.obtain());
                                sundayInterval.setStart(map.get("start").toString());
                                sundayInterval.setEnd(map.get("end").toString());
                                sunday.add(sundayInterval);
                            }
                        }
                        openinghours.setSunday(sunday);
                        company.setOpeningHours(openinghours);
                        company.setDeliveryService((boolean)d.getData().get("deliveryService"));
                        ArrayList<Organization> organizations = new ArrayList<>();
                        for(Map map: (ArrayList<Map>)d.getData().get("organizations")){
                            Organization organization = new Organization(Parcel.obtain());
                            organization.setId((double)map.get("id"));
                            organization.setName(map.get("name").toString());
                            organization.setUrl(map.get("url").toString());
                            organizations.add(organization);
                        }
                        company.setOrganizations(organizations);
                        company.setOpeningHoursComments(d.getData().get("openingHoursComments").toString());
                        ArrayList<Message> messages = new ArrayList<>();
                        for(Map map: (ArrayList<Map>) d.getData().get("messages")){
                            Message message = new Message(Parcel.obtain());
                            message.setContent(map.get("content").toString());
                            message.setDate(map.get("date").toString());
                            messages.add(message);
                        }
                        company.setMessages(messages);
                        ArrayList<ProductGroup> productGroups= new ArrayList<>();
                        for(Map map: (ArrayList<Map>) d.getData().get("productGroups")){
                            ProductGroup productGroup = new ProductGroup(Parcel.obtain());
                            productGroup.setCategory(map.get("category").toString());
                            productGroup.setRawProduct((boolean)map.get("rawProduct"));
                            productGroup.setProducer((double)map.get("producer"));
                            productGroup.setProductTags((ArrayList<String>) map.get("productTags"));
                            productGroup.setSeasons((ArrayList<String>) map.get("seasons"));
                            productGroups.add(productGroup);
                        }
                        company.setProductGroups(productGroups);
                        company.setProductsDescription(d.getData().get("productsDescription").toString());
                        company.setGeoHash(d.getData().get("geoHash").toString());
                        int i=0;
                        if(!companies.contains(company)){
                            companies.add(company);
                        }

                        //System.out.println(companies.get(i).getAddress().getCity()+ "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                        i= i+1;
                    }
                    mSingletonclass.setCompanies(companies);
                    System.out.println("All companies have been read and the application is ready to go");
                //    System.out.println(companies.get(1).getAddress().getCity());
                   // System.out.println(companies.get(2).getAddress().getCity());


                }
            }
        });

    }



}