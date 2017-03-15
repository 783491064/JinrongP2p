package com.example.bjc.bjcp2pdemo.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.base.BaseActivity;
import com.example.bjc.bjcp2pdemo.common.AppManager;
import com.example.bjc.bjcp2pdemo.util.BitmapUtils;
import com.example.bjc.bjcp2pdemo.util.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.InjectView;
import butterknife.OnClick;

public class SeettingActivity extends BaseActivity {


    private static final int CRAMA =1 ;
    private static final int PICTURE = 2;
    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_setting)
    ImageButton ibSetting;
    @InjectView(R.id.imageView1)
    ImageView imageView1;
    @InjectView(R.id.textView1)
    TextView textView1;
    @InjectView(R.id.loginout)
    Button loginout;
    private boolean isChangedIcon;

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        ibBack.setVisibility(View.VISIBLE);
        ibSetting.setVisibility(View.GONE);
        tvTitle.setText("用户信息");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_seetting;
    }
    @OnClick({R.id.ib_back, R.id.textView1, R.id.loginout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                //关闭当前的界面
                if(!isChangedIcon){
                    closeCurrentActivity();
                }else{
                    AppManager.getInstance().removeAll();
                    goToActivity(MainActivity.class,null);
                }
                break;
            case R.id.textView1:
                //点击了更换头像
                new AlertDialog.Builder(this)
                        .setTitle("选择头像")
                        .setItems(new String[]{"相机", "图片库"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0://点击了相机
                                        Intent carma=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(carma,CRAMA);
                                        break;
                                    case 1://点击了图片库
                                        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(picture, PICTURE);
                                        break;
                                }
                            }
                        })
                        .show();
                break;
            case R.id.loginout:
                AppManager.getInstance().removeAll();
                //清空用户的登录信息；
                SharedPreferences userinfo = getSharedPreferences("userinfo", MODE_PRIVATE);
                userinfo.edit().clear().commit();
                goToActivity(MainActivity.class,null);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //确认用户修改过头像
        isChangedIcon = true;
        String path=getCacheDir()+"/tx.png";
        if(requestCode==CRAMA&&resultCode==RESULT_OK&&data!=null){//对相机拍照的处理
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            Bitmap zoomBitmap = BitmapUtils.zoomBitmap(bitmap, UIUtils.dp2px(62), UIUtils.dp2px(62));
            Bitmap circleBitmap = BitmapUtils.changeToCircle(zoomBitmap);
            imageView1.setImageBitmap(circleBitmap);
            saveBitmap(circleBitmap);//保存头像   这里需要上传到服务器的这里就不传了；
        }else if(requestCode==PICTURE&&resultCode==RESULT_OK&&data!=null){//对图库的处理
            //图库
            Uri selectedImage = data.getData();
            //这里返回的uri情况就有点多了
            //**:在4.4.2之前返回的uri是:content://media/external/images/media/3951或者file://....在4.4.2返回的是content://com.android.providers.media.documents/document/image:3951或者
            //总结：uri的组成，eg:content://com.example.project:200/folder/subfolder/etc
            //content:--->"scheme"
            //com.example.project:200-->"host":"port"--->"authority"[主机地址+端口(省略) =authority]
            //folder/subfolder/etc-->"path" 路径部分
            //android各个不同的系统版本,对于获取外部存储上的资源，返回的Uri对象都可能各不一样,所以要保证无论是哪个系统版本都能正确获取到图片资源的话
            //就需要针对各种情况进行一个处理了
            String pathResult = getPath(selectedImage);
            if (!TextUtils.isEmpty(path)) {
                Bitmap decodeFile = BitmapFactory.decodeFile(pathResult);
                Bitmap zoomBitmap = BitmapUtils.zoomBitmap(decodeFile, UIUtils.dp2px(62), UIUtils.dp2px(62));
                //bitmap圆形裁剪
                Bitmap circleImage = BitmapUtils.changeToCircle(zoomBitmap);
                try {
                    FileOutputStream fos = new FileOutputStream(path);
                    //bitmap压缩(压缩格式、质量、压缩文件保存的位置)
                    circleImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    //真是项目当中，是需要上传到服务器的..这步我们就不做了。
                    imageView1.setImageBitmap(circleImage);
                    //将图片保存在本地
                    saveBitmap(circleImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存图片持久化到本地
     * @param circleBitmap
     */
    private void saveBitmap(Bitmap circleBitmap) {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                File externalFilesDir = this.getExternalFilesDir(null);
                File file=new File(externalFilesDir,"icon.png");
                circleBitmap.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 根据系统相册选择的文件获取路径
     *
     * @param uri
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        //高于4.4.2的版本
        if (sdkVersion >= 19) {
            Log.e("TAG", "uri auth: " + uri.getAuthority());
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(this, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    /**
     * uri路径查询字段
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMedia(Uri uri) {
        return "media".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
