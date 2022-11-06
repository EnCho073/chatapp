package com.example.withtalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.withtalk.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class SignupActivity extends AppCompatActivity {

    private EditText email;
    private EditText name;
    private EditText password;
    private Button signup;
    private String splash_background;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        String splash_background= mFirebaseRemoteConfig.getString("app_color");
        getWindow().setStatusBarColor(Color.parseColor(splash_background));

        email=(EditText) findViewById(R.id.signupActivity_email);
        name=(EditText) findViewById(R.id.signupActivity_name);
        password=(EditText) findViewById(R.id.signupActivity_password);
        signup=(Button) findViewById(R.id.signupActivity_button_signup);
        signup.setBackgroundColor(Color.parseColor(splash_background));
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력값이 null 일 때 진행하지 못함
                if (email.getText().toString() == null || name.getText().toString() == null || password.getText().toString() == null){
                    return;
                }
                String getUseremail = email.getText().toString();
                String getUserpassword = password.getText().toString();
                if(getUseremail.isEmpty()){
                    Toast.makeText(SignupActivity.this,"이메일을 입력하세요",Toast.LENGTH_LONG).show();
                    return;
                }
                if(getUserpassword.isEmpty()){
                    Toast.makeText(SignupActivity.this,"패스워드을 입력하세요",Toast.LENGTH_LONG).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(getUseremail, getUserpassword)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent signupsuccess = new Intent(SignupActivity.this,LoginActivity.class);
                                    startActivity(signupsuccess);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
