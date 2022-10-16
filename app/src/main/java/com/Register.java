package com;

import static com.example.androidapp.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    TextView Alreadyhaveanaccount;
    EditText inputEmail,inputPassword,inputconfirmpassword;
    Button btnligin;
    String emailpattern="[A-Za-z9._-]+[a-z]+\\.[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_register);
        Alreadyhaveanaccount=findViewById(R.id.Alreadyhaveanaccount);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputconfirmpassword=findViewById(R.id.inputconfirmpassword);
        btnligin=findViewById(R.id.btnligin);



        Alreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.Register.this ,MainActivity.class));
            }
        });


        btnligin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });
    }

    private void performAuth() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmpassword=inputconfirmpassword.getText().toString();
        progressDialog=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();

        mUser=mAuth.getCurrentUser();


        if(email.matches(emailpattern))
        {
            inputEmail.setError("Enter Correct email");
        } else if (password.isEmpty() || password.length() < 6)
        {
            inputPassword.setError("Enter Proper password");
        }else if(!password.equals(confirmpassword))
        {
            inputconfirmpassword.setError("Please check the password");
        }else
        {
            progressDialog.setMessage("please while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            Task<AuthResult> registration_successfull = mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUsertoNextActivity();
                        Toast.makeText(com.Register.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(com.Register.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }
    }

    private void sendUsertoNextActivity() {
        Intent intent=new Intent(Register.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}