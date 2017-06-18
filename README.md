# Volley介绍 #
Volley 是 Goole I/O 2013上发布的网络通信库，使网络通信更快、更简单、更健壮。
适用于数据不大但通信频繁的场景，不适合大文件下载。

# Volley功能 #
* Json，图像等异步下载
* 网络请求的优先级处理
* 缓存
* 取消请求

# Volley工作流程 #
![icon](img/volley-request.png)

1. 线程管理
2. 缓存的管理
3. 发送网络请求过程
	1. 在主线程把请求加入请求队列
	2. 缓存线程查询请求是否有缓存，如果有缓存，则从缓存中获取数据解析返回给主线程，如果没有缓存，把请求分发给网络线程
	3. 网络线程发送请求，从服务器获取数据，解析后返回给主线程



# Volley的源码分析 #

# Volley使用 #
* [Github](https://github.com/mcxiaoke/android-volley)
* [基本用法](http://blog.csdn.net/guolin_blog/article/details/17482095)
* [加载图片](http://blog.csdn.net/guolin_blog/article/details/17482165)
* [自定义请求](http://blog.csdn.net/guolin_blog/article/details/17612763)

## 1. 添加网络权限 ##
    <uses-permission android:name="android.permission.INTERNET"/>
## 2. 添加volley依赖 ##
    compile 'com.android.volley:volley:1.0.0'
## 3. 字符串请求 ##
    public void onStartStringRequest(View view) {
        String url = "http://10.0.2.2:8080/GooglePlayServer/home?index=0";
        StringRequest stringRequest = new StringRequest(url, mStringListener, mErrorListener);
        Volley.newRequestQueue(this).add(stringRequest);
    }
## 4. JsonObject请求 ##
    public void onStartJsonObjectRequest(View view) {
        String url = "http://10.0.2.2:8080/GooglePlayServer/home?index=0";
        //Get请求第二个参数传null
        //Post请求第二个参数传JsonObject对象
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, mJSONObjectListener, mErrorListener);
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
## 5. JsonArray请求 ##
    public void onStartJsonArrayRequest(View view) {
        String url = "http://10.0.2.2:8080/GooglePlayServer/app?index=0";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, mJSONArrayListener, mErrorListener);
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

## 6. 自定义Gson请求 ##

### 添加Gson依赖 ###
    compile 'com.google.code.gson:gson:2.8.0'
### 创建Gson请求 ###
	
	public class GsonRequest<T> extends Request<T> {
	
	    private final Response.Listener<T> mListener;
	
	    private Gson mGson = new Gson();
	
	    private Class<T> mClass;//要解析的类的类对象
	
	    public GsonRequest(String url, Class classz,  Response.Listener<T> listener, Response.ErrorListener errorListener) {
	        this(Method.GET, url, classz, listener, errorListener);
	    }
	
	    public GsonRequest(int method, String url, Class classz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
	        super(method, url, errorListener);
	        mListener = listener;
	        mClass = classz;
	    }
	
	    /**
	     * 将网络请求的结果用Gson解析成期望的类型，在子线程中调用
	     */
	    @Override
	    protected Response<T> parseNetworkResponse(NetworkResponse response) {
	        String parsedString;
	        try {
	            parsedString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
	        } catch (UnsupportedEncodingException e) {
	            parsedString = new String(response.data);
	        }
	        Log.d(TAG, "parsedString: " + parsedString);
	        T result = mGson.fromJson(parsedString, mClass);
	        return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
	    }
	
	    /**
	     * 分发解析后的结果，在主线程中调用
	     */
	    @Override
	    protected void deliverResponse(T response) {
	        mListener.onResponse(response);
	    }
	}


### 发送请求 ###
    public void onStartGsonRequest(View view) {
        String url = "http://10.0.2.2:8080/GooglePlayServer/home?index=0";
        GsonRequest<HomeBean> request = new GsonRequest<HomeBean>(url, HomeBean.class, mHomeBeanListener, mErrorListener);
        Volley.newRequestQueue(MainActivity.this).add(request);
    }


## 7. 图片请求 ##
    public void onStartImageRequest(View view) {
        String url  = "http://10.0.2.2:8080/GooglePlayServer/image?name=image/home01.jpg";
        //第三第四个参数分别用于指定允许图片最大的宽度和高度，如果指定的网络图片的宽度或高度大于这里的最大值，则会对图片进行压缩，
        // 指定成0的话就表示不管图片有多大，都不会进行压缩。第三第四个参数分别用于指定允许图片最大的宽度和高度，如果指定的网络图片
        // 的宽度或高度大于这里的最大值，则会对图片进行压缩，指定成0的话就表示不管图片有多大，都不会进行压缩。
        ImageRequest request = new ImageRequest(url, mBitmapListener, 0, 0, Bitmap.Config.RGB_565, mErrorListener);
        Volley.newRequestQueue(this).add(request);
    }

## 8. 请求队列的封装 ##
一个应用只需一个RequestQueue, 不必每次发请求都创建一个请求队列。

	public class NetworkManager {
	
	    private static NetworkManager sNetworkManager;
	
	    private RequestQueue mQueue;
	
	    public static NetworkManager getInstance() {
	        if (sNetworkManager == null) {
	            synchronized (NetworkManager.class) {
	                if (sNetworkManager == null) {
	                    sNetworkManager = new NetworkManager();
	                }
	            }
	        }
	        return sNetworkManager;
	    }
	
	    public void init(Context context) {
	        mQueue = Volley.newRequestQueue(context);
	    }
	
	    public void sendRequest(Request request) {
	        mQueue.add(request);
	    }
	}

## 9. NetworkImageView的使用 ##
### 设置网络图片 ###
    mNetworkImageView = (NetworkImageView) findViewById(R.id.network_image_view);
    mNetworkImageView.setDefaultImageResId(R.mipmap.ic_launcher);//设置默认图片
    String url  = "http://10.0.2.2:8080/GooglePlayServer/image?name=image/home01.jpg";
    mNetworkImageView.setImageUrl(url, NetworkManager.getInstance().getImageLoader());

### ImageLoader的封装 ###
ImageLoader是加载和缓存网络图片的工具。由于它也要用到RequestQueue, 一个应用也只需要一个ImageLoader,所以同样的封装到NetworkManager中。

    public void init(Context context) {
        mQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mQueue, new ImageLruCache(DEFAULT_IMAGE_CACHE_SIZE));
    }


    /**
     * 图片内存LRU缓存
     */
    private static class ImageLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache{


        public ImageLruCache(int maxSize) {
            super(maxSize);
        }

        /**
         * 返回对应key的bitmap的大小，当存入缓存时，要计算是否超出缓存的最大值
         */
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }

        /**
         * 返回对应url的图片缓存
         */
        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        /**
         * 存入缓存
         */
        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }
    }

# Volley的封装层级 #
Volley的封装级别类似Retrofit，[FunHttp](https://github.com/uncleleonfan/FunHttp)。 Retrofit，FunHttp都是对OKhttp的一层封装，解决了数据转换和线程切换等问题。
Volley内部使用HttpClient或者HttpURLConnection完成网络请求，由于Volley的良好扩展性，还可以配置使用Okhttp进行网络请求。
可以看出HttpClient，HttpURLConnection，Okhttp属于同一层级，Retrofit，Volley，FunHttp属于同一层级。

## 不要纠结细节，看主要逻辑和框架 ##
[如何阅读源码](http://www.jianshu.com/p/be86e5678252)

[源码分析大全](http://www.codekk.com/open-source-project-analysis)

## 请求队列的初始化 ##
1. 磁盘缓存的初始化（DiskBasedCache）mCache
2. 执行网络请求对象（Network）的创建 mNetwork
3. 初始化网络请求的线程池mDispatchers = new NetworkDispatcher[threadPoolSize];，默认大小是4.
4. 创建网络请求响应和错误的分发器mDelivery=new ExecutorDelivery(new Handler(Looper.getMainLooper()))

## 请求队列的启动 ##
1. 创建缓存分发器，启动该线程，执行run方法，run方法里面初始化磁盘缓存（把缓存文件的头读取出来，存入集合）
	    
		mCacheDispatcher = new CacheDispatcher(mCacheQueue, mNetworkQueue, mCache, mDelivery);
        mCacheDispatcher.start();


2. 创建网络分发器并且启动

        // Create network dispatchers (and corresponding threads) up to the pool size.
        for (int i = 0; i < mDispatchers.length; i++) {
            NetworkDispatcher networkDispatcher = new NetworkDispatcher(mNetworkQueue, mNetwork,
                    mCache, mDelivery);
            mDispatchers[i] = networkDispatcher;
            networkDispatcher.start();
        }

## 发送请求 ##

1. 首先网络请求添加到缓存请求队列mCacheQueue，CacheDispatcher的run方法里面的监控mCacheQueue，如果mCacheQueue有请求，则拿出来，查看是否有缓存，如果有并且没有过期，则解析网络缓存的结果，分发到主线程
2. 请求加入到网络请求队列mNetworkQueue,NetworkDispatcher的run方法里面监控mNetworkQueue,如果有请求，则拿出来发送网络请求，获取到结果后解析，然后存入缓存，最后分发到主线程。

