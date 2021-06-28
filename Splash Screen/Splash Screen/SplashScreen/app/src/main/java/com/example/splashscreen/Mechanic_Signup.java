package com.example.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.splashscreen.Models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Mechanic_Signup extends AppCompatActivity {
    TextView goTologin;
    RadioGroup mGenderradioGrp;
    RadioButton MaleRB, FemaleRB;
    TextInputEditText emailET, nameET, phoneET, PasswordET, CNICET, expertise;
    String emailStr, nameStr, phoneStr, passwordStr, cnicStr, ExperienceStr;
    Button btnsignup;
    ProgressBar mProgressBar;
    FirebaseAuth auth;
    DatabaseReference myRef;
    LinearLayout getView;
    CheckBox TermNConditioncCb;
    Typeface face;
    TextView termNConditionTV;
    TextView goTologinTv, AlreadyAccountTv;
    private Button mSelectImgBtn;
    ImageView mProfilePic;
    private static final int RC_PHOTO_PICKER = 1;
    private Uri selectedProfileImageUri;
    private StorageReference mProfilePicStorageReference;
    StorageReference profilePicRef;
    private String downloadUri;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic__signup);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        myRef = FirebaseDatabase.getInstance().getReference("Users");
        mProfilePicStorageReference = FirebaseStorage.getInstance().getReference();

        mProfilePic = findViewById(R.id.selectedImg);
        mSelectImgBtn = findViewById(R.id.btn_selectimg);
        mSelectImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfilePicture();
            }
        });

        WidgetRegistration();
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringConverstion();
                if (emailStr.isEmpty()) {
                    emailET.setError("Enter Email");
                } else if (nameStr.isEmpty()) {
                    emailET.setError("Enter Name");
                } else if (phoneStr.isEmpty()) {
                    phoneET.setError("Enter Phone No.");
                } else if (passwordStr.isEmpty()) {
                    PasswordET.setError("Enter Password");
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                profilePicRef = mProfilePicStorageReference.child(selectedProfileImageUri.getLastPathSegment());
                                profilePicRef.putFile(selectedProfileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                downloadUri = uri.toString();
                                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                userModel user = new userModel(uid, nameStr,phoneStr,downloadUri,  emailStr,"Car",ExperienceStr, "","", "admin");
                                                myRef.child(uid).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(Mechanic_Signup.this, "SignUp successfully!", Toast.LENGTH_SHORT).show();
                                                        mProgressBar.setVisibility(View.INVISIBLE);
//                                                        startActivity(new Intent(Mechanic_Signup.this, MainActivity.class));
                                                        finish();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Mechanic_Signup.this, "Data register failed", Toast.LENGTH_SHORT).show();
                                                        mProgressBar.setVisibility(View.INVISIBLE);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mProgressBar.setVisibility(View.GONE);
                                        Toast.makeText(Mechanic_Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Mechanic_Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        goTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void WidgetRegistration() {

        emailET = findViewById(R.id.emailET);
        nameET = findViewById(R.id.nameET);

        phoneET = findViewById(R.id.phoneET);
        expertise = findViewById(R.id.expertise);

        PasswordET = findViewById(R.id.PasswordET);

        btnsignup = findViewById(R.id.btnsignup);

        goTologin = findViewById(R.id.goTologinTv);
        mProgressBar = findViewById(R.id.mProgressBar);
        getView = findViewById(R.id.getView);



    }

    void StringConverstion() {
        emailStr = emailET.getText().toString();
        nameStr = nameET.getText().toString();
        phoneStr = phoneET.getText().toString();
        passwordStr = PasswordET.getText().toString();
        cnicStr = CNICET.getText().toString();
        ExperienceStr = expertise.getText().toString();
    }

    public void getProfilePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            selectedProfileImageUri = selectedImageUri;
            mProfilePic.setImageURI(selectedImageUri);
            mProfilePic.setVisibility(View.VISIBLE);
        }
    }
}
