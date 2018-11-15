package com.softwaresolution.db.userperformancerating.userperfamance_avg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.activeandroid.query.Select;
import com.softwaresolution.db.userperformancerating.R;
import com.softwaresolution.db.userperformancerating.data_models.RatingModel;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;
import com.softwaresolution.db.userperformancerating.data_models.UserRatingModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminUserPerformanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    List<UserModelNew>  userList=new ArrayList<>();

    HashMap<String,List<RatingModel>>   resultData=new HashMap();



    public AdminUserPerformanceAdapter(List<UserModelNew> userList) {

        this.userList = userList;


        loadRatingData();

    }


    public class UserHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public CircleImageView picture;

        public  TextView  work1,work2,work3;




        public UserHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            picture = (CircleImageView) view.findViewById(R.id.picture);

            work1 = (TextView) view.findViewById(R.id.workTV1);
            work2 = (TextView) view.findViewById(R.id.workTV2);
            work3 = (TextView) view.findViewById(R.id.workTV3);

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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_user_row_tem, viewGroup, false);
            return new UserHolder(view);

        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_view_admin_header, viewGroup, false);
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

            ManageTVWorkAVG(userModel,userHolder.work1,userHolder.work2,userHolder.work3);

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


    public void ManageTVWorkAVG(final UserModelNew user, final TextView work1,final TextView work2,final TextView work3)
    {

        try {

            double work1rating=0.00;
            double work2rating=0.00;
            double work3rating=0.00;

           List<RatingModel>  userRatings =resultData.get(""+user.getId());

           if(userRatings!=null && userRatings.size() > 0) {
               for (int i = 0; i < userRatings.size(); i++) {

                   work1rating += userRatings.get(i).getWork1_rating();
                   work2rating += userRatings.get(i).getWork2_rating();
                   work3rating += userRatings.get(i).getWork3_rating();

               }


               work1rating = work1rating / userRatings.size();
               work2rating = work2rating / userRatings.size();
               work3rating = work3rating / userRatings.size();
           }


            DecimalFormat f = new DecimalFormat("0.00");

            work1.setText(""+f.format(work1rating));
            work2.setText(""+f.format(work2rating));
            work3.setText(""+f.format(work3rating));

        }catch (Exception ex)
        {
            ex.toString();
        }

    }



    public  void loadRatingData()
    {

        try {

            for(int i=0; i<userList.size(); i++)
            {
                List<RatingModel> userRating = new Select().all()

                        .from(RatingModel.class)
                        .join(UserRatingModel.class)
                        .on("RatingModel.id = UserRatingModel.rating_id")
                        .join(UserModelNew.class)
                        .on("UserRatingModel.user_id = UserModelNew.id")
                        .where("UserModelNew.id == ?",userList.get(i).getId())
                        .execute();
                        resultData.put(userList.get(i).getId()+"",userRating);

            }
            int test=0;
            Log.i("","");
        }catch (Exception ex)
        {
            ex.toString();
        }


    }







}
