 package com.example.splashscreen;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreen.Models.ModelCartitems;
import com.example.splashscreen.adapter.AdapterCartItems;
import com.example.splashscreen.adapter.ShowProductsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class MainUserActivity extends AppCompatActivity {

    private TextView tabProductsTv, tabOrdersTv, filteredProductsTv;
    private EditText searchProductEt;
    private RelativeLayout productsRl, ordersRl;
    private ImageButton filterProductBtn;
    private RecyclerView productsRv;
    private ArrayList<ModelProduct> productList;
    private ShowProductsAdapter adapterProductSeller;
    private ImageButton addProductBtn;
    public String deliveryFee;

    DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;
    ValueEventListener valueEventListener;

    private FirebaseAuth firebaseAuth;

    private ArrayList<ModelCartitems> cartItemList;
    private AdapterCartItems adapterCartItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        deliveryFee = "0.0";

        addProductBtn = findViewById(R.id.addProductBtn);
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
        deleteCartData();


        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCartDialog();
            }
        });

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
/*
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
        });*/

        filterProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainUserActivity.this);
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

    private void deleteCartData() {

        EasyDB easyDB = EasyDB.init(this, "ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text", "unique"}))
                .addColumn(new Column("Item_PID", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_name", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price_Each", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Quantity", new String[]{"text", "not null"}))
                .doneTableColumn();
        easyDB.deleteAllDataFromTable();//delete all records from cart
    }

    public double allTotalPrice = 0.00;
    public TextView sTotaltv, dFeetv, totaltv, allTotalPriceTv;

    private void showCartDialog() {

        cartItemList = new ArrayList<>();

        View view = LayoutInflater.from(this).inflate(R.layout.dialogue_cart, null);
        RecyclerView cartItemsRv = view.findViewById(R.id.cartItemsRv);
        TextView sTotaltv = view.findViewById(R.id.sTotaltv);
        TextView dFeetv = view.findViewById(R.id.dFeetv);
        TextView totaltv = view.findViewById(R.id.totaltv);
        Button checkoutbtn = view.findViewById(R.id.checkoutbtn);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        EasyDB easyDB = EasyDB.init(this, "ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text", "unique"}))
                .addColumn(new Column("Item_PID", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_name", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price_Each", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Quantity", new String[]{"text", "not null"}))
                .doneTableColumn();

        Cursor res = easyDB.getAllData();
        while (res.moveToNext()) {
            String id = res.getString(1);
            String pId = res.getString(2);
            String name = res.getString(3);
            String price = res.getString(4);
            String cost = res.getString(5);
            String quantity = res.getString(6);

            allTotalPrice = allTotalPrice + Double.parseDouble(cost);

            ModelCartitems modelCartItem = new ModelCartitems(
                    "" + id,
                    "" + pId,
                    "" + name,
                    "" + price,
                    "" + cost,
                    "" + quantity
            );

            cartItemList.add(modelCartItem);
        }
        adapterCartItems = new AdapterCartItems(this , cartItemList);
        cartItemsRv.setAdapter(adapterCartItems);

        dFeetv.setText("Rs" +deliveryFee);
        sTotaltv.setText("Rs"+String.format("%.2f", allTotalPrice));

        if (allTotalPriceTv != null)
          allTotalPriceTv.setText("Rs"+(allTotalPrice +Double.parseDouble((deliveryFee.replace("Rs", "")))));

        //show dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        //reset total price on dialog dismiss
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                allTotalPrice = 0.00;
            }
        });


    }

    private void loadFilteredProducts(String selected) {
        productList = new ArrayList<>();
        /*.child(firebaseAuth.getUid()).child("Products")*/
        //get all products
        DatabaseReference reference = FirebaseDatabase.getInstance("https://apna-mechanic-cd1cf-default-rtdb.firebaseio.com/").getReference("Products");
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
                adapterProductSeller = new ShowProductsAdapter(MainUserActivity.this, productList);
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
        DatabaseReference reference = FirebaseDatabase.getInstance("https://apna-mechanic-cd1cf-default-rtdb.firebaseio.com/").getReference("Products");
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
                adapterProductSeller = new ShowProductsAdapter(MainUserActivity.this, productList);
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






