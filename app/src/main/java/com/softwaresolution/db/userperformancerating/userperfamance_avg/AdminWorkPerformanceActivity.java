package com.softwaresolution.db.userperformancerating.userperfamance_avg;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.softwaresolution.db.userperformancerating.MyApplication;
import com.softwaresolution.db.userperformancerating.R;
import com.softwaresolution.db.userperformancerating.data_models.UserModelNew;
import com.softwaresolution.db.userperformancerating.login.UserLoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.mayanknagwanshi.imagepicker.imageCompression.ImageCompressionListener;
import in.mayanknagwanshi.imagepicker.imagePicker.ImagePicker;

public class AdminWorkPerformanceActivity extends AppCompatActivity implements  AdminUserPerformanceActivityContract.View{



    private AdminUserPerformanceAdapter adminUserPerformanceAdopter;

    private RecyclerView  recyclerView;

    private CircleImageView profilePicture;

    private TextView userName,userDesig;

    private ImagePicker imagePicker;

    private  AdminUserPerformanceActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_work_performance);


        recyclerView=findViewById(R.id.recycler_view);
        profilePicture=(CircleImageView) findViewById(R.id.profilePicture);
        userName=findViewById(R.id.userName);
        userDesig=findViewById(R.id.userDesig);


        setProfile();

        presenter=new AdminUserPerformanceActivityPresenter(AdminWorkPerformanceActivity.this);

        presenter.getUsersData(MyApplication.currentUser.getId()+"");

        imagePicker = new ImagePicker();

    }


    @Override
    public void setUsersData(List<UserModelNew> userModelNewList) {

        try {

            adminUserPerformanceAdopter = new AdminUserPerformanceAdapter(userModelNewList);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adminUserPerformanceAdopter);
        }catch (Exception ex)
        {
            ex.toString();
        }



    }


    public  void setProfile()
    {

        UserModelNew userModel=MyApplication.currentUser;

        if(userModel.getPicture()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(userModel.getPicture(), 0, userModel.getPicture().length);
            profilePicture.setImageBitmap(bitmap);
        }


        userName.setText(userModel.getName());
        userDesig.setText(userModel.getDesignation());


    }




    private  byte []   bitmap_to_Byte(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();

        return byteArray;
    }




    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void onAddPicture(View view) {



        isReadStoragePermissionGranted();



    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == ImagePicker.SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            //Add compression listener if withCompression is set to true
            imagePicker.addOnCompressListener(new ImageCompressionListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onCompressed(String filePath) {//filePath of the compressed image
                    //convert to bitmap easily
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);

                    if(MyApplication.currentUser!=null) {
                        MyApplication.currentUser.setPicture(bitmap_to_Byte(selectedImage));
                        MyApplication.currentUser.save();
                        setProfile();
                    }

                }
            });
        }
        //call the method 'getImageFilePath(Intent data)' even if compression is set to false
        String filePath = imagePicker.getImageFilePath(data);
        if (filePath != null) {//filePath will return null if compression is set to true

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public  void  startPickingImage()
    {
        try {
            imagePicker.withActivity(this) //calling from activity
                    //.withFragment(W) //calling from fragment
                    .chooseFromGallery(false) //default is true
                    //.chooseFromCamera(false) //default is true
                    .withCompression(true) //default is true

                    .start();
        }catch (Exception ex)
        {
            ex.toString();
        }
    }




    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                isWriteStoragePermissionGranted();

                return true;
            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation


            startPickingImage();

            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                startPickingImage();

                return true;
            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation

            startPickingImage();

            return true;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:

                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){


                }else{


                }
                break;

            case 3:


                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){


                }else{

                }
                break;
        }
    }




    @Override
    public void onBackPressed() {
        //super.onBackPressed();


        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                        Intent  intent=new Intent(AdminWorkPerformanceActivity.this,UserLoginActivity.class);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();




    }


    public  void onSignOutClick(View view)
    {
        onBackPressed();
    }



    @Override
    public void setPresenter(AdminUserPerformanceActivityContract.Presenter presenter) {

    }
}
