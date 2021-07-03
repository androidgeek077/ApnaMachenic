package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.splashscreen.Models.BookingModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewUserBooking extends AppCompatActivity {


    DatabaseReference CustomerReference;
    //    CustomerProfileAdapter mProductAdapter;
    RecyclerView mCustomerRecycVw;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_booking);
        auth=FirebaseAuth.getInstance();
        CustomerReference= FirebaseDatabase.getInstance().getReference().child("Booking").child(auth.getCurrentUser().getUid());
        mCustomerRecycVw=findViewById(R.id.main_recycler_vw);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mCustomerRecycVw.setLayoutManager(mLayoutManager);

        FirebaseRecyclerOptions<BookingModel> options=new FirebaseRecyclerOptions.Builder<BookingModel>()
                .setQuery(CustomerReference, BookingModel.class)
                .build();

        final FirebaseRecyclerAdapter<BookingModel, CustomersViewHolder> adapter=new FirebaseRecyclerAdapter<BookingModel, CustomersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CustomersViewHolder holder, final int position, @NonNull final BookingModel model) {


                DisplayMetrics displaymetrics = new DisplayMetrics();
             getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width

                holder.userName.setText(model.getName());

                holder.DateTV.setText(model.getDatetime());
                holder.userType.setText(model.getType());


//                holder.mMinusBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        count=holder.mTotalCount.getText().toString();
//                        countInt=Integer.parseInt(count);
//                        incrementalCount=countInt--;
//
//                        holder.mTotalCount.setText(countInt+"");
//                    }
//                });


            }

            @NonNull
            @Override
            public CustomersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_machenic_layout, viewGroup,false);
                CustomersViewHolder customersViewHolder=new CustomersViewHolder(view);
                return customersViewHolder;
            }
        };

        mCustomerRecycVw.setAdapter(adapter);
        adapter.startListening();

    }


    public static class CustomersViewHolder extends  RecyclerView.ViewHolder{


        ImageView postImage,mDelCustomerBtn;
        TextView userName;
        TextView DateTV;
        LinearLayout mItemCountLin;
        TextView userType;
        CardView cardView;

        ImageView mPlusBtn, mMinusBtn;
        TextView mTotalCount;
        public CustomersViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.userName);
            DateTV = (TextView) itemView.findViewById(R.id.DateTV);
            userType=itemView.findViewById(R.id.userType);
//            cardView=itemView.findViewById(R.id.cardVBtn);


        }
    
    }
}