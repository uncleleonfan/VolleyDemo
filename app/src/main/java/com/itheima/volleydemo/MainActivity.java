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
        String url  = "https://ws1.sinaimg.cn/large/610dc034ly1fj3w0emfcbj20u011iabm.jpg";
        mNetworkImageView.setImageUrl(url, NetworkManager.getInstance().getImageLoader());
    }

    //发送一个StringRequest
    public void onStartStringRequest(View view) {
        String url = "http://gank.io/api/data/Android/10/1";
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

    //发送一个JsonObjectRequest
    public void onStartJsonObjectRequest(View view) {
        String url = "http://gank.io/api/data/Android/10/1";
        //Get请求第二个参数传null
        //Post请求第二个参数传JsonObject对象
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, mJSONObjectListener, mErrorListener);
//        Volley.newRequestQueue(this).add(jsonObjectRequest);
        NetworkManager.getInstance().sendRequest(jsonObjectRequest);
    }

    //获取一个json对象
    private Response.Listener<JSONObject> mJSONObjectListener = new Response.Listener<JSONObject>() {
        /**
         *
         * {"picture":["image/home01.jpg","image/home02.jpg","image/home03.jpg","image/home04.jpg","image/home05.jpg","image/home06.jpg","image/home07.jpg","image/home08.jpg"]}
         */
        @Override
        public void onResponse(JSONObject response) {
            try {
                //获取网络响应中results数组中第一个元素的"desc"字段
                String desc = response.getJSONArray("results").getJSONObject(0).getString("desc");
                Toast.makeText(MainActivity.this, desc, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    //发送JsonArrayRequest请求获取一个json数组
    public void onStartJsonArrayRequest(View view) {
        String url = "https://api.github.com/users/octocat/repos";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, mJSONArrayListener, mErrorListener);
//        Volley.newRequestQueue(this).add(jsonArrayRequest);
        NetworkManager.getInstance().sendRequest(jsonArrayRequest);
    }

    private Response.Listener<JSONArray> mJSONArrayListener = new Response.Listener<JSONArray>() {

        @Override
        public void onResponse(JSONArray response) {
            try {
                //获取数组中第一个元素的"name"字段
                String name = response.getJSONObject(0).getString("name");
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    //使用自定义请求获取网络结果解析成一个Bean对象
    public void onStartGsonRequest(View view) {
        String url = "http://gank.io/api/data/Android/10/1";
        GsonRequest<GankBean> request = new GsonRequest<GankBean>(url, GankBean.class, mGankBeanListener, mErrorListener);
//        Volley.newRequestQueue(MainActivity.this).add(request);
        NetworkManager.getInstance().sendRequest(request);
    }

    private Response.Listener<GankBean> mGankBeanListener = new Response.Listener<GankBean>() {
        @Override
        public void onResponse(GankBean response) {
            //获取网络响应中results数组中第一个元素的"desc"字段
            String desc = response.getResults().get(0).getDesc();
            Toast.makeText(MainActivity.this, desc, Toast.LENGTH_SHORT).show();
        }
    };

    public void onStartImageRequest(View view) {
        String url  = "https://ws1.sinaimg.cn/large/610dc034ly1fj3w0emfcbj20u011iabm.jpg";
        //第三第四个参数分别用于指定允许图片最大的宽度和高度，如果指定的网络图片的宽度或高度大于这里的最大值，则会对图片进行压缩，
        // 指定成0的话就表示不管图片有多大，都不会进行压缩。
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
