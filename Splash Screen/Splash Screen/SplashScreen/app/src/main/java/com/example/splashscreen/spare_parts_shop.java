package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.splashscreen.Models.AllCategory;
import com.example.splashscreen.Models.CategoryItem;
import com.example.splashscreen.adapter.MainRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class spare_parts_shop extends AppCompatActivity {

    RecyclerView mainCategoryRecycler;
    MainRecyclerAdapter mainRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_parts_shop);

        List<CategoryItem> categoryItemList = new ArrayList<>();
        categoryItemList.add(new CategoryItem(1, R.drawable.car1));
        categoryItemList.add(new CategoryItem(1, R.drawable.car2));
        categoryItemList.add(new CategoryItem(1, R.drawable.car3));
        categoryItemList.add(new CategoryItem(1, R.drawable.car4));
        categoryItemList.add(new CategoryItem(1, R.drawable.car5));
        categoryItemList.add(new CategoryItem(1, R.drawable.car6));


        List<CategoryItem> categoryItemList2 = new ArrayList<>();
        categoryItemList2.add(new CategoryItem(1, R.drawable.bike1));
        categoryItemList2.add(new CategoryItem(1, R.drawable.bike2));
        categoryItemList2.add(new CategoryItem(1, R.drawable.bike3));
        categoryItemList2.add(new CategoryItem(1, R.drawable.bike4));
        categoryItemList2.add(new CategoryItem(1, R.drawable.bike5));
        categoryItemList2.add(new CategoryItem(1, R.drawable.bike6));

        List<CategoryItem> categoryItemList3 = new ArrayList<>();
        categoryItemList3.add(new CategoryItem(1, R.drawable.rickshaw1));
        categoryItemList3.add(new CategoryItem(1, R.drawable.rickshaw2));
        categoryItemList3.add(new CategoryItem(1, R.drawable.rickshaw3));
        categoryItemList3.add(new CategoryItem(1, R.drawable.rickshaw4));
        categoryItemList3.add(new CategoryItem(1, R.drawable.rickshaw5));
        categoryItemList3.add(new CategoryItem(1, R.drawable.rickshaw6));

        List<CategoryItem> categoryItemList4 = new ArrayList<>();
        categoryItemList4.add(new CategoryItem(1, R.drawable.truck1));
        categoryItemList4.add(new CategoryItem(1, R.drawable.truck2));
        categoryItemList4.add(new CategoryItem(1, R.drawable.truck3));
        categoryItemList4.add(new CategoryItem(1, R.drawable.truck4));
        categoryItemList4.add(new CategoryItem(1, R.drawable.truck5));
        categoryItemList4.add(new CategoryItem(1, R.drawable.truck6));

        List<CategoryItem> categoryItemList5 = new ArrayList<>();
        categoryItemList5.add(new CategoryItem(1, R.drawable.tractor1));
        categoryItemList5.add(new CategoryItem(1, R.drawable.tractor2));
        categoryItemList5.add(new CategoryItem(1, R.drawable.tractor3));
        categoryItemList5.add(new CategoryItem(1, R.drawable.tractor4));
        categoryItemList5.add(new CategoryItem(1, R.drawable.tractor5));
        categoryItemList5.add(new CategoryItem(1, R.drawable.tractor6));

        List<CategoryItem> categoryItemList6 = new ArrayList<>();
        categoryItemList6.add(new CategoryItem(1, R.drawable.buss1));
        categoryItemList6.add(new CategoryItem(1, R.drawable.buss2));
        categoryItemList6.add(new CategoryItem(1, R.drawable.buss3));
        categoryItemList6.add(new CategoryItem(1, R.drawable.buss4));
        categoryItemList6.add(new CategoryItem(1, R.drawable.buss5));
        categoryItemList6.add(new CategoryItem(1, R.drawable.buss6));




        List<AllCategory> allCategoryList = new ArrayList<>();
        allCategoryList.add(new AllCategory( "Car", categoryItemList));
       allCategoryList.add(new AllCategory("Motor Cycle", categoryItemList2));
       allCategoryList.add(new AllCategory("Rickshaw", categoryItemList3));
       allCategoryList.add(new AllCategory("Truck", categoryItemList4));
       allCategoryList.add(new AllCategory("Tractor", categoryItemList5));
       allCategoryList.add(new AllCategory("Bus", categoryItemList6));
        setMainCategoryRecycler(allCategoryList);

   }
    private void setMainCategoryRecycler(List<AllCategory> allCategoryList){
        mainCategoryRecycler = findViewById(R.id.main_recycler);
       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this);
       mainCategoryRecycler.setLayoutManager(layoutManager);
       mainRecyclerAdapter = new MainRecyclerAdapter(this, allCategoryList);
       mainCategoryRecycler.setAdapter(mainRecyclerAdapter);

   }
}
