
package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreen.adapter.AdapterProductUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopDetails extends AppCompatActivity {
    private ImageView shopIv;
    private TextView shopName,Phone,email,openclose,DeleiveryFee,address,filterproductsitem;
    private ImageButton call,map,cart,back,filterproduct;
    private EditText search;
    private RecyclerView productRV;
    private FirebaseAuth firebaseAuth;
    private  String shopUid;
    private  String myLatitude, myLongitude;
    private String mainshopname,shopEmail,shopePhone,shopAddress, shopLatitude, shopLongitude;
    private ArrayList <ModelProduct> productsList;
    private AdapterProductUser adapterProductUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        shopIv= findViewById(R.id.shopIv);
        shopName= findViewById(R.id.shopName);
        Phone= findViewById(R.id.Phone);
        email= findViewById(R.id.email);
        openclose= findViewById(R.id.openclose);
        DeleiveryFee= findViewById(R.id.DelieveryFee);
        address= findViewById(R.id.address);
        call= findViewById(R.id.call);
        map= findViewById(R.id.map);
        cart= findViewById(R.id.cart);
        back= findViewById(R.id.back);
        search= findViewById(R.id.search);
        filterproduct= findViewById(R.id.filterproduct);
        filterproductsitem= findViewById(R.id.filterproductsitem);
        productRV= findViewById(R.id.productRV);
        shopUid = getIntent().getStringExtra("shopUid");
        firebaseAuth= FirebaseAuth.getInstance();
        loadMyInfo();
        loadShopDetails();
        loadShopProducts();

        //search
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    adapterProductUser.getFilter().filter(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhone();

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();

            }
        });

       filterproduct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(ShopDetails.this);
               builder.setTitle("Choose Category:")
                       .setItems(Constants.productCategories1, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               //get selected item
                               String selected = Constants.productCategories1[which];
                               filterproductsitem.setText(selected);
                               if (selected.equals("All")) {
                                   //load all
                                   loadShopProducts();
                                   //showProd();
                               } else {
                                   //load filtered
                                   adapterProductUser.getFilter().filter(selected);
                               }
                           }
                       })
                       .show();
           }
       });

    }

    private void openMap() {
        String address = "https://maps.google.com/maps?saddr=" + myLatitude + "," + myLongitude + "&daddr=" + shopLatitude + "," + shopLongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
        startActivity(intent);
    }

    private void dialPhone() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(shopePhone))));
        Toast.makeText(this, ""+shopePhone, Toast.LENGTH_SHORT).show();


    }

    private void loadMyInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance("https://apna-mechanic-cd1cf-default-rtdb.firebaseio.com/").getReference("Users");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String name= ""+ds.child("name").getValue();
                    String email= ""+ds.child("email").getValue();
                    String phone= ""+ds.child("phone").getValue();
                    String profileImage= ""+ds.child("profileImage").getValue();
                    String accountType= ""+ds.child("accountType").getValue();
                    String city= ""+ds.child("city").getValue();
                    String myLatitude= ""+ds.child("latitude").getValue();
                    String myLongitude= ""+ds.child("longitude").getValue();



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void loadShopDetails() {
        DatabaseReference ref= FirebaseDatabase.getInstance("https://apna-mechanic-cd1cf-default-rtdb.firebaseio.com/").getReference("Users");
        ref.child(shopUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name= ""+dataSnapshot.child("name").getValue();
                mainshopname= ""+dataSnapshot.child("mainshopname").getValue();
                shopEmail= ""+dataSnapshot.child("shopEmail").getValue();
                shopePhone= ""+dataSnapshot.child("shopPhone").getValue();
                shopLatitude= ""+dataSnapshot.child("shoplatitude").getValue();
                shopLongitude = ""+dataSnapshot.child("shopLongitude").getValue();
                shopAddress = ""+dataSnapshot.child("shopAddress").getValue();

                String deleiverCharge = ""+dataSnapshot.child("deleiverCharge").getValue();
                String profilePic = ""+dataSnapshot.child("ProfilePic").getValue();
                String  ShopOpen  = ""+dataSnapshot.child("ShopOpen").getValue();

                //setData
                shopName.setText(mainshopname);
                email.setText(shopEmail);
                DeleiveryFee.setText("Deleivery Fee: Rs"+ deleiverCharge);
                address.setText(shopAddress);
                Phone.setText(shopePhone);
                if(ShopOpen.equals("true")){
                    openclose.setText("Open");
                }
                else  { openclose.setText("closed");

                }
                try {
                    Picasso.get().load(profilePic).into(shopIv);


                }catch (Exception e){

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadShopProducts() {
        productsList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://apna-mechanic-cd1cf-default-rtdb.firebaseio.com/").getReference("Products");
        reference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productsList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ModelProduct modelProduct= ds.getValue(ModelProduct.class);
                    productsList.add(modelProduct);

                }

                adapterProductUser= new AdapterProductUser(ShopDetails.this, productsList);
                productRV.setAdapter(adapterProductUser);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
