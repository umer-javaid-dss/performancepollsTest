package com.softwaresolution.db.userperformancerating.userperfamance_avg;


import com.softwaresolution.db.userperformancerating.BasePresenter;
import com.softwaresolution.db.userperformancerating.BaseView;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;

import java.util.List;

public interface AdminUserPerformanceActivityContract {


    interface View extends BaseView<Presenter> {

        void setUsersData(List<UserModelNew> userModelNewList);

    }

    interface Presenter extends BasePresenter {

        void getUsersData(String userId);
     }



}
