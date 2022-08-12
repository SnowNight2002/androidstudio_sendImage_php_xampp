package com.example.androidsendpc;


import static android.util.Base64.DEFAULT;
import static android.util.Base64.encodeToString;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView predict;
    ImageView img_photo;
    private Button client_submit;
    final int TAKE_PHOTO = 1;
    Uri imageUri;
    File outputImage;

    private static final int UPDATE_ok = 0;
    private static final int UPDATE_UI = 1;
    private static final int ERROR = 2;
    // 测试路径(默认值)

    static String testPath = "/data/user/0/com.example.androidsendpc/files/images/thegtgname.jpeg";
    //Environment.getExternalStorageDirectory().getAbsolutePath()
    //+ "/test.jpg";///storage/sdcard/Download/android-env.jpg

    Button btn_send;// "上传"按钮
    ImageView iv; // 图片框

    static String baseUrl = "http://10.39.180.153/demo/";
    static String infoUrl = baseUrl + "uploadfile_get.php";
    static String upUrl = baseUrl + "uploadfile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvent();
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFile();
            }
        });
        iv = (ImageView) findViewById(R.id.iv);
    }

    //初始化控件
    private void initViews() {
        img_photo = findViewById(R.id.img_photo);
        predict = findViewById(R.id.predict);
        client_submit = findViewById(R.id.client_submit);
    }

    private void initEvent() {

        int[] name = new int[500];

        client_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i <= name[i]; i++){
                    String filename = "Pic" + name[i]; //自定义的照片名称

                    File outputImage = new File(getExternalCacheDir(), filename);  //拍照后照片存储路径
                    try {
                        if (outputImage.exists()) {
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= 24) {
                        //图片的url
                        imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.android.androidsendpc", outputImage);
                    } else {
                        imageUri = Uri.fromFile(outputImage);
                    }
                    //跳转界面到系统自带的拍照界面
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");  //调用手机拍照功能其实就是启动一个activity
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);  //指定图片存放位置，指定后，在onActivityResult里得到的Data将为null
                    startActivityForResult(intent, TAKE_PHOTO);  //开启相机
                }
            }
        });
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 使用try让程序运行在内报错
                    try {
                        //将图片显示
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        img_photo.setImageBitmap(bitmap);  //imageview控件显示刚拍的图片
                        saveBitmap("thegtgname.jpeg", bitmap, getApplicationContext() );

                        //upload(upUrl, new File(imageUri.getPath()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * Save Bitmap
     *
     * @param name file name
     * @param bm  picture to save
     */
    static void saveBitmap(String name, Bitmap bm, Context mContext) {
        Log.d("Save Bitmap", "Ready to save picture");
        //指定我们想要存储文件的地址
        String TargetPath = mContext.getFilesDir() + "/images/";
        Log.d("Save Bitmap", "Save Path=" + TargetPath);
        //判断指定文件夹的路径是否存在
        if (!fileIsExist(TargetPath)) {
            Log.d("Save Bitmap", "TargetPath isn't exist");
        } else {
            //如果指定文件夹创建成功，那么我们则需要进行图片存储操作
            File saveFile = new File(TargetPath, name);

            try {
                FileOutputStream saveImgOut = new FileOutputStream(saveFile);
                // compress - 压缩的意思
                bm.compress(Bitmap.CompressFormat.JPEG, 80, saveImgOut);
                //存储完成后需要清除相关的进程
                saveImgOut.flush();
                saveImgOut.close();
                Log.d("Save Bitmap", "The picture is save to your phone!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
    static boolean fileIsExist(String fileName)
    {
        //传入指定的路径，然后判断路径是否存在
        File file=new File(fileName);
        if (file.exists())
            return true;
        else{
            //file.mkdirs() 创建文件夹的意思
            return file.mkdirs();
        }
    }




    // 发送文件
    private void sendFile(){

        // 接口地址
        String url = upUrl;
        String filepath = testPath;

        File file = new File(filepath);
        if (file.exists()) {
            //showPic(testPath);
            upload(url, file);
        }else{
            show("你给的文件路径不存在");
        }
    }

    /*
     * 完成上传文件
     * @param url 上传接口的网址
     * @param uploadfile 要上传文件对象
     */
    private void upload(String url, File uploadfile) {
        RequestParams params = new RequestParams();
        // 这里必须按照文档写
        params.addBodyParameter("uploadfile", uploadfile);
        btn_send.setEnabled(false);
        // 发送请求

        new HttpUtils().send(
                HttpRequest.HttpMethod.POST,// 上传必须是POST
                url,
                params,

                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> info) {

                        String result = info.result.trim();// 上传文件的数据库ID
                        show("上传后的结果:"+result);

                        result = result.substring(result.indexOf("{")+1);// 从{开始截取,包含{
                        if (result.equals("0")) {
                            show("上传失败");
                        } else {
                            show("上传成功,新ID是:" + result);
                        }
                        btn_send.setEnabled(true);
                        // 获取服务器信息,展示上传的图片
                       // getUploadedInfo(infoUrl,result);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        show("网络失败:" + s);
                        btn_send.setEnabled(true);
                    }
                }
        );
    }

    public void show(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.i("spl",msg);
    }
    /*
     * 获取上传文件的信息
     * @param url 接口的网址
     * @param fid ID
     */
    /*private void getUploadedInfo(String url, String fid) {
        int ifid = Integer.parseInt(fid.trim());
        url = infoUrl + "?fid="+ifid;
        show("url="+url);
        // 发送请求
        new HttpUtils().send(
                HttpRequest.HttpMethod.GET,
                url,

                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> info) {

                        String result = info.result.trim();
                        show("刚获取的:"+result);
                        result = result.substring(result.indexOf("{"));// 从{开始截取,包含{
                        show("截取后的:"+result);
                        Uploadfile uploadfile = JSON.parseObject(result, Uploadfile.class);

                        String picUrl = baseUrl+uploadfile.getUploadfile();
                        if (uploadfile == null) {
                            show("失败");
                        } else {
                            show("成功,图片地址是:" + picUrl);
                        }

                        showPic(picUrl);

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        show("网络失败:" + s);

                    }
                }
        );
    }

    private void showPic(String picUrl) {
        show("图片地址:"+picUrl);
        new BitmapUtils(this).display(iv,picUrl);
    }*/
}
