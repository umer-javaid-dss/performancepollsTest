package com.softwaresolution.db.userperformancerating.userpermance;


import com.softwaresolution.db.userperformancerating.BasePresenter;
import com.softwaresolution.db.userperformancerating.BaseView;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;

import java.util.List;

public interface UserPerformanceActivityContract {


    interface View extends BaseView<Presenter> {

        void setUsers(List<UserModelNew> userModelNewList);

    }

    interface Presenter extends BasePresenter {

        void getUsers(String userId);
     }



}
