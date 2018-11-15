package com.softwaresolution.db.userperformancerating.userpermance;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.activeandroid.query.Select;
import com.softwaresolution.db.userperformancerating.MyApplication;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;
import com.softwaresolution.db.userperformancerating.login.LoginActivityContract;

import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;

public class UserPerformanceActivityPresenter implements UserPerformanceActivityContract.Presenter{



    @NonNull
    private final UserPerformanceActivityContract.View mUserPerformanceView;


    @SuppressLint("RestrictedApi")
    UserPerformanceActivityPresenter(@NonNull UserPerformanceActivityContract.View userPerformanceView)
    {
        mUserPerformanceView = checkNotNull(userPerformanceView);

    }


    @Override
    public void start() {



    }

    @Override
    public void getUsers(String userId) {

        mUserPerformanceView.setUsers(getUsersData(userId));

    }


    public List<UserModelNew> getUsersData(String userId)
    {

        try {
            return new Select()

                    .all()
                    .from(UserModelNew.class)
                    .where("UserModelNew.id != ? AND UserModelNew.designation != ?",userId,"admin")
                    .execute();
        }catch (Exception ex)
        {
            ex.toString();
        }

        return null;
    }

}
