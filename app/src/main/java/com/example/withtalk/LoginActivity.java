package com.example.withtalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.withtalk.model.UserModel;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText loginActivity_edittext_id,loginActivity_edittext_pw;
    private Button login;
    private Button signup;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private EditText email;
    private EditText password;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        String splash_background= mFirebaseRemoteConfig.getString("app_color");
        getWindow().setStatusBarColor(Color.parseColor(splash_background));
        loginActivity_edittext_id = (EditText)findViewById(R.id.loginActivity_edittext_id);
        signup =(Button)findViewById(R.id.loginActivity_button_signup);
        mAuth = FirebaseAuth.getInstance();
        email=(EditText) findViewById(R.id.loginActivity_edittext_id);
        password=(EditText) findViewById(R.id.loginActivity_edittext_pw);
        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;

        //회원가입 버튼 누르면 화면 전환

        login = (Button)findViewById(R.id.loginActivity_button_login);
        login.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String stEmail = email.getText().toString();
            String stPassword = password.getText().toString();
            if(stEmail.isEmpty()){
                Toast.makeText(LoginActivity.this,"이메일을 입력하세요",Toast.LENGTH_LONG).show();
                return;
            }
            if(stPassword.isEmpty()){
                Toast.makeText(LoginActivity.this,"패스워드을 입력하세요",Toast.LENGTH_LONG).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(stEmail, stPassword)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent in = new Intent(LoginActivity.this, Mainpage.class);
                                in.putExtra("email",stEmail);
                                startActivity(in);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "로그인 실패.",
                                        Toast.LENGTH_SHORT).show();
                                    }
                                                                                }
                                });
                                }
        }); //로그인 버튼
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent out = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(out);
            }
        });//회원가입버튼
    }
}

