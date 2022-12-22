package com.example.swt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swt.model.Company;
import com.example.swt.model.ProductGroup;
import com.example.swt.model.TimeInterval;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityMore extends AppCompatActivity {
    ArrayList<Company> favorites = new ArrayList<>();

    ArrayList<String> chosenCategories = new ArrayList<>();
    ArrayList<String> favouritecompaniesnames = new ArrayList<>();
    Singletonclass mCompanybase2;
    Filterclass filterobject;
    Button goback;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        TextView name =(TextView) findViewById(R.id.textName);
        TextView address =(TextView) findViewById(R.id.textAddress);
        TextView type =(TextView) findViewById(R.id.textType);
        TextView des =(TextView) findViewById(R.id.textDescription);
        TextView delivery =(TextView) findViewById(R.id.textDelivery);
        TextView product =(TextView) findViewById(R.id.textProducts);
        TextView productDes =(TextView) findViewById(R.id.textProductDescription);
        TextView owner =(TextView) findViewById(R.id.textOwner);
        TextView org =(TextView) findViewById(R.id.textOrganisation);
        TextView time =(TextView) findViewById(R.id.textTime) ;
        Button addToFav = findViewById(R.id.addToFav);
        filterobject = Filterclass.get();
        Button goback = findViewById(R.id.goback);

        HashMap<String, Company>   favorites = filterobject.getFavouritecompanies();




       Company company = getIntent().getExtras().getParcelable("name");

            name.setText(company.getName());
            address.setText(company.getAddress().toString());
            type.setText(company.getTypes().toString());
            des.setText(company.getDescription());
            delivery.setText("Has Delivery");
            for(ProductGroup productGroup: company.getProductGroups()){
                product.setText(productGroup.toString());
            }
          productDes.setText(company.getProductsDescription());
          owner.setText(company.getOwner());
          if(!company.getOrganizations().isEmpty()){
              org.setText(company.getOrganizations().get(0).getName());
          }else{
              org.setText("No Organisation");
          }

        time.setText(company.getOpeningHours().toString());

          goback.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent gobacktofirstactivtiy = new Intent(ActivityMore.this,FirstActivity.class);
                  startActivity(gobacktofirstactivtiy);
              }
          });



        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean favorite = false;
                boolean favorite2 = true;
                if (!favorites.containsKey(company.getId()) ) {
                    company.setIsFavorite(favorite2);
                    favorites.put(company.getId(),company);
                    filterobject.setFavouritecompanies(favorites);
                    Toast toast = Toast.makeText(getApplicationContext(), "Added to favorites", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    company.setIsFavorite(favorite);
                    favorites.remove(company.getId());
                    filterobject.setFavouritecompanies(favorites);
                    Toast toast = Toast.makeText(getApplicationContext(), "Removed from favorites", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
























    }
}
