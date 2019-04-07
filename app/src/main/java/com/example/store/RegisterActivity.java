package com.example.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button mRegisterButton;
    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        email = (TextInputEditText) findViewById(R.id.register_email);
        password = (TextInputEditText) findViewById(R.id.register_password);
        confirmPassword = (TextInputEditText) findViewById(R.id.register_confirm_password);
        mRegisterButton = (Button) findViewById(R.id.loginBtn);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


    }

    public void register() {
        final String emailText;
        final String passwordText;
        String confirmPasswordText;

        emailText = email.getText().toString();
        passwordText = password.getText().toString();
        confirmPasswordText = confirmPassword.getText().toString();

        if (emailText.isEmpty() || emailText.equals(" ")) {
            email.setError("Please enter an email");
            return;
        }

        if (passwordText.isEmpty() || passwordText.equals(" ")) {
            password.setError("Please enter the password");
            return;
        }

        if (passwordText.length() < 8) {
            password.setError("Password must be 8 characters");
            return;
        }

        if (confirmPasswordText.isEmpty() || confirmPasswordText.equals(" ")) {
            confirmPassword.setError("The password isn't matching");
            return;
        }

        if (!confirmPasswordText.equals(passwordText)) {
            confirmPassword.setError("The password isn't matching");
            return;
        }


        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("my_store", "createUserWithEmail:success");
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("my_app", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }
}
