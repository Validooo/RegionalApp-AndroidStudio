package com.example.swt;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.swt.model.Company;


import com.example.swt.model.Message;
import com.example.swt.model.Organization;
import com.example.swt.model.ProductGroup;
import com.example.swt.model.TimeInterval;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class FirstActivity extends AppCompatActivity {
    Button btn_data;
    Button btn_filter;
    Button btnSearch;
    Button nearcompanies;
    EditText search;
    ListView companyLIst;
    String searchTerm= "";
    String[] listCategories;
    boolean isOpen;
    boolean hasDelivery;
    boolean[] checkedCategories;
    ArrayList<Integer> UserCategories = new ArrayList<>();
    ArrayList<String> chosenCategories = new ArrayList<>();
    ArrayList<String> chosenItemCategories = new ArrayList<>();
    ArrayList<String> chosenOrganisations = new ArrayList<>();
    ArrayList<String> chosenStoreTypes = new ArrayList<>();
    ArrayList<Company> alldata = new ArrayList<>();
    double currentlat;
    double currentlog;
    int rangeKm;
    double currentlat2;
    double currentlog2;
    Singletonclass mCompanybase2;
    Filterclass filterobject;
    Button btnFavorite;
    HashMap<String,Company> favouritecompaniesfromfilterobject = new HashMap<>();




    private FusedLocationProviderClient fusedLocationProviderClient;

    private FirebaseFirestore db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_data=(Button) findViewById(R.id.btn_data);
        btn_filter=(Button) findViewById(R.id.btn_filter);
        btnSearch=(Button)findViewById(R.id.btnSearch);
        search = (EditText) findViewById(R.id.companyAddress);
        companyLIst=(ListView) findViewById(R.id.companyList);
        nearcompanies =(Button)findViewById(R.id.shownearcompanires);
        db = FirebaseFirestore.getInstance();
        mCompanybase2 = Singletonclass.get();
        mCompanybase2.readCompanies();
        alldata = new ArrayList<Company>();
        filterobject = Filterclass.get();
        btnFavorite= findViewById(R.id.btnFavorite);
        listCategories = getResources().getStringArray(R.array.categories2);
        checkedCategories = new boolean[listCategories.length];
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        ArrayList<Company> favorites = new ArrayList<>();



        for (String i : listCategories){
            chosenCategories.add(i);
        }




       // gets all companies data
        alldata= (ArrayList<Company>) getIntent().getSerializableExtra("alldata");
            ////////////chosenCategories =(getIntent().getExtras().getStringArrayList("chosenCategories"));

            isOpen = filterobject.getisOpen();
            chosenStoreTypes = filterobject.getChosenStoreTypes();
            chosenItemCategories = filterobject.getChosenItemCategories();
            chosenOrganisations = filterobject.getChosenOrganisations();
            hasDelivery = filterobject.gethasDelivery();
            currentlat= filterobject.getCurrentlat();
            currentlog= filterobject.getCurrentlog();
            rangeKm = filterobject.getRangeinkm();
            favouritecompaniesfromfilterobject = filterobject.getFavouritecompanies();

        if(alldata == null){
            alldata = mCompanybase2.getCompanies();
        }

                                                                           // getting all the information from filter class


        btn_filter.setOnClickListener(new View.OnClickListener() {                 // go to filteractivity
            @Override
            public void onClick(View v) {//go to Filter Screen
                Intent filter = new Intent(FirstActivity.this, FilterActivity.class);
                filter.putExtra("chosenCategories",chosenCategories);
                startActivity(filter);
            }
        });





        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                HashMap<String,Company> favouritescompanies = favouritecompaniesfromfilterobject;
                ArrayList<Company>   justarrayforcompanies =  new ArrayList<>();
                ArrayList<String> names = new ArrayList<>();
                favouritescompanies.forEach((k,c)->{
                    names.add(c.getName());
                    justarrayforcompanies.add(c);
                } );
              String s = "No favourite companies";
              if (names.isEmpty()){
                  names.add(s);
              }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, R.id.companyName, names);
                companyLIst.setAdapter(arrayAdapter);
                if(!names.contains(s)) {
                    companyLIst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {// go to more detail
                            Intent intent = new Intent(FirstActivity.this, ActivityMore.class);
                            intent.putExtra("name", justarrayforcompanies.get(position));
                            startActivity(intent);
                        }
                    });
                }

            }
        });






        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FirstActivity.this);
                mBuilder.setTitle(R.string.av_cat);
                //übergeben Sting-Items aus Liste, das boolean-Array für Checkboxen und den OnClickListener für diese
                mBuilder.setMultiChoiceItems(listCategories, checkedCategories, new DialogInterface.OnMultiChoiceClickListener() {
                    //überprüfe, ob die Kategorie checked ist und ob sie in der  Liste der ausgewählten ist, um Duplikate zu vermeiden
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(! UserCategories.contains(position)){
                                UserCategories.add(position);
                            }
                        }else if(UserCategories.contains(position)) {
                            UserCategories.remove(Integer.valueOf(position));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> category = new ArrayList<String>();

                        if (UserCategories.isEmpty()) {
                            for (String i : listCategories) category.add(i);
                        } else {

                            for (int i = 0; i < UserCategories.size(); i++) {
                                category.add(listCategories[UserCategories.get(i)]);
                                System.out.println(UserCategories.size());
                            }
                            chosenCategories = category;
                            for (String i : chosenCategories){
                               // System.out.println(i+"3333333333333333333333333333333333333333333333333333333333333333333");
                            }
                        }
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedCategories.length; i++){
                            checkedCategories[i] = false;
                            UserCategories.clear();
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//gets  search term
                searchTerm = search.getText().toString();
                getList();
                System.out.println(chosenItemCategories.size());
                System.out.println(chosenStoreTypes.size());
                System.out.println(chosenOrganisations.size());
                System.out.println(hasDelivery);
                System.out.println(isOpen);
                System.out.println(rangeKm);
            }

        });
        // btnSearch.callOnClick();


        nearcompanies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ArrayList<Company> companies = getListBySearchTerm(alldata);
                ArrayList<Company> nearcompanies = new ArrayList<>();
                ArrayList<String> names2 = new ArrayList<>();
                if (ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // if permission is granted
                    getCurrentlocation();
                    for (Company company : companies) {
                        if (distance1(company.getLocation().getLat(), company.getLocation().getLon(), currentlat2, currentlog2) < 0.1) {
                            names2.add(company.getName());
                        }
                    }


                    String s = " No Companies found";
                    if (names2.isEmpty() == false) {
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, R.id.companyName, names2);
                        companyLIst.setAdapter(arrayAdapter);
                    } else {
                        names2.add(s);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, R.id.companyName, names2);
                        companyLIst.setAdapter(arrayAdapter);

                    }


                    if (names2.contains(s) == false) {
                        companyLIst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {// go to more detail
                                Intent intent = new Intent(FirstActivity.this, ActivityMore.class);
                                intent.putExtra("name", nearcompanies.get(position));
                                startActivity(intent);
                            }
                        });
                    }
                }
                else {
                    // if permission is not granted
                    ActivityCompat.requestPermissions(FirstActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            ,Manifest.permission.ACCESS_COARSE_LOCATION},100);
                }
            }
        });

    }

    public boolean checkTime(String start,String target, String end){//checks if target is between start and end
        LocalTime main = LocalTime.parse(target);
        return (main.isAfter(LocalTime.parse(start)) && main.isBefore(LocalTime.parse(end)));
    }

    public void getList(){// returns List of companies that meet the criteria
        ArrayList<Company> companies = getListBySearchTerm(alldata);
        ArrayList<Company> filteredCompanies = new ArrayList<>();
        filteredCompanies.clear();
        ArrayList<String> names = new ArrayList<>();
        names.clear();
        boolean firstfilter =false;                         // die sind die Variable , wenn die true gesetzt werden
        boolean secondfilter = false;                       // wird das Company zu filteredCompanies hinzugefügt
        boolean thirdfilter= false;
        boolean fourthfilter = false;
        boolean fivefilter = false;
        boolean sixthfilter=false;
        for(Company company : companies){
            firstfilter =false;
            secondfilter = false;
            thirdfilter= false;
            fourthfilter = false;
            fivefilter = false;
            sixthfilter=false;
            if(chosenStoreTypes.isEmpty() && chosenOrganisations.isEmpty() && chosenItemCategories.isEmpty() && !isOpen && !hasDelivery && (rangeKm==0) && !filteredCompanies.contains(company)) {
                filteredCompanies.add(company);
            }
            else{
                for(ProductGroup productGroup : company.getProductGroups()) {
                    if (!filteredCompanies.contains(company) && chosenItemCategories.isEmpty()) {        //hier müssen wir erstmal prüfen ob der Nutzer nichts ausgewählt hat
                        firstfilter = true;                                                                      // dann setzten wir first filter auf true
                    }else {
                        if (!filteredCompanies.contains(company) && (!chosenItemCategories.isEmpty())) {        // hier wenn der Nutzer doch was ausgewählt hat , dann müssen wir prüfen ob die Company alle eingebene categories hat
                            if (chosenItemCategories.contains(productGroup.getCategory())) {                            // and dafür setzen wir ein Zähler
                                firstfilter = true;
                            }
                        }
                    }
                }
                for(Organization organization : company.getOrganizations()){
                    if(!filteredCompanies.contains(company)&& chosenOrganisations.isEmpty()){                      // hier das gleiche
                        secondfilter= true;
                    }else {
                        if (!filteredCompanies.contains(company) && (!chosenOrganisations.isEmpty())) {
                            if (chosenOrganisations.contains(organization.getName())) {
                                secondfilter = true;
                            }
                        }
                    }

                }
                for(String type : company.getTypes()){
                    if (!filteredCompanies.contains(company) && (chosenStoreTypes.isEmpty())){
                        thirdfilter = true;
                    }else {
                        if (!filteredCompanies.contains(company) && (!chosenStoreTypes.isEmpty())) {
                            if (chosenStoreTypes.contains(type)) {
                                thirdfilter=true;
                            }
                        }
                    }

                }
                for(TimeInterval timeInterval : company.getOpeningHours().getCurrentDay()) {
                    if (!filteredCompanies.contains(company) && !isOpen) {                      // hier wenn der Nutzer nicht eingibt ob die Company gerade auf hat oder nicht
                        fourthfilter = true;
                    } else {

                        if (!filteredCompanies.contains(company) && isOpen) {                    // wenn die jetzt auf hat müssen wir das prüfen
                            if (checkTime(timeInterval.getStart(), LocalTime.now().toString(), timeInterval.getEnd())) {
                                fourthfilter = true;
                            }
                        }
                    }
                }

                if((!hasDelivery) && !filteredCompanies.contains(company)){                   // hier wenn der Nutzer nichts eingibt bei Delivry box
                    fivefilter =true;
                }else {
                    if(hasDelivery && company.isDeliveryService() && !filteredCompanies.contains(company)) {     // hier wenn er was geliefert will
                        fivefilter =true;
                    }
                }


                if(rangeKm==0 &&!filteredCompanies.contains(company)){
                    sixthfilter=true;
                }else{
                    if(distance1(company.getLocation().getLat(),company.getLocation().getLon(),currentlat,currentlog) <= rangeKm && !filteredCompanies.contains(company)) {       // hier rechnet die Methode distance die Entfernung
                        sixthfilter =true;                                                                                                                                            // wenn die Entfernung kleiner als der fesgelegter Umkreis
                    }
                }

                if(firstfilter && secondfilter && thirdfilter && fourthfilter && fivefilter && sixthfilter && !filteredCompanies.contains(company)){// hier wenn die company alle filter erfüllt hat, wird sie zu filteredCompanies hinzugefügt
                    filteredCompanies.add(company);
                }
            }
        }
        String s = "No Companies Found";


        for(Company company : filteredCompanies) if(!names.contains(company.getName()))names.add(company.getName());
        if(!names.isEmpty()) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, R.id.companyName, names);
            companyLIst.setAdapter(arrayAdapter);
        }else{
            names.add(s);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, R.id.companyName, names);
            companyLIst.setAdapter(arrayAdapter);
            for(Company name :filteredCompanies){
                System.out.println(name.getName());
            }

        }



            companyLIst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {// go to more detail
                    if(names.contains(s) == false) {
                    Intent intent = new Intent(FirstActivity.this, ActivityMore.class);
                    intent.putExtra("name", filteredCompanies.get(position));
                    intent.putExtra("chosenCategories", chosenCategories);
                    startActivity(intent);
                }
                }
            });

    }




    public ArrayList<Company> getListBySearchTerm( ArrayList<Company> mainList){ //hier wird überprüft, ob der eingegebene Suchterm mit den Daten eienr Company übereinstimmt und ob die Kategorie ausgewählt ist oder nicht
        ArrayList<Company> companies = new ArrayList<>();
        for(Company company : mainList){
            if(!companies.contains(company) && searchTerm == "") companies.add(company);
            else if(!companies.contains(company) && company.getName().toLowerCase().contains(searchTerm.toLowerCase()) && (chosenCategories.contains("name") || chosenCategories.isEmpty())) companies.add(company);
            else if(!companies.contains(company) && company.getAddress().toString().toLowerCase().contains(searchTerm.toLowerCase()) && (chosenCategories.contains("address") || chosenCategories.isEmpty())) companies.add(company);
            else if(!companies.contains(company) && company.getOwner()!=null &&company.getOwner().toLowerCase().contains(searchTerm.toLowerCase()) && (chosenCategories.contains("owner") || chosenCategories.isEmpty())) companies.add(company);
            else if(!companies.contains(company) && company.getTypes().toString().toLowerCase().contains(searchTerm.toLowerCase()) && (chosenCategories.contains("types") || chosenCategories.isEmpty()))companies.add(company);
            else if(!companies.contains(company) && company.getDescription().toLowerCase().contains(searchTerm.toLowerCase()) && (chosenCategories.contains("description") || chosenCategories.isEmpty()))companies.add(company);
            else if (!companies.contains(company)){
                for(ProductGroup productGroup: company.getProductGroups()){
                    if(!companies.contains(company) && productGroup.toString().toLowerCase().contains(searchTerm.toLowerCase()) && (chosenCategories.contains("productGroups") || chosenCategories.isEmpty()))companies.add(company);
                }
            }
            else if(!companies.contains(company) && company.getProductsDescription().toLowerCase().contains(searchTerm.toLowerCase()) && (chosenCategories.contains("productsDescription") || chosenCategories.isEmpty())) companies.add(company);
            else if(!companies.contains(company) && company.getOpeningHoursComments().toLowerCase().contains(searchTerm.toLowerCase()) && (chosenCategories.contains("openingHoursComments") || chosenCategories.isEmpty())) companies.add(company);
            else if(!companies.contains(company)){
                for(Organization organization: company.getOrganizations()){
                    if(!companies.contains(company) && organization.toString().toLowerCase().contains(searchTerm.toLowerCase()) && (chosenCategories.contains("organizations") || chosenCategories.isEmpty()))companies.add(company);
                }
            }
            else if(!companies.contains(company)){
                for(Message message: company.getMessages()){
                    if(!companies.contains(company) && message.toString().toLowerCase().contains(searchTerm.toLowerCase()) && (chosenCategories.contains("message") || chosenCategories.isEmpty())) companies.add(company);
                }
            }
        }

        return companies;

    }
    //berechnet die Entfernung anhand 2 Koordinaten
    private static double distance1(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }

    private void getCurrentlocation() {
        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE
        );
        //Check conditon
        if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
                @Override
                public void onComplete(@NonNull Task<android.location.Location> task) {

                    android.location.Location location = task.getResult();

                    if (location != null) {
                        currentlat = location.getLatitude();
                        currentlog = location.getLongitude();
                        System.out.println(location.getLatitude());
                        System.out.println(location.getLongitude());
                    } else {
                        LocationRequest locationRequest = new LocationRequest().
                                setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {

                                android.location.Location location1 = locationResult.getLastLocation();
                                currentlat2 = location.getLatitude();
                                currentlog2 = location.getLongitude();
                                System.out.println(currentlat2+"ÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄ");
                                System.out.println(currentlog2+"BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                            }
                        };
                        if (ActivityCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                    }
                }
            });
        }else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) );
        }
    }


}
