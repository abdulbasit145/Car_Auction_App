package com.app.car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Registrtion extends AppCompatActivity {

    EditText UserName,Email,Password,ConfirmPassword,Phone;
    Button RegBtn;
    TextView LogBtn;

    public static final String RIDER_USERS = "User";
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);

    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrtion);

        UserName = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        ConfirmPassword = findViewById(R.id.confirmPassword);
        Phone = findViewById(R.id.phoneNo);

        RegBtn = findViewById(R.id.btn_register);
        LogBtn = findViewById(R.id.loginButton);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        LogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registrtion.this,Login.class));
            }
        });

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Authentication();
            }
        });


        ImageButton imageButtonTogglePassword = findViewById(R.id.imageButtonTogglePasswordReg);
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

        ImageButton imageButtonTogglePasswordCheck = findViewById(R.id.imageButtonTogglePasswordCheck);
        imageButtonTogglePasswordCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextPassword = findViewById(R.id.confirmPassword);
                int inputType = editTextPassword.getInputType();

                if (inputType == (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD)) {
                    // Show password
                    editTextPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
                    imageButtonTogglePasswordCheck.setImageResource(R.drawable.ic_password_visibility);
                } else {
                    // Hide password
                    editTextPassword.setInputType((InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD));
                    imageButtonTogglePasswordCheck.setImageResource(R.drawable.ic_password_visibility_off);
                }

                // Move cursor to the end of the text
                editTextPassword.setSelection(editTextPassword.getText().length());
            }
        });
    }
    private void Authentication(){
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String confirmPassword = ConfirmPassword.getText().toString();
        String username = UserName.getText().toString();
        String phoneNumber = Phone.getText().toString();

        if(email.isEmpty()){
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
        } else if (!confirmPassword.equals(password)) {
            Password.setError("Password doesn't matches");
            return;
        }else {
            progressDialog.setMessage("Creating your Account....");
            progressDialog.setTitle("Creating");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();

                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String userId = firebaseUser.getUid();

                        reference = FirebaseDatabase.getInstance().getReference().child(Registrtion.RIDER_USERS).child(userId);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", userId);
                        hashMap.put("username", username);
                        hashMap.put("password", password);
                        hashMap.put("email", email);
                        hashMap.put("phoneNumber", phoneNumber);

                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    sendUserToMainActivity(username);
                                }
                            }
                        });

                        Toast.makeText(Registrtion.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Registrtion.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    private void sendUserToMainActivity(String username) {
        Intent intent = new Intent(Registrtion.this, MainActivity.class);
        intent.putExtra("username",username);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}