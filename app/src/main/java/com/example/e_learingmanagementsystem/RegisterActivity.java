package com.example.e_learingmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_learingmanagementsystem.ui.instructor.DashboardFragment;
import com.example.e_learingmanagementsystem.ui.student.StudentHomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity {

    TextView alreadyHaveAccount;
    EditText editTextFirstName,editTextLastName ,editTextEmail, editTextPassword, editTextConfirmPassword;
    RadioGroup radioGroup;
    Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        alreadyHaveAccount=findViewById(R.id.textViewAlreadyHaveAccount);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        radioGroup = findViewById(R.id.radioGroup);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                String role;
                if(checkedId == -1) {
                    //No radio button are checked
                    Toast.makeText(RegisterActivity.this, "Please select a role", Toast.LENGTH_SHORT).show();
                } else {
                    //one of the radio button is selected
                    switch (checkedId) {
                        case R.id.radBtnStudent:
                            role = "Student";
                            PerforAuth(role);
                            break;
                        case R.id.radBtnInstructor:
                            role = "Instructor";
                            PerforAuth(role);
                            break;
                    }
                }

            }
        });
    }

    private void PerforAuth(String role) {
        String email=editTextEmail.getText().toString();
        String password=editTextPassword.getText().toString();
        String confirmPassword=editTextConfirmPassword.getText().toString();


        if(!email.matches(emailPattern)) {
            editTextEmail.setError("Please enter a valid Email");
        } else if(password.isEmpty() || password.length()<8) {

            editTextPassword.setError("Plaese enter a valid Password");
        } else if(!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Password doesn't match.");
        }else {
            progressDialog.setMessage("Please wait while Registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        progressDialog.dismiss();
                        if(role.equals("Student")) {
                            Intent intent = new Intent(RegisterActivity.this, StudentHomeFragment.class);
                        } else {
                            Intent intent = new Intent(RegisterActivity.this, DashboardFragment.class);
                        }
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}