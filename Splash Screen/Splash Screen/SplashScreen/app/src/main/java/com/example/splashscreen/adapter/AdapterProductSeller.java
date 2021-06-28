package com.example.splashscreen.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreen.EditProductActivity;
import com.example.splashscreen.FilterProduct;
import com.example.splashscreen.ModelProduct;
import com.example.splashscreen.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProductSeller extends RecyclerView.Adapter<AdapterProductSeller.HolderProductSeller> implements Filterable {

    public ArrayList<ModelProduct> productList, filterList;
    private Context context;
    private FilterProduct filter;

    public AdapterProductSeller(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProductSeller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_seller, parent, false);
        return new HolderProductSeller(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductSeller holder, int position) {
        //get data
        ModelProduct modelProduct = productList.get(position);
        String id = modelProduct.getProductId();
        String uid = modelProduct.getUid();
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountNote = modelProduct.getDiscountNote();
        String discountPrice = modelProduct.getDiscountPrice();
        String productCategory = modelProduct.getProductCategory();
        String productDescription = modelProduct.getProductDescription();
        String icon = modelProduct.getProductIcon();
        String availability = modelProduct.getProductAvailability();
        String title = modelProduct.getProductTitle();
        String timestamp = modelProduct.getTimestamp();
        String originalPrice = modelProduct.getOriginalPrice();


        //set data
        holder.titleTv.setText(title);
        holder.availabilityTv.setText(availability);
        holder.discountedNoteTv.setText(discountNote);
        holder.discountedPriceTv.setText("Rs" + discountPrice);
        holder.originalPriceTv.setText("Rs" + originalPrice);

        if (discountAvailable.equals("true")) {
            //product is on discount
            holder.discountedPriceTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setVisibility(View.VISIBLE);
            holder.originalPriceTv.setPaintFlags(holder.originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);//add strike through on original price

        } else {
            //product is not on discount
            holder.discountedPriceTv.setVisibility(View.GONE);
            holder.discountedNoteTv.setVisibility(View.GONE);
            holder.originalPriceTv.setPaintFlags(0);
        }
        try {
            Picasso.get().load(icon).placeholder(R.drawable.ic_baseline_add_shopping_cart).into(holder.productIconIv);

        } catch (Exception e) {
            holder.productIconIv.setImageResource(R.drawable.ic_baseline_add_shopping_cart);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle item clicks, show item details (in bottom sheet)
                detailsBottomSheet(modelProduct); //here modelProduct contains details of clicked products

            }
        });

    }

    private void detailsBottomSheet(ModelProduct modelProduct) {
        //bottom Sheet
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        //inflate view for bottom sheet
        View view = LayoutInflater.from(context).inflate(R.layout.bs_product_details_seller, null);
        //set view to bottom sheet
        bottomSheetDialog.setContentView(view);


        //init views of bottom sheet
        ImageButton backBtn = view.findViewById(R.id.backBtn);
        ImageButton deleteBtn = view.findViewById(R.id.deleteBtn);
        ImageButton editBtn = view.findViewById(R.id.editBtn);
        ImageView productIconIv = view.findViewById(R.id.productIconIv);
        TextView discountNoteTv = view.findViewById(R.id.discountNoteTv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        TextView categoryTv = view.findViewById(R.id.categoryTv);
        TextView availabilityTv = view.findViewById(R.id.availabilityTv);
        TextView discountedPriceTv = view.findViewById(R.id.discountedPriceTv);
        TextView originalPriceTv = view.findViewById(R.id.originalPriceTv);

        //get data
        String id = modelProduct.getProductId();
        String uid = modelProduct.getUid();
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountNote = modelProduct.getDiscountNote();
        String discountPrice = modelProduct.getDiscountPrice();
        String productCategory = modelProduct.getProductCategory();
        String productDescription = modelProduct.getProductDescription();
        String icon = modelProduct.getProductIcon();
        String availability = modelProduct.getProductAvailability();
        String title = modelProduct.getProductTitle();
        String timestamp = modelProduct.getTimestamp();
        String originalPrice = modelProduct.getOriginalPrice();

        //set data
        titleTv.setText(title);
        descriptionTv.setText(productDescription);
        categoryTv.setText(productCategory);
        availabilityTv.setText(availability);
        discountNoteTv.setText(discountNote);
        discountedPriceTv.setText("Rs" + discountPrice);
        originalPriceTv.setText("Rs" + originalPrice);

        if (discountAvailable.equals("true")) {
            //product is on discount
            discountedPriceTv.setVisibility(View.VISIBLE);
            discountNoteTv.setVisibility(View.VISIBLE);
            originalPriceTv.setPaintFlags(originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);//add strike through on original price

        } else {
            //product is not on discount
            discountedPriceTv.setVisibility(View.GONE);
            discountNoteTv.setVisibility(View.GONE);
        }

        try {
            Picasso.get().load(icon).placeholder(R.drawable.ic_baseline_add_shopping_cart).into(productIconIv);

        } catch (Exception e) {
            productIconIv.setImageResource(R.drawable.ic_baseline_add_shopping_cart);
        }

        //show dialog
        bottomSheetDialog.show();

        //edit click
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();

                Intent i = new Intent(context, EditProductActivity.class);
                i.putExtra("product", modelProduct);
                context.startActivity(i);
            }
        });

        //delete click
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                //show delete confirm dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete product" + title + " ?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete
                                deleteProduct(id); //id is the product id
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //cancel, dismiss dialog
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });

        //back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dismiss bottom sheet
                bottomSheetDialog.dismiss();

            }
        });

    }

    private void deleteProduct(String id) {
        //delete product using its id
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://apna-mechanic-cd1cf-default-rtdb.firebaseio.com/")
                .getReference("Products");
        reference.child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //product delete
                        Toast.makeText(context, "Product deleted!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed deleting product
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FilterProduct(this, filterList);
        }
        return filter;
    }

    class HolderProductSeller extends RecyclerView.ViewHolder {

        /*holds views of recyclerview*/

        private ImageView productIconIv;
        private TextView discountedNoteTv, titleTv, availabilityTv, discountedPriceTv, originalPriceTv;
        ;

        public HolderProductSeller(@NonNull View itemView) {
            super(itemView);

            productIconIv = itemView.findViewById(R.id.productIconIv);
            discountedNoteTv = itemView.findViewById(R.id.discountedNoteTv);
            titleTv = itemView.findViewById(R.id.titleTv);
            availabilityTv = itemView.findViewById(R.id.availabilityTv);
            discountedPriceTv = itemView.findViewById(R.id.discountedPriceTv);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);


        }
    }
}
