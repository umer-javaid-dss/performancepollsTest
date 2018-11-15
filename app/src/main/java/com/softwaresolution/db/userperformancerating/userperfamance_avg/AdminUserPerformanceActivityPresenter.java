package com.softwaresolution.db.userperformancerating.userperfamance_avg;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.activeandroid.query.Select;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;
import com.softwaresolution.db.userperformancerating.userpermance.UserPerformanceActivityContract;

import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;

public class AdminUserPerformanceActivityPresenter implements AdminUserPerformanceActivityContract.Presenter{



    @NonNull
    private final AdminUserPerformanceActivityContract.View mAdminUserPerformanceView;


    @SuppressLint("RestrictedApi")
    AdminUserPerformanceActivityPresenter(@NonNull AdminUserPerformanceActivityContract.View adminUserPerformanceView)
    {
        mAdminUserPerformanceView = checkNotNull(adminUserPerformanceView);

    }


    @Override
    public void start() {



    }

    @Override
    public void getUsersData(String userId) {


        mAdminUserPerformanceView.setUsersData(getUsers(userId));
    }




    public List<UserModelNew> getUsers(String userId)
    {

        try {
            return new Select()

                    .all()
                    .from(UserModelNew.class)
                    .where("UserModelNew.id != ?",userId)
                    .execute();
        }catch (Exception ex)
        {
            ex.toString();
        }

        return null;
    }

}
