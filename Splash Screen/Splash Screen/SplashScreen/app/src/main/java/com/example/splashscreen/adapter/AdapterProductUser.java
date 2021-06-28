package com.example.splashscreen.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreen.FilterProduct;
import com.example.splashscreen.FilterProductUser;
import com.example.splashscreen.ModelProduct;
import com.example.splashscreen.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import interfaces.ProductInterface;
import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterProductUser extends RecyclerView.Adapter<AdapterProductUser.HolderProductUser> implements Filterable
{
    private Context context;
    public ArrayList <ModelProduct> productsList, filterList;
    private FilterProductUser filter;
   // ProductInterface.onProductClickListener onProductClickListener;

   /* public void setOnProductClickListener(ProductInterface.onProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }*/

    public AdapterProductUser(Context context, ArrayList<ModelProduct> productsList) {
        this.context = context;
        this.productsList = productsList;
        this.filterList= productsList;
    }

    @NonNull
    @Override
    public HolderProductUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_rowproductuser, parent, false);
        return  new HolderProductUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductUser holder, int position) {
        ModelProduct modelProduct= productsList.get(position);
        String discountavailable= modelProduct.getDiscountAvailable();
        String discountNote= modelProduct.getDiscountNote();
        String discountPrice= modelProduct.getDiscountPrice();
        String productCatagory= modelProduct.getProductCategory();
        String originalPrice= modelProduct.getOriginalPrice();
        String ProductDescription= modelProduct.getProductDescription();
        String ProductTitle= modelProduct.getProductTitle();
        String ProductQuantity= modelProduct.getProductAvailability();
        String ProductId= modelProduct.getProductId();
        String timestamp= modelProduct.getTimestamp();
        String ProductIcon= modelProduct.getProductIcon();
//setdata
        holder.titleTv.setText(ProductTitle);
        holder.discountedNoteTv.setText(discountNote);
        holder.descriptionTv.setText(ProductDescription);
        holder.originalPriceTv.setText(originalPrice);
        holder.discountedPriceTv.setText(discountPrice);
        if(discountavailable.equals("true")){
            holder.discountedPriceTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setVisibility(View.VISIBLE);
            holder.originalPriceTv.setPaintFlags(holder.originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        }
        else{
            holder.discountedPriceTv.setVisibility(View.GONE);
            holder.discountedNoteTv.setVisibility(View.GONE);
            holder.originalPriceTv.setPaintFlags(0);
        }
        try{ Picasso.get().load(ProductIcon).placeholder(R.drawable.ic_shop_black).into(holder.productIconIv);

        }catch (Exception e){
            holder.productIconIv.setImageResource(R.drawable.ic_shop_black);

        }
        holder.addToCartTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  onProductClickListener.OnAddToCartClick(v,modelProduct , position);

                //add product to cart
                showQuantityDailog(modelProduct);

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show products details
            }
        });



    }

    private double cost = 0;
    private double finalCost = 0;
    int quantity = 0;
    private void showQuantityDailog(ModelProduct modelProduct) {
        //inflate layout for dialog
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_quantity, null);
        //init layout views
        ImageView productIv = view.findViewById(R.id.productIv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView pQuantityTv = view.findViewById(R.id.pQuantityTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        TextView discountednoteTv = view.findViewById(R.id.discountednoteTv);
        TextView originalPriceTv = view.findViewById(R.id.originalPriceTv);
        TextView priceDiscountedTv = view.findViewById(R.id.priceDiscountedTv);
        TextView finalPriceTv = view.findViewById(R.id.finalPriceTv);
        ImageButton decrementbtn = view.findViewById(R.id.decrementbtn);
        TextView quantityTv = view.findViewById(R.id.quantityTv);
        ImageButton incrementbtn = view.findViewById(R.id.incrementbtn);
        Button continueBtn = view.findViewById(R.id.continueBtn);

        //get data from model
       String productId = modelProduct.getProductId();
       String title = modelProduct.getProductTitle();
       String productAvailability = modelProduct.getProductAvailability();
       String description = modelProduct.getProductDescription();
       String discountNote = modelProduct.getDiscountNote();
       String image = modelProduct.getProductIcon();


       String price;
       if (modelProduct.getDiscountAvailable().equals("true")){
           //product have discount
           price = modelProduct.getDiscountPrice();
           discountednoteTv.setVisibility(View.VISIBLE);
           originalPriceTv.setPaintFlags(originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
       }
       else {
           //product have no discount
           discountednoteTv.setVisibility(View.GONE);
           priceDiscountedTv.setVisibility(View.GONE);
           price = modelProduct.getOriginalPrice();

       }

       cost = Double.parseDouble(price.replaceAll("Rs",""));
       finalCost = Double.parseDouble(price.replaceAll("Rs",""));
       quantity = 1;

       //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        //set data
        try {
            Picasso.get().load(image).placeholder(R.drawable.ic_cart_gray).into(productIv);
        }
        catch (Exception e){
            productIv.setImageResource(R.drawable.ic_cart_gray);
        }

        titleTv.setText(""+title);
        pQuantityTv.setText(""+productAvailability);
        descriptionTv.setText(""+description);
        discountednoteTv.setText(""+discountNote);
        quantityTv.setText(""+quantity);
        originalPriceTv.setText("Rs"+modelProduct.getOriginalPrice());
        priceDiscountedTv.setText("Rs"+modelProduct.getDiscountPrice());
        finalPriceTv.setText("Rs"+finalCost);

        AlertDialog dialog = builder.create();
        dialog.show();

        //increase quantity of the product
        incrementbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalCost = finalCost + cost;
                quantity++;

                finalPriceTv.setText("Rs"+finalCost);
                quantityTv.setText(""+quantity);
            }
        });

        //decrement quatity of product, only if quantity is > 1
        decrementbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>1){
                    finalCost = finalCost - cost;
                    quantity --;

                    finalPriceTv.setText("Rs"+finalCost);
                    quantityTv.setText(""+quantity);
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleTv.getText().toString().trim();
                String priceEach = originalPriceTv.getText().toString().trim().replace("Rs","");
                String price = finalPriceTv.getText().toString().trim().replace("Rs","");
                String quantity = quantityTv.getText().toString().trim();

                //add to db(SQLite)
                addToCart(productId, title, priceEach, price, quantity);

                dialog.dismiss();

            }
        });
    }

    private int itemId = 1;
    private void addToCart(String productId, String title, String priceEach, String price, String quantity) {
        itemId++;

        EasyDB easyDB = EasyDB.init(context, "ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text", "unique"}))
                .addColumn(new Column("Item_PID", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Name", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price_Each", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Quantity", new String[]{"text", "not null"}))
                .doneTableColumn();

        Boolean b = easyDB.addData("Item_Id", itemId)
                .addData("Item_PID", productId)
                .addData("Item_Name", title)
                .addData("Item_Price_Each", priceEach)
                .addData("Item_Price", price)
                .addData("Item_Quantity", quantity)
                .doneDataAdding();

        Toast.makeText(context, "Added To Cart...", Toast.LENGTH_SHORT).show();










    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterProductUser(this, filterList);

        }
        return filter;
    }

    class HolderProductUser extends RecyclerView.ViewHolder{
        //uid views
        private ImageView productIconIv;
        private TextView discountedNoteTv,titleTv,descriptionTv,availabilityTv,addToCartTv,discountedPriceTv,originalPriceTv;

        public HolderProductUser(@NonNull View itemView) {
            super(itemView);
            productIconIv = itemView.findViewById(R.id.productIconIv);
            discountedNoteTv = itemView.findViewById(R.id.discountedNoteTv);
            titleTv= itemView.findViewById(R.id.titleTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            availabilityTv = itemView.findViewById(R.id.availabilityTv);
            addToCartTv = itemView.findViewById(R.id.addToCartTv);
            discountedPriceTv = itemView.findViewById(R.id.discountedPriceTv);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);
        }
    }
}

