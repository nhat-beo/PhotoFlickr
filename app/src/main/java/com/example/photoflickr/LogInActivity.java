package com.example.photoflickr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photoflickr.user.database.UserDatabase;
import com.example.photoflickr.user.model.User;

import java.util.List;

public class LogInActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private TextView tvRememberMe, tvMessage;
    private AppCompatButton btnSignIn, btnSignUp;
    private CheckBox cbRemember;
    private ImageView btnShowPassword;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initUi();

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        boolean check = sharedPreferences.getBoolean("remember", false);
        edtUsername.setText(username);
        edtPassword.setText(password);
        cbRemember.setChecked(check);

        tvRememberMe.setPaintFlags(tvRememberMe.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogIn();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOpenSignUp();
            }
        });

        btnShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePass();
            }
        });
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

    private void onClickLogIn() {
        String strUsername = edtUsername.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        String strEmail = "";

        User user = new User(strUsername, strEmail, strPassword);
        if (checkUserExits(user)) {
            tvMessage.setVisibility(View.GONE);
            remember();
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
            Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();
        } else {
            tvMessage.setText("Invalid username or address");
            tvMessage.setTextColor(Color.RED);
        }


    }

    private void onClickOpenSignUp() {
        startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
    }

    //Ham check user
    private boolean checkUserExits(User user) {
        List<User> list = UserDatabase.getInstance(this).userDao().checkExistsUser(user.getUsername(), user.getPassword());
        return list != null && !list.isEmpty();
    }

    //Nho mat khau
    public void remember() {
        if (cbRemember.isChecked()) {
            editor.putString("username", edtUsername.getText().toString());
            editor.putString("password", edtPassword.getText().toString());
            editor.putBoolean("remember", true);
            editor.commit();
        } else {
            editor.putString("username", "");
            editor.putString("password", "");
            editor.putBoolean("remember", false);
            editor.commit();
        }
    }

    //Anh xa
    private void initUi() {
        edtUsername = findViewById(R.id.edt_username_login);
        edtPassword = findViewById(R.id.edt_password_login);
        tvRememberMe = findViewById(R.id.tv_remember_me);
        tvMessage = findViewById(R.id.tv_message_sign_in);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignUp = findViewById(R.id.btn_open_sign_up_screen);
        cbRemember = findViewById(R.id.cb_remember);
        btnShowPassword = findViewById(R.id.btn_show_password);
    }
}