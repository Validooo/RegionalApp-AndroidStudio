package com.example.swt;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FilterActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn_done;
    Button useyourownlocation;
    Button givethelocation;
    TextView selectedTime;
    TextView selectedTimeEnd;
    TextView bar;
    TextView city, postleitzahl, street, hausnumber;
    Button OK;
    CheckBox isOpen;
    CheckBox hasDelivery;
    String[] categories;
    String[] types;
    String[] organisations;
    boolean[] checkedItems1;
    boolean[] checkedItems2;
    boolean[] checkedItems3;
    ArrayList<Integer> selectedItems1 = new ArrayList<>();
    ArrayList<String> chosenItemCategories = new ArrayList<>();
    ArrayList<Integer> selectedItems2 = new ArrayList<>();
    ArrayList<String> chosenOrganisations = new ArrayList<>();
    ArrayList<Integer> selectedItems3 = new ArrayList<>();
    ArrayList<String> chosenStoreTypes = new ArrayList<>();
    ArrayList<String> chosenCategories = new ArrayList<>();
    Boolean isBtn4;
    SeekBar seekBar;
    private FusedLocationProviderClient fusedLocationProviderClient;
    double currentlat;
    double currentlog;
    int rangeKm =0;
    Filterclass filterobject;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        btn1=(Button) findViewById(R.id.btn1);
        btn2=(Button) findViewById(R.id.btn2);
        btn3=(Button) findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button) findViewById(R.id.btn5);
        btn_done=(Button) findViewById(R.id.btn_done);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        bar = (TextView) findViewById(R.id.bar);
        useyourownlocation = (Button) findViewById(R.id.useyourownlocation);
        givethelocation = (Button) findViewById(R.id.givethelocation);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        city = (TextView) findViewById(R.id.cityname);
        postleitzahl = (TextView) findViewById(R.id.postleitzahl);
        street = (TextView) findViewById(R.id.street);
        hausnumber = (TextView) findViewById(R.id.hausnumber);
         OK = (Button) findViewById(R.id.OK);
        city.setVisibility(View.INVISIBLE);
        postleitzahl.setVisibility(View.INVISIBLE);
        hausnumber.setVisibility(View.INVISIBLE);
        street.setVisibility(View.INVISIBLE);
        OK.setVisibility(View.INVISIBLE);
        Singletonclass mCompanybase = Singletonclass.get();
        filterobject = Filterclass.get();



        selectedTime=(TextView)findViewById(R.id.time1);
        selectedTimeEnd=(TextView) findViewById(R.id.time2);

        categories = getResources().getStringArray(R.array.categories);
        checkedItems1 = new boolean[categories.length];

        types = getResources().getStringArray(R.array.types);
        checkedItems2 = new boolean[types.length];

        organisations = getResources().getStringArray(R.array.organisations);
        checkedItems3 = new boolean[organisations.length];

        isOpen = (CheckBox) findViewById(R.id.checkBox);


        hasDelivery = (CheckBox) findViewById(R.id.checkBox2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(FilterActivity.this);
                builder1.setTitle("Available item categories");
                builder1.setMultiChoiceItems(categories, checkedItems1, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){//see if each item is checked and remove or add them to list based on it
                            if(! selectedItems1.contains(position)){
                                selectedItems1.add(position);
                            }
                        }else if(selectedItems1.contains(position)) {
                            selectedItems1.remove(Integer.valueOf(position));
                        }
                    }
                });
                builder1.setCancelable(false);
                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//save user choice
                        ArrayList<String> item = new ArrayList<>();
                        for(int i = 0; i< selectedItems1.size();i++){
                            item.add(categories[selectedItems1.get(i)]);
                        }
                        chosenItemCategories = new ArrayList<>(item);


                    }
                });
                builder1.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(FilterActivity.this);
                builder1.setTitle("Available store types");
                builder1.setMultiChoiceItems(types, checkedItems2, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(! selectedItems2.contains(position)){
                                selectedItems2.add(position);
                            }
                        }else if(selectedItems2.contains(position)) {
                            selectedItems2.remove(Integer.valueOf(position));
                        }
                    }
                });
                builder1.setCancelable(false);
                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> item = new ArrayList<>();
                        for(int i = 0; i< selectedItems2.size();i++){
                            item.add(types[selectedItems2.get(i) ]);
                        }
                        chosenStoreTypes= new ArrayList<>(item);


                    }
                });
                builder1.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(FilterActivity.this);
                builder1.setTitle("Available Organisations");
                builder1.setMultiChoiceItems(organisations, checkedItems3, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(! selectedItems3.contains(position)){
                                selectedItems3.add(position);
                            }
                        }else if(selectedItems3.contains(position)) {
                            selectedItems3.remove(Integer.valueOf(position));
                        }
                    }
                });
                builder1.setCancelable(false);
                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> item = new ArrayList<>();
                        for(int i = 0; i< selectedItems3.size();i++){
                            item.add(organisations[selectedItems3.get(i) ]);
                        }
                        chosenOrganisations = new ArrayList<>(item);


                    }
                });
                builder1.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();

            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBtn4=true;
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"Start picker");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBtn4=false;
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"End picker");
            }
        });

        // send the data to MainActivity
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//send information back to main screen
                Intent intent = new Intent(FilterActivity.this, FirstActivity.class);
                intent.putExtra("chosenCategories",getIntent().getExtras().getStringArrayList("chosenCategories"));
                filterobject.setChosenItemCategories(chosenItemCategories);
                filterobject.setChosenOrganisations(chosenOrganisations);
                filterobject.setChosenStoreTypes(chosenStoreTypes);
                filterobject.setIsOpen(isOpen.isChecked());
                filterobject.setHasDelivery(hasDelivery.isChecked());
                filterobject.setcurrentlat(currentlat);
                filterobject.setCurrentlog(currentlog);
                filterobject.setrangeinkm(rangeKm);
                System.out.println(chosenItemCategories.size());
                System.out.println(chosenStoreTypes.size());
                startActivity(intent);
            }
        });

        // get the current location of the user
        useyourownlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FilterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(FilterActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                  // if permission is granted
                    getCurrentlocation();

                }else {
                    // if permission is not granted
                    ActivityCompat.requestPermissions(FilterActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            ,Manifest.permission.ACCESS_COARSE_LOCATION},100);
                }

            }
        });


        // this button just shows the hidden fields where the user will give the address manually
        givethelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                city.setVisibility(View.VISIBLE);
                postleitzahl.setVisibility(View.VISIBLE);
                hausnumber.setVisibility(View.VISIBLE);
                street.setVisibility(View.VISIBLE);
                OK.setVisibility(View.VISIBLE);








            }
        });

        // Ok is the hidden button that will be shown after pressing on givethelocationbutton
        // and after entering the address manually, we will get the lat and lag using geoLocate
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geoLocate();

            }
        });

        // this bar will sprecify how far should the company be away of the specified address
        // the variable rangeKm will save the wanted distance

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                bar.setText("Distance:  " +  String.valueOf(progress) +"km");
                rangeKm = progress;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(isBtn4){
            selectedTime.setText("Start : "+hourOfDay + ":" + minute);
        } else{
            selectedTimeEnd.setText("End : "+hourOfDay + ":" + minute);
        }

    }

    // requst permision to get the current location
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {

        if(requestCode== 100 && grantResults.length> 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            getCurrentlocation();
        }else {
            Toast.makeText(getApplicationContext(),"Permission denied.",Toast.LENGTH_SHORT).show();

        }
    }

    // this method gets the current location and saves the latitude and longtitude in currentlat and currentlog variable;
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
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    Location location = task.getResult();

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

                                Location location1 = locationResult.getLastLocation();
                                currentlat = location.getLatitude();
                                currentlog = location.getLongitude();
                            }
                        };
                        if (ActivityCompat.checkSelfPermission(FilterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FilterActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


    // this method will give the the lat  and lag  when the user decides to give the address manually
    private void geoLocate(){

        String streetnamestring="";
        String hausnumberstring="";
        if(city.getText().toString().isEmpty()  || postleitzahl.getText().toString().isEmpty()) {
            Toast.makeText(FilterActivity.this, "Please enter both city and postleitzahl", Toast.LENGTH_SHORT).show();
        }
     else {


            if (street.getText().toString() != null) {
                streetnamestring += street.getText().toString();

            }
            if (hausnumber.getText().toString() != null) {
                hausnumberstring += hausnumber.getText().toString();
            }

            String fulladress = streetnamestring + hausnumberstring + "," + city.getText().toString() + postleitzahl.getText().toString();

            Geocoder geocoder = new Geocoder(FilterActivity.this);
            List<Address> list = new ArrayList<>();
            try {
                list = geocoder.getFromLocationName(fulladress, 6);
            } catch (IOException e) {

            }

            if (list.size() > 0) {
                Address address = list.get(0);

                System.out.println(address.getLatitude());
                System.out.println(address.getLongitude());
                currentlat = address.getLatitude();
                currentlog = address.getLongitude();
                //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            }
        }
    }



}