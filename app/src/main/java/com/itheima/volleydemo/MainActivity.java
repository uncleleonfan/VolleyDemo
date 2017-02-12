package com.itheima.volleydemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    private NetworkImageView mNetworkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.image_view);

        mNetworkImageView = (NetworkImageView) findViewById(R.id.network_image_view);
        mNetworkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        String url  = "http://10.0.2.2:8080/GooglePlayServer/image?name=image/home01.jpg";
    }

    public void onStartStringRequest(View view) {
        String url = "http://10.0.2.2:8080/GooglePlayServer/home?index=0";
        StringRequest stringRequest = new StringRequest(url, mStringListener, mErrorListener);
//        Volley.newRequestQueue(this).add(stringRequest);
        NetworkManager.getInstance().sendRequest(stringRequest);
    }

    private Response.Listener<String> mStringListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
        }
    };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    public void onStartJsonObjectRequest(View view) {
        String url = "http://10.0.2.2:8080/GooglePlayServer/home?index=0";
        //Get请求第二个参数传null
        //Post请求第二个参数传JsonObject对象
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, mJSONObjectListener, mErrorListener);
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    
    private Response.Listener<JSONObject> mJSONObjectListener = new Response.Listener<JSONObject>() {
        /**
         *
         * {"picture":["image/home01.jpg","image/home02.jpg","image/home03.jpg","image/home04.jpg","image/home05.jpg","image/home06.jpg","image/home07.jpg","image/home08.jpg"]}
         */
        @Override
        public void onResponse(JSONObject response) {
            try {
                //image/home01.jpg
                Toast.makeText(MainActivity.this, response.getJSONArray("picture").getString(0), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void onStartJsonArrayRequest(View view) {
        String url = "http://10.0.2.2:8080/GooglePlayServer/app?index=0";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, mJSONArrayListener, mErrorListener);
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private Response.Listener<JSONArray> mJSONArrayListener = new Response.Listener<JSONArray>() {
        /**
         * [{"id":1580615,"name":"人人","packageName":"com.renren.mobile.android","iconUrl":"app/com.renren.mobile.android/icon.jpg","stars":2,"size":21803987,"downloadUrl":"app/com.renren.mobile.android/com.renren.mobile.android.apk","des":"2005-2014你的校园一直在这儿。中国最大的实名制SNS网络平台，大学生"}]
         */
        @Override
        public void onResponse(JSONArray response) {
            try {
                //1580615
                Toast.makeText(MainActivity.this, response.getJSONObject(0).getString("id"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void onStartGsonRequest(View view) {
        String url = "http://10.0.2.2:8080/GooglePlayServer/home?index=0";
        GsonRequest<HomeBean> request = new GsonRequest<HomeBean>(url, HomeBean.class, mHomeBeanListener, mErrorListener);
        Volley.newRequestQueue(MainActivity.this).add(request);
    }

    private Response.Listener<HomeBean> mHomeBeanListener = new Response.Listener<HomeBean>() {
        @Override
        public void onResponse(HomeBean response) {
            Toast.makeText(MainActivity.this, response.getPicture().get(0), Toast.LENGTH_SHORT).show();
        }
    };

    public void onStartImageRequest(View view) {
        String url  = "http://10.0.2.2:8080/GooglePlayServer/image?name=image/home01.jpg";
        //第三第四个参数分别用于指定允许图片最大的宽度和高度，如果指定的网络图片的宽度或高度大于这里的最大值，则会对图片进行压缩，
        // 指定成0的话就表示不管图片有多大，都不会进行压缩。第三第四个参数分别用于指定允许图片最大的宽度和高度，如果指定的网络图片
        // 的宽度或高度大于这里的最大值，则会对图片进行压缩，指定成0的话就表示不管图片有多大，都不会进行压缩。
        ImageRequest request = new ImageRequest(url, mBitmapListener, 0, 0, Bitmap.Config.RGB_565, mErrorListener);
        Volley.newRequestQueue(this).add(request);
    }

    private Response.Listener<Bitmap> mBitmapListener = new Response.Listener<Bitmap>() {
        @Override
        public void onResponse(Bitmap response) {
            mImageView.setImageBitmap(response);
        }
    };
}
