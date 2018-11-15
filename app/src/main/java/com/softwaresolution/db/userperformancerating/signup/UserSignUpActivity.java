package com.softwaresolution.db.userperformancerating.signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softwaresolution.db.userperformancerating.userperfamance_avg.AdminWorkPerformanceActivity;
import com.softwaresolution.db.userperformancerating.MyApplication;
import com.softwaresolution.db.userperformancerating.R;
import com.softwaresolution.db.userperformancerating.userpermance.WorkPerformanceActivity;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;
import com.softwaresolution.db.userperformancerating.login.UserLoginActivity;

import static com.softwaresolution.db.userperformancerating.login.UserLoginActivity.getUsersNew;

public class UserSignUpActivity extends AppCompatActivity implements SignupActivityContract.View{


    private  EditText username;
    private  EditText password;
    private  EditText designation;


    private TextView sign_up_btn;


    private  SignUpActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        init();

        presenter=new SignUpActivityPresenter(UserSignUpActivity.this);

        sign_up_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(username.getText().toString().trim().length()==0){
                            username.setError("Username is not entered");
                            username.requestFocus();
                            return;
                        }
                        if(password.getText().toString().trim().length()==0){
                            password.setError("Password is not entered");
                            password.requestFocus();
                            return;
                        }

                        if(designation.getText().toString().trim().length()==0){
                            designation.setError("Designation is not entered");
                            designation.requestFocus();
                            return;
                        }

                        presenter.performSignUp(username.getText().toString(),
                                                password.getText().toString(),
                                                designation.getText().toString());



                    }
                }
        );
    }


    private  void init()
    {

        username = (EditText)findViewById(R.id.editText_user);
        password = (EditText)findViewById(R.id.editText_password);
        designation=(EditText)findViewById(R.id.editText_designation);

        sign_up_btn = (TextView) findViewById(R.id.button_signup);



    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

        Intent  intent=new Intent(UserSignUpActivity.this,UserLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void setSignUpAdmin(String message) {

        finish();
        Intent intent = new Intent(UserSignUpActivity.this, AdminWorkPerformanceActivity.class);
        startActivity(intent);
    }

    @Override
    public void setSignUpUser(String message) {

        finish();
        Intent intent = new Intent(UserSignUpActivity.this, WorkPerformanceActivity.class);
        startActivity(intent);

    }

    @Override
    public void setSignUpFailed(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(SignupActivityContract.Presenter presenter) {

    }
}
