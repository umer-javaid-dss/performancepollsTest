package com.softwaresolution.db.userperformancerating.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.softwaresolution.db.userperformancerating.userperfamance_avg.AdminWorkPerformanceActivity;
import com.softwaresolution.db.userperformancerating.MyApplication;
import com.softwaresolution.db.userperformancerating.R;
import com.softwaresolution.db.userperformancerating.signup.UserSignUpActivity;
import com.softwaresolution.db.userperformancerating.userpermance.WorkPerformanceActivity;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;

public class UserLoginActivity extends AppCompatActivity implements LoginActivityContract.View{


    private  EditText username;

    private  EditText password;

    private TextView login_btn;



    private  LoginActivityPresenter  presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        init();


        presenter=new LoginActivityPresenter(UserLoginActivity.this);

        login_btn.setOnClickListener(
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



                        presenter.performLogin(username.getText().toString(),password.getText().toString().trim());

                    }
                }
        );
    }


    @Override
    public void setLoginAdmin(String message) {

        finish();
        Intent intent = new Intent(UserLoginActivity.this, AdminWorkPerformanceActivity.class);
        startActivity(intent);

    }

    @Override
    public void setLoginUser(String message) {

        finish();
        Intent intent = new Intent(UserLoginActivity.this, WorkPerformanceActivity.class);
        startActivity(intent);

    }

    @Override
    public void setLoginFailed(String message) {


        MyApplication.currentUser=null;

        Toast.makeText(UserLoginActivity.this,"User and Password is not correct",
                Toast.LENGTH_SHORT).show();


    }

    @Override
    public void setPresenter(LoginActivityContract.Presenter presenter) {


    }


    private  void init()
    {

        username = (EditText)findViewById(R.id.editText_user);
        password = (EditText)findViewById(R.id.editText_password);
        login_btn = (TextView) findViewById(R.id.button_login);


    }




    public static UserModelNew getUsersNew (String user_name,String user_pass) {

        try {
            return new Select()
                    .from(UserModelNew.class)
                    .where("user_name = ? AND user_pass = ?", user_name, user_pass)
                    .executeSingle();
        }catch (Exception ex)
        {
            ex.toString();
        }

        return  null;
    }




    public void onSignUpClick(View view) {


        finish();
        Intent  intent=new Intent(UserLoginActivity.this,UserSignUpActivity.class);
        startActivity(intent);

    }


}
