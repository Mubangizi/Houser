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

public class SignupActivity extends AppCompatActivity {

    private Button signupbtn;
    private TextView logintext;
    private EditText email;
    private EditText userpassword;
    private EditText confpassword;
    private FirebaseAuth mAuth;
    private ProgressBar mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText) findViewById(R.id.signup_email);
        userpassword = (EditText) findViewById(R.id.signup_password);
        confpassword = (EditText) findViewById(R.id.signup_confirmpass);
        signupbtn = (Button) findViewById(R.id.signup_btn);
        mAuth = FirebaseAuth.getInstance();
        logintext = (TextView) findViewById(R.id.login_text);
        mprogress = findViewById(R.id.signupprogressBar);


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_val = email.getText().toString();
                String password_val = userpassword.getText().toString();
                String confpass_val = confpassword.getText().toString();

                if(!TextUtils.isEmpty(email_val)&&!TextUtils.isEmpty(password_val)&&!TextUtils.isEmpty(email_val)){
                    if(password_val.equals(confpass_val)){
                        mprogress.setVisibility(View.VISIBLE);
                        signuserup(email_val, password_val);
                    }else {
                        toast("Passwords are not the same");
                    }
                }else{
                    toast("There are missing Fields");
                }
            }
        });

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginintent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(loginintent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        if(currentuser!=null){
            Intent mainIntent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }

    private void signuserup(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mprogress.setVisibility(View.INVISIBLE);
                            Intent mainIntent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }else{
                            mprogress.setVisibility(View.INVISIBLE);
                            String error= task.getException().getMessage();
                            toast(error);
                        }
                    }
                });

    }



    public void toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
