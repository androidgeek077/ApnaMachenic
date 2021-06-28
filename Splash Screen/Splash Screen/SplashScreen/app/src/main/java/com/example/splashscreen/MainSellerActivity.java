package com.example.splashscreen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreen.adapter.AdapterProductSeller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainSellerActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;
    ValueEventListener valueEventListener;
    private TextView tabProductsTv, tabOrdersTv, filteredProductsTv;
    private EditText searchProductEt;
    private RelativeLayout productsRl, ordersRl;
    private ImageButton filterProductBtn;
    private RecyclerView productsRv;
    private ArrayList<ModelProduct> productList;
    private AdapterProductSeller adapterProductSeller;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seller);

        tabProductsTv = findViewById(R.id.tabProductsTv);
        tabOrdersTv = findViewById(R.id.tabOrdersTv);
        filteredProductsTv = findViewById(R.id.filteredProductsTv);
        searchProductEt = findViewById(R.id.searchProductEt);
        filterProductBtn = findViewById(R.id.filterProductBtn);
        productsRl = findViewById(R.id.productsRl);
        ordersRl = findViewById(R.id.ordersRl);
        productsRv = findViewById(R.id.productsRv);

        firebaseAuth = FirebaseAuth.getInstance();

        loadAllProducts();
        //showProd();

        showProductsUI();

        //search
        searchProductEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    adapterProductSeller.getFilter().filter(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tabProductsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Load products
                showProductsUI();
                loadAllProducts();
            }
        });

        tabOrdersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load orders
                showOrdersUI();
            }
        });

        filterProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainSellerActivity.this);
                builder.setTitle("Choose Category:")
                        .setItems(Constants.productCategories1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //get selected item
                                String selected = Constants.productCategories1[which];
                                filteredProductsTv.setText(selected);
                                if (selected.equals("All")) {
                                    //load all
                                    loadAllProducts();
                                    //showProd();
                                } else {
                                    //load filtered
                                    loadFilteredProducts(selected);
                                }
                            }
                        })
                        .show();
            }
        });

    }

    private void loadFilteredProducts(String selected) {
        productList = new ArrayList<>();
        /*.child(firebaseAuth.getUid()).child("Products")*/
        //get all products
        DatabaseReference reference = FirebaseDatabase.getInstance("https://apna-mechanic-cd1cf-default-rtdb.firebaseio.com/")
                .getReference("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //before getting reset list
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String productCategory = "" + ds.child("productCategory").getValue();
                    //if selected category matches product category then add in list
                    if (selected.equals(productCategory)) {
                        ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                        productList.add(modelProduct);
                    }
                }
                //setup adapter
                adapterProductSeller = new AdapterProductSeller(MainSellerActivity.this, productList);
                //set adapter
                productsRv.setAdapter(adapterProductSeller);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadAllProducts() {
        productList = new ArrayList<>();
        //get all products
        //.child(firebaseAuth.getUid()).child("Products")
        DatabaseReference reference = FirebaseDatabase.getInstance("https://apna-mechanic-cd1cf-default-rtdb.firebaseio.com/")
                .getReference("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //before getting reset list
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                    productList.add(modelProduct);
                }
                //setup adapter
                adapterProductSeller = new AdapterProductSeller(MainSellerActivity.this, productList);
                //set adapter
                productsRv.setAdapter(adapterProductSeller);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void showProductsUI() {
        //show products ui and hide orders ui
        productsRl.setVisibility(View.VISIBLE);
        ordersRl.setVisibility(View.GONE);

        loadAllProducts();

        tabProductsTv.setTextColor(getResources().getColor(R.color.black));
        tabProductsTv.setBackgroundResource(R.drawable.shape_rect04);

        tabOrdersTv.setTextColor(getResources().getColor(R.color.white));
        tabOrdersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

    }


    private void showOrdersUI() {
        //show orders ui and hide products ui
        productsRl.setVisibility(View.GONE);
        ordersRl.setVisibility(View.VISIBLE);

        tabProductsTv.setTextColor(getResources().getColor(R.color.white));
        tabProductsTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        tabOrdersTv.setTextColor(getResources().getColor(R.color.black));
        tabOrdersTv.setBackgroundResource(R.drawable.shape_rect04);

    }

}






