package com.example.xed.houser.accounts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xed.houser.MainActivity;
import com.example.xed.houser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailtext;
    private EditText passwordtext;
    private Button loginbtn;
    private TextView signupText;
    private FirebaseAuth mAuth;
    private ProgressBar mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailtext = (EditText) findViewById(R.id.login_email);
        passwordtext = (EditText) findViewById(R.id.login_password);
        loginbtn= (Button) findViewById(R.id.login_btn);
        signupText = (TextView) findViewById(R.id.signup_text);
        mAuth = FirebaseAuth.getInstance();
        mprogress = findViewById(R.id.loginprogressBar);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_val = emailtext.getText().toString();
                String password_val = passwordtext.getText().toString();

                if(!TextUtils.isEmpty(email_val)&&!TextUtils.isEmpty(password_val)){

                    mprogress.setVisibility(View.VISIBLE);
                    logUserIn(email_val, password_val);

                }else{
                    toast("Missing Fields");
                }
            }
        });

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupintent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signupintent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            movetomain();
        }
    }


    public void logUserIn(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mprogress.setVisibility(View.INVISIBLE);
                            movetomain();
                        } else {
                            // If sign in fails, display a message to the user.
                            mprogress.setVisibility(View.VISIBLE);
                            String error= task.getException().getMessage();
                            toast("Authentication failed "+error);
                        }

                    }
                });
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void movetomain(){
        Intent mainintent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainintent);
        finish();
    }


}
