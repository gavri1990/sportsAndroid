package com.example.sports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity
{
    EditText editText1, editText2; //ορισμός μεταβλητών class EditText
    private FirebaseAuth mAuth; //μεταβλητή για σύνδεση με Authentication

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText1 = findViewById(R.id.editText1); //σύνδεση με xml μέσω id
        editText2 = findViewById(R.id.editText2);
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(View view)
    {
        String email = editText1.getText().toString(); //διάβασμα editTexts σε μεταβλητές String
        String password = editText2.getText().toString();
        if(email.equals("") || password.equals(""))//επειδή αν πατηθεί login με κενή 1 από τις 2, κρασάρει η εφαρμογή
        {
            Toast.makeText(this, "Both email and password are required!", Toast.LENGTH_LONG).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful()) //μήνυμα Toast σε επιτυχές Login
                            {
                                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this, Menu.class); //Intent για άνοιγμα Menu Activity
                                startActivity(intent);
                            }
                            else //μήνυμα Toast σε ανεπιτυχές Login
                            {
                                Toast.makeText(getApplicationContext(), "Wrong username and/or password", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

}
