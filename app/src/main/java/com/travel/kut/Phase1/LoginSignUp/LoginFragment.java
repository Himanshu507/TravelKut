package com.travel.kut.Phase1.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.travel.kut.MainActivity;
import com.travel.kut.R;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    EditText email, password;
    private FirebaseAuth mAuth;
    static final int GOOGLE_SIGN = 123;
    GoogleSignInClient googleSignInClient;
    List<DocumentSnapshot> document_id = new ArrayList<DocumentSnapshot>();
    DocumentReference documentReference;
    CollectionReference collectionReference;
    FirebaseFirestore db;
    User_Model user_model;
    ImageView google, facebook ;
    MaterialButton loginbutn;
    TextView forget_pass;

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



    public LoginFragment() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.email_edit);
        password = view.findViewById(R.id.pass_edit);
        loginbutn = view.findViewById(R.id.loginButton);
        loginbutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginverify();
            }
        });
        google = view.findViewById(R.id.google_login);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInGoogle();
            }
        });
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Users");
        user_model = new User_Model(document_id);
        forget_pass = view.findViewById(R.id.forget_pass);
        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),ForgetPasswordActivity.class);
                startActivity(i);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(getContext(),gso);


        return view;
    }

    private void loginverify() {
        String useremail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (useremail.isEmpty()){
            email.requestFocus();
            email.setError("Please enter email");
            email.setFocusable(true);
            return;
        }

        if (pass.isEmpty()){
            password.requestFocus();
            password.setError("Please enter password");
            password.setFocusable(true);
            return;
        }

        if (!isValidEmail(useremail)){
            email.requestFocus();
            email.setError("Please enter valid email");
            email.setFocusable(true);
            return;
        }

        if (pass.length() < 7){
            password.requestFocus();
            password.setError("Password should be greater than 7 letters");
            password.setFocusable(true);
            return;
        }else {
            Login_With_Firebase(useremail,pass);
        }

    }


    private void Login_With_Firebase(String user_name, String pass_word) {

        mAuth.signInWithEmailAndPassword(user_name, pass_word)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            password.requestFocus();
                            password.setError(task.getException().getLocalizedMessage());
                            password.setFocusable(true);
                            updateUI(null);
                        }
                    }
                });
    }


    public void SignInGoogle() {
        Intent i = googleSignInClient.getSignInIntent();
        startActivityForResult(i, GOOGLE_SIGN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthwithGoogle(account);
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void firebaseAuthwithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser user = mAuth.getCurrentUser();
                    documentReference = collectionReference.document(user.getUid());
                    documentReference.set(user_model)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    updateUI(user);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
