package com.example.splashscreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.splashscreen.Models.userModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ManageMechanics extends AppCompatActivity {

    ProgressBar mProgressBar;
    DatabaseReference CustomerReference;
    FirebaseAuth auth;
    //    CustomerProfileAdapter mProductAdapter;
    RecyclerView mCustomerRecycVw;
    String count;
    private Dialog dialog;
    private EditText nameET, emailET, PasswordET, phoneET, expertise;
    Spinner mechanicTypeSpnr;
    private Button btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_mechanics);

        auth = FirebaseAuth.getInstance();
        CustomerReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mCustomerRecycVw = findViewById(R.id.main_recycler_vw);
        mProgressBar = findViewById(R.id.mProgressBar);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mCustomerRecycVw.setLayoutManager(mLayoutManager);
        getData();
    }

    void getData() {
        FirebaseRecyclerOptions<userModel> options = new FirebaseRecyclerOptions.Builder<userModel>()
                .setQuery(CustomerReference, userModel.class)
                .build();

        final FirebaseRecyclerAdapter<userModel, UserViewHolder> adapter = new FirebaseRecyclerAdapter<userModel, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final UserViewHolder holder, final int position, @NonNull final userModel model) {


                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width

                holder.userNameTV.setText(model.getName());
                Glide.with(ManageMechanics.this).load(model.getImageurl()).into(holder.userprofileIV);

                holder.userAddressTV.setText("Mechanic Type:" + model.getMechanictype());
                holder.userCnicTV.setText("Experience: " + model.getExperienceyears());
                holder.mobileNoTV.setText("Cell#:" + model.getContact());
                holder.userEmailTV.setText("Email: " + model.getMailaddress());
                holder.EditMacBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ManageMechanics.this, EditMechanicActivity.class);
                        intent.putExtra("name", model.getName());
                        intent.putExtra("name", model.getName());
                    }
                });
                holder.deleteMacBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       new AlertDialog.Builder(ManageMechanics.this)
                                // set message, title, and icon
                                .setTitle("Delete")
                                .setMessage("Do you want to Delete thi User?")
                                .setIcon(R.drawable.ic_delete_white)

                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        DatabaseReference key = getRef(position);
                                        key.removeValue();
                                        dialog.dismiss();
                                    }

                                })
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();

                                    }
                                })
                                .create().show();

                    }
                });
                holder.EditMacBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog(model);
                    }
                });


            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_layout, viewGroup, false);
                UserViewHolder UserViewHolder = new UserViewHolder(view);
                mProgressBar.setVisibility(View.GONE);

                return UserViewHolder;
            }
        };

        mCustomerRecycVw.setAdapter(adapter);
        adapter.startListening();
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView userprofileIV;
        TextView userNameTV, userAddressTV, userCnicTV, mobileNoTV, userEmailTV;
        Button EditMacBtn;
        LinearLayout mItemCountLin;
        Button deleteMacBtn;
        CardView cardView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameTV = (TextView) itemView.findViewById(R.id.userNameTV);
            userprofileIV = itemView.findViewById(R.id.userprofileIV);
            userAddressTV = itemView.findViewById(R.id.userAddressTV);
            userCnicTV = itemView.findViewById(R.id.userCnicTV);
            mobileNoTV = itemView.findViewById(R.id.mobileNoTV);
            userEmailTV = itemView.findViewById(R.id.userEmailTV);
            EditMacBtn = itemView.findViewById(R.id.EditMacBtn);
            deleteMacBtn = itemView.findViewById(R.id.deleteMacBtn);
        }
    }

    private void openDialog(userModel model) {
        dialog = new Dialog(ManageMechanics.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_box);
        dialog.getWindow().getAttributes().windowAnimations = R.style.Theme_AppCompat_DayNight_Dialog_Alert;
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        dialog.setCancelable(true);
        nameET = dialog.findViewById(R.id.nameET);

        emailET = dialog.findViewById(R.id.emailET);
        PasswordET = dialog.findViewById(R.id.PasswordET);
        phoneET = dialog.findViewById(R.id.phoneET);
        expertise = dialog.findViewById(R.id.expertise);
        mechanicTypeSpnr = dialog.findViewById(R.id.mechanicTypeSpnr);
        btnsignup = dialog.findViewById(R.id.btnsignup);
        nameET.setText(model.getName());
        expertise.setText(model.getExperienceyears());
        phoneET.setText(model.getContact());
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userModel model1 = new userModel(model.getUserid(), nameET.getText().toString(), phoneET.getText().toString(), model.getImageurl(), model.getMailaddress(), mechanicTypeSpnr.getSelectedItem().toString(), expertise.getText().toString(), "", "", model.getUsertype());
                CustomerReference.child(model.getUserid()).setValue(model1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ManageMechanics.this, "User Updated Successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        dialog.show();
    }


}