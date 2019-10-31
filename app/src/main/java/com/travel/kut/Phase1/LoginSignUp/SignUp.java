package com.travel.kut.Phase1.LoginSignUp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.travel.kut.MainActivity;
import com.travel.kut.Phase1.Models.User_item_model;
import com.travel.kut.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.travel.kut.Phase1.LoginSignUp.LoginFragment.isValidEmail;

public class SignUp extends Fragment {


    EditText user_Email, user_name, user_pass;
    MaterialButton signUpbtn;
    CircleImageView userpic;
    private FirebaseAuth mAuth;
    Uri fileuri, tempUri;

    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    private static final int REQUEST_CODE = 43;
    private static final int CAMERA_REQUEST = 52;
    Boolean img_selected=false;

    ProgressBar progressBar;

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    boolean check;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            Intent i = new Intent(getContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
        }

    }


    public SignUp() {
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        user_Email = view.findViewById(R.id.email_edit);
        user_pass = view.findViewById(R.id.pass_edit);
        user_name = view.findViewById(R.id.username_edit);
        signUpbtn = view.findViewById(R.id.SignUpButton);
        userpic = view.findViewById(R.id.user_img);
        progressBar = view.findViewById(R.id.progress_circular);
        userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryOrCameraDialog();
            }
        });

        db = FirebaseFirestore.getInstance();

        storage = FirebaseStorage.getInstance("gs://travelkutt.appspot.com/");
        storageReference = storage.getReference();


        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginverify();
            }
        });
        check = checkPermissions();
        return view;
    }

    private void loginverify() {
        String useremail = user_Email.getText().toString().trim();
        String pass = user_pass.getText().toString().trim();
        String username = user_name.getText().toString().trim();

        if (username.isEmpty()){
            user_name.requestFocus();
            user_name.setError("Please enter username");
            user_name.setFocusable(true);
            return;
        }

        if (useremail.isEmpty()){
            user_Email.requestFocus();
            user_Email.setError("Please enter email");
            user_Email.setFocusable(true);
            return;
        }

        if (pass.isEmpty()){
            user_pass.requestFocus();
            user_pass.setError("Please enter password");
            user_pass.setFocusable(true);
            return;
        }

        if (!isValidEmail(useremail)){
            user_Email.requestFocus();
            user_Email.setError("Please enter valid email");
            user_Email.setFocusable(true);
            return;
        }

        if (pass.length() < 7){
            user_pass.requestFocus();
            user_pass.setError("Password should be greater than 7 letters");
            user_pass.setFocusable(true);
            return;
        }else {
            Login_With_Firebase(useremail,pass);
        }

    }


    private void Login_With_Firebase(String user_name, String pass_word) {

        mAuth.createUserWithEmailAndPassword(user_name, pass_word)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressDialog = new ProgressDialog(getContext());
                            progressDialog.setTitle("Creating...");
                            progressDialog.show();
                            uploadImage(tempUri);

                        } else {
                            user_Email.requestFocus();
                            user_Email.setError(task.getException().getMessage());
                            user_pass.setFocusable(true);
                            //updateUI(null);
                        }
                    }
                });
    }

    public void galleryOrCameraDialog() {
        //permissionManager.checkAndRequestPermissions(this);
        TextView galleryTv, cameraTv, noImageTv;
        View view = View.inflate(getContext(), R.layout.pic_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .show();

        galleryTv = view.findViewById(R.id.galleryTv);
        cameraTv = view.findViewById(R.id.cameraTv);

        galleryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
                alertDialog.dismiss();
            }
        });

        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraIntent();
                alertDialog.dismiss();
            }
        });
    }

    private void openCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            img_selected = false;
            if (data != null) {
                img_selected = true;
                fileuri = data.getData();
                try {
                   /* Bitmap photo = (Bitmap) data.getExtras().get("data");
                    tempUri = getImageUri(getContext(), photo);*/
                    Glide.with(getContext()).load(fileuri).into(userpic);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileuri);
                    tempUri = getImageUri(getContext(), bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == CAMERA_REQUEST) {
            img_selected = false;
            if (data != null) {
                img_selected = true;
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Glide.with(getContext()).load(photo).into(userpic);
                tempUri = getImageUri(getContext(), photo);
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void openImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void uploadImage(Uri filePath) {
        if (filePath != null) {
            StorageReference ref = storageReference.child("UsersPics").child(filePath.getLastPathSegment());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    /*paths.add(String.valueOf(task.getResult()));
                                    if (paths.size() == uris.size()) {
                                        upload_To_FireStore();
                                    }*/

                                    String uploadedImage_Path = String.valueOf(task.getResult());
                                    upload_To_FireStore(user_Email.getText().toString().trim(),user_name.getText().toString().trim(),uploadedImage_Path);

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }else {
            upload_To_FireStore(user_Email.getText().toString().trim(),user_name.getText().toString().trim(),null);
        }
    }


    private void upload_To_FireStore(String user_email, String user_name, String user_pic) {
        User_item_model item = new User_item_model(user_email,user_name,user_pic);
        FireBase_AddItem(item);
    }

    private void FireBase_AddItem(User_item_model item) {
        db.collection("Users").document().set(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       updateUI(mAuth.getCurrentUser());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }


}
