package com.example.photoflickr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photoflickr.user.database.UserDatabase;
import com.example.photoflickr.user.model.User;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private ImageView btnShowPassword, btnShowConfirmPassword;
    private TextView tvMessage;
    private EditText edtUsername, edtEmail, edtPassword, edtConfirmPassword;
    private AppCompatButton btnSignIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUi();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOpenSignIn();
            }
        });

        btnShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePass();
            }
        });

        btnShowConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideConfirmPass();
            }
        });
    }

    private void onClickSignUp() {
        String strUsername = edtUsername.getText().toString().trim();
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        String strConfirmPassword = edtConfirmPassword.getText().toString().trim();

        User user = new User(strUsername, strEmail, strPassword);

        if (TextUtils.isEmpty(strUsername)
                || TextUtils.isEmpty(strEmail)
                || TextUtils.isEmpty(strPassword)
                || TextUtils.isEmpty(strConfirmPassword)) {
            tvMessage.setText("Invalid information");
            tvMessage.setTextColor(Color.RED);
            return;
        } else if (!user.isValidEmail()) {
            tvMessage.setText("Invalid email");
            tvMessage.setTextColor(Color.RED);
            return;
        } else if (!user.isValidPassword()) {
            tvMessage.setText("Invalid password");
            tvMessage.setTextColor(Color.RED);
            return;
        } else if (strConfirmPassword.length() != strPassword.length()) {
            tvMessage.setText("Password incorrect");
            tvMessage.setTextColor(Color.RED);
            return;
        }

        if (isUserExits(user)) {
            tvMessage.setText("User already exits");
            tvMessage.setTextColor(Color.RED);
            return;
        }

        tvMessage.setVisibility(View.GONE);
        UserDatabase.getInstance(SignUpActivity.this).userDao().insertUser(user);
        Toast.makeText(SignUpActivity.this, "Sign up succesfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
        SignUpActivity.this.finish();
    }

    //Hien mat khau
    private void showHidePass() {
        if (edtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            //Show Password
            edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            btnShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_show_password));
        } else {
            //Hide Password
            edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            btnShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_hide_password));
        }
    }

    //Hien mat khau
    private void showHideConfirmPass() {
        if (edtConfirmPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            //Show Password
            edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            btnShowConfirmPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_show_password));
        } else {
            //Hide Password
            edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            btnShowConfirmPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_hide_password));
        }
    }

    private void onClickOpenSignIn() {
        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
    }

    //Ham check user
    private boolean isUserExits(User user) {
        List<User> list = UserDatabase.getInstance(this).userDao().checkUser(user.getUsername());
        return list != null && !list.isEmpty();
    }

    private void initUi() {
        edtUsername = findViewById(R.id.edt_username_sign_up);
        edtEmail = findViewById(R.id.edt_email_sign_up);
        edtPassword = findViewById(R.id.edt_password_sign_up);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password_sign_up);
        btnShowPassword = findViewById(R.id.btn_show_password_sign_up);
        btnShowConfirmPassword = findViewById(R.id.btn_show_confirm_password);
        btnSignIn = findViewById(R.id.btn_open_sign_in_screen);
        btnSignUp = findViewById(R.id.btn_sign_up);
        tvMessage = findViewById(R.id.tv_message_sign_up);
    }
}