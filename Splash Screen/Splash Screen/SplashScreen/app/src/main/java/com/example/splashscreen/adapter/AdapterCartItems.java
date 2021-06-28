package com.example.splashscreen.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splashscreen.MainUserActivity;
import com.example.splashscreen.Models.ModelCartitems;
import com.example.splashscreen.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterCartItems extends RecyclerView.Adapter<AdapterCartItems.HoldercartItems> {
    private Context context;
    private ArrayList<ModelCartitems> cartitems;

    public AdapterCartItems(Context context, ArrayList<ModelCartitems> cartitems) {
        this.context = context;
        this.cartitems = cartitems;

    }

    @NonNull
    @Override
    public HoldercartItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cartitems, parent, false);
        return new HoldercartItems(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HoldercartItems holder, int position) {
        ModelCartitems modelCartitems = cartitems.get(position);
        String id = modelCartitems.getId();
        String getpId = modelCartitems.getpId();
        String title = modelCartitems.getName();
        final String cost = modelCartitems.getCost();
        String price = modelCartitems.getPrice();
        String Quantity = modelCartitems.getQuantity();

        holder.itemTitleTv.setText("" + title);
        holder.ItemPriceTv.setText("" + cost);
        holder.ItemQuantityTv.setText("" + Quantity);
        holder.ItemPriceEachTv.setText("" + price);

        holder.ItemRemoveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyDB easyDB = EasyDB.init(context, "ITEMS_DB")
                        .setTableName("ITEMS_TABLE")
                        .addColumn(new Column("Item_Id", new String[]{"text", "unique"}))
                        .addColumn(new Column("Item_PID", new String[]{"text", "not null"}))
                        .addColumn(new Column("Item_name", new String[]{"text", "not null"}))
                        .addColumn(new Column("Item_Price_Each", new String[]{"text", "not null"}))
                        .addColumn(new Column("Item_Price", new String[]{"text", "not null"}))
                        .addColumn(new Column("Item_Quantity", new String[]{"text", "not null"}))
                        .doneTableColumn();


                easyDB.deleteRow(1, id);
                Toast.makeText(context, "Removed from cart", Toast.LENGTH_SHORT).show();

                cartitems.remove(position);
                notifyItemChanged(position);
                notifyDataSetChanged();

                double tx = 0.00;
                if (((MainUserActivity) context).allTotalPriceTv != null)
                    tx = Double.parseDouble((((MainUserActivity) context).allTotalPriceTv.getText().toString().trim().replace("Rs", "")));
                double totalPrice = tx - Double.parseDouble(cost.replace("$",""));
                double deliveryFee = Double.parseDouble((((MainUserActivity) context).deliveryFee.replace("Rs", "")));
                double sTotalPrice = Double.parseDouble(String.format("%.2f", totalPrice)) - Double.parseDouble(String.format("%.2f", deliveryFee));
                ((MainUserActivity) context).allTotalPrice = 0.00;
                ((MainUserActivity) context).sTotaltv.setText("$" + String.format("%.2f", sTotalPrice));
                ((MainUserActivity) context).allTotalPriceTv.setText("$" + String.format("%.2f", Double.parseDouble(String.format("%.2f", totalPrice))));


            }
        });
    }

    @Override
    public int getItemCount() {
        return cartitems.size();
    }

    class HoldercartItems extends RecyclerView.ViewHolder {
        private TextView itemTitleTv, ItemPriceTv, ItemPriceEachTv, ItemQuantityTv, ItemRemoveTv;

        public HoldercartItems(@NonNull View itemView) {
            super(itemView);
            itemTitleTv = itemView.findViewById(R.id.itemTitleTv);
            ItemPriceTv = itemView.findViewById(R.id.itemPriceTv);
            ItemPriceEachTv = itemView.findViewById(R.id.ItemPriceEachtv);
            ItemQuantityTv = itemView.findViewById(R.id.itemQuantityTv);
            ItemRemoveTv = itemView.findViewById(R.id.ItemRemoveTv);



        }
    }
}

