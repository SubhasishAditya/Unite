package com.aditya.unite1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
     FirebaseAuth auth;
    EditText emailBox,passwordBox,nameBox;
    Button loginBtn,signupBtn;
    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();


        emailBox=findViewById(R.id.emailBox);
        passwordBox=findViewById(R.id.passwordBox);
        loginBtn=findViewById(R.id.loginBtn);
        signupBtn=findViewById(R.id.createBtn);
        nameBox=findViewById(R.id.nameBox);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password,name;
                email=emailBox.getText().toString();
                password=passwordBox.getText().toString();
                name=nameBox.getText().toString();

                User user =new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setName(name);

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("Users")
                                    .document().set(user ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                }
                            });
                            Toast.makeText(SignupActivity.this,"Account created Successfully.",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        loginBtn.setOnClickListener((view)-> {

            startActivity(new Intent(SignupActivity.this,LoginActivity.class));

        });


    }
}