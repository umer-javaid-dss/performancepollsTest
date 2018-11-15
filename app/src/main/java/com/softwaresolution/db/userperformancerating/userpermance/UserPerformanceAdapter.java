package com.softwaresolution.db.userperformancerating.userpermance;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.softwaresolution.db.userperformancerating.MyApplication;
import com.softwaresolution.db.userperformancerating.R;
import com.softwaresolution.db.userperformancerating.data_models.RatingModel;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;
import com.softwaresolution.db.userperformancerating.data_models.UserRatingModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserPerformanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    List<UserModelNew>  userList=new ArrayList<>();



    public UserPerformanceAdapter(List<UserModelNew> userList) {
        this.userList = userList;
    }


    public class UserHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public CircleImageView picture;
        public  Spinner  spinnerWork1;
        public  Spinner  spinnerWork2;
        public  Spinner  spinnerWork3;

        public UserHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            picture = (CircleImageView) view.findViewById(R.id.picture);

            spinnerWork1 = (Spinner) view.findViewById(R.id.work1);
            spinnerWork2 = (Spinner) view.findViewById(R.id.work2);
            spinnerWork3 = (Spinner) view.findViewById(R.id.work3);
        }
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public View View;


        public HeaderViewHolder(View itemView) {
            super(itemView);
            View = itemView;

        }
    }





    @Override
    public int getItemViewType(int position) {

        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {


        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_row_tem, viewGroup, false);
            return new UserHolder(view);

        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_view_header, viewGroup, false);
            return new HeaderViewHolder(view);

        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");



    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {



        }else if (holder instanceof UserHolder) {

            UserHolder userHolder= (UserHolder) holder;
            UserModelNew userModel = userList.get(position-1);
            userHolder.name.setText(userModel.getName());

            ManageSpinnerWorkRating(userModel,userHolder.spinnerWork1,userHolder.spinnerWork2,userHolder.spinnerWork3);


            if(userModel.getPicture()!=null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(userModel.getPicture(), 0, userModel.getPicture().length);
                ((UserHolder) holder).picture.setImageBitmap(bitmap);
            }
        }




    }

    @Override
    public int getItemCount() {
        return userList.size()+1;
    }







    public void ManageSpinnerWorkRating(final UserModelNew user,final Spinner spinner1,final Spinner spinner2,final Spinner spinner3)
    {


        try
        {

            RatingModel ratingModel=  new Select()
                    .from(RatingModel.class)
                    .join(UserRatingModel.class)
                    .on("RatingModel.id = UserRatingModel.rating_id")
                    .join(UserModelNew.class)
                    .on("UserRatingModel.user_id = UserModelNew.id")
                    .where("UserModelNew.id == ? AND UserRatingModel.form_user ==?",user.getId(),MyApplication.currentUser.getId())
                    .executeSingle();

            if(ratingModel!=null) {

                spinner1.setSelection(ratingModel.getWork1_rating(),false);
                spinner2.setSelection(ratingModel.getWork2_rating(),false);
                spinner3.setSelection(ratingModel.getWork3_rating(),false);
            }



        }catch (Exception ex){
            ex.toString();
        }




        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                RatingModel  ratingModel=  new Select()
                        .from(RatingModel.class)
                        .join(UserRatingModel.class)
                        .on("RatingModel.id = UserRatingModel.rating_id")
                        .join(UserModelNew.class)
                        .on("UserRatingModel.user_id = UserModelNew.id")
                        .where("UserModelNew.id == ? AND UserRatingModel.form_user ==?",user.getId(),MyApplication.currentUser.getId())
                        .executeSingle();


                if(ratingModel==null)
                {

                    RatingModel ratingModel1=new RatingModel();
                    String str= (String) parent.getSelectedItem();
                    ratingModel1.setWork1_rating(Integer.parseInt(str));
                    ratingModel1.save();

                    UserRatingModel userRatingModel=new UserRatingModel();
                    userRatingModel.setRating_id(ratingModel1.getId());
                    userRatingModel.setForm_user(MyApplication.currentUser.getId());
                    userRatingModel.setTimestamp(new Date());
                    userRatingModel.setUser_id(user.getId());

                    userRatingModel.save();

                }
                else
                {

                    String str= (String) parent.getSelectedItem();
                    ratingModel.setWork1_rating(Integer.parseInt(str));
                    ratingModel.save();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                RatingModel  ratingModel=  new Select()
                        .from(RatingModel.class)
                        .join(UserRatingModel.class)
                        .on("RatingModel.id = UserRatingModel.rating_id")
                        .join(UserModelNew.class)
                        .on("UserRatingModel.user_id = UserModelNew.id")
                        .where("UserModelNew.id == ? AND UserRatingModel.form_user ==?",user.getId(),MyApplication.currentUser.getId())
                        .executeSingle();


                if(ratingModel==null)
                {

                    RatingModel ratingModel1=new RatingModel();
                    String str= (String) parent.getSelectedItem();
                    ratingModel1.setWork2_rating(Integer.parseInt(str));
                    ratingModel1.save();

                    UserRatingModel userRatingModel=new UserRatingModel();
                    userRatingModel.setRating_id(ratingModel1.getId());
                    userRatingModel.setForm_user(MyApplication.currentUser.getId());
                    userRatingModel.setTimestamp(new Date());
                    userRatingModel.setUser_id(user.getId());

                    userRatingModel.save();

                }
                else
                {

                    String str= (String) parent.getSelectedItem();
                    ratingModel.setWork2_rating(Integer.parseInt(str));
                    ratingModel.save();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                RatingModel  ratingModel=  new Select()
                        .from(RatingModel.class)
                        .join(UserRatingModel.class)
                        .on("RatingModel.id = UserRatingModel.rating_id")
                        .join(UserModelNew.class)
                        .on("UserRatingModel.user_id = UserModelNew.id")
                        .where("UserModelNew.id == ? AND UserRatingModel.form_user ==?",user.getId(),MyApplication.currentUser.getId())
                        .executeSingle();


                if(ratingModel==null)
                {

                    RatingModel ratingModel1=new RatingModel();
                    String str= (String) parent.getSelectedItem();
                    ratingModel1.setWork3_rating(Integer.parseInt(str));
                    ratingModel1.save();

                    UserRatingModel userRatingModel=new UserRatingModel();
                    userRatingModel.setRating_id(ratingModel1.getId());
                    userRatingModel.setForm_user(MyApplication.currentUser.getId());
                    userRatingModel.setTimestamp(new Date());
                    userRatingModel.setUser_id(user.getId());

                    userRatingModel.save();

                }
                else
                {

                    String str= (String) parent.getSelectedItem();
                    ratingModel.setWork3_rating(Integer.parseInt(str));
                    ratingModel.save();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }







}
