package com.app.car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    EditText Email,Password,UserName;
    Button LogBtn;
    TextView RegBtn;
    TextView forgotPasswordButton;

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
    Pattern pat = Pattern.compile(emailRegex);

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        UserName = findViewById(R.id.username);
        LogBtn = findViewById(R.id.login);
        RegBtn = findViewById(R.id.registerButton);

        forgotPasswordButton = findViewById(R.id.forgetPassword);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();

                if (email.isEmpty()) {
                    Email.setError("Please enter your email");
                    return;
                }

                progressDialog.setMessage("Sending password reset email...");
                progressDialog.show();

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        LogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validating();
            }
        });

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registrtion.class));
            }
        });

        ImageButton imageButtonTogglePassword = findViewById(R.id.imageButtonTogglePassword);
        imageButtonTogglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextPassword = findViewById(R.id.password);
                int inputType = editTextPassword.getInputType();

                if (inputType == (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD)) {
                    // Show password
                    editTextPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
                    imageButtonTogglePassword.setImageResource(R.drawable.ic_password_visibility);
                } else {
                    // Hide password
                    editTextPassword.setInputType((InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD));
                    imageButtonTogglePassword.setImageResource(R.drawable.ic_password_visibility_off);
                }

                // Move cursor to the end of the text
                editTextPassword.setSelection(editTextPassword.getText().length());
            }
        });
    }
    private void Validating(){
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String username = UserName.getText().toString();

        if (email.isEmpty()) {
            Email.setError("Please Enter Email");
            return;
        } else if (!pat.matcher(email).matches()) {
            Email.setError("Please Enter a valid Email");
            return;
        } else if (password.isEmpty()) {
            Password.setError("Please input Password");
            return;
        } else if (password.length() < 6) {
            Password.setError("Password too short");
            return;
        } else {
            progressDialog.setMessage("Login in to your Account....");
            progressDialog.setTitle("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToMainActivity(username);
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    private void sendUserToMainActivity(String username) {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}