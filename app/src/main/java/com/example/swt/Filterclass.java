package com.example.swt;

import com.example.swt.model.Company;

import java.util.ArrayList;
import java.util.HashMap;

public class Filterclass {

    ArrayList<String> chosenItemCategories = new ArrayList<>();
    ArrayList<String> chosenOrganisations = new ArrayList<>();
    ArrayList<String> chosenStoreTypes = new ArrayList<>();
    boolean IsOpen;
    boolean hasDelivery;
    double currentlat;
    double currentlog;
    int rangeinkm;
   HashMap<String, Company> Favouritecompanies= new HashMap<>();



    public void setFavouritecompanies(HashMap<String, Company> Favouritecompanies){
        this.Favouritecompanies = Favouritecompanies;

    }

    public HashMap<String, Company>  getFavouritecompanies(){
        return Favouritecompanies;
    }



    private Filterclass(){
    }
    private static Filterclass filterobject = new Filterclass();



    public static Filterclass get() {
        if (filterobject == null){
            filterobject = new Filterclass();
        }
        return filterobject;
    }


    public void setChosenStoreTypes(ArrayList<String> chosenStoreTypes){
        this.chosenStoreTypes = new ArrayList<>(chosenStoreTypes);
    }
    public void setChosenOrganisations(ArrayList<String> chosenOrganisations){
        this.chosenOrganisations = new ArrayList<>(chosenOrganisations);
    }

    public void setChosenItemCategories(ArrayList<String> chosenItemCategories){
        this.chosenItemCategories = new ArrayList<>(chosenItemCategories);
    }

    public void setIsOpen(boolean IsOpen){
        this.IsOpen= IsOpen;
    }

    public void setHasDelivery(boolean hasDelivery){
        this.hasDelivery = hasDelivery;
    }

    public void setcurrentlat(double currentlat){
        this.currentlat = currentlat;
    }
    public void setCurrentlog(double currentlog){
        this.currentlog= currentlog;
    }
    public void setrangeinkm(int rangeinkm){
        this.rangeinkm = rangeinkm;
    }

    public ArrayList<String> getChosenStoreTypes(){
        return chosenStoreTypes;
    }
    public ArrayList<String> getChosenOrganisations(){
        return  chosenOrganisations;
    }
    public ArrayList<String> getChosenItemCategories(){
        return chosenItemCategories;
    }
    public boolean getisOpen(){
    return IsOpen;
    }
    public boolean gethasDelivery(){
   return hasDelivery;
    }
    public double getCurrentlat(){return currentlat;}
    public double getCurrentlog(){return currentlog;}
    public int getRangeinkm(){return rangeinkm;}
}