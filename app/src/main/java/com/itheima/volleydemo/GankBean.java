package com.itheima.volleydemo;

import java.util.List;

public class GankBean {

    /**
     * error : false
     * results : [{"_id":"59a65dd2421aa901c85e5fe9","createdAt":"2017-08-30T14:40:18.444Z","desc":"Android版ARKit","publishedAt":"2017-09-01T12:55:52.582Z","source":"web","type":"Android","url":"https://github.com/google-ar/arcore-android-sdk","used":true,"who":"JackHang"},{"_id":"59a79050421aa901bcb7dbd6","createdAt":"2017-08-31T12:28:00.485Z","desc":"How to create multiple apk files for android application","publishedAt":"2017-09-01T12:55:52.582Z","source":"web","type":"Android","url":"https://mindorks.com/blog/how-to-create-multiple-apk-files-for-android-application","used":true,"who":"AMIT SHEKHAR"},{"_id":"59a8b60a421aa901c1c0a8c2","createdAt":"2017-09-01T09:21:14.889Z","desc":"英语不好？打造一款AndroidStudio翻译插件","publishedAt":"2017-09-01T12:55:52.582Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247486724&idx=1&sn=5a66e01a2ecb72dbd97c619718d906ab","used":true,"who":"陈宇明"},{"_id":"59a621eb421aa901bcb7dbcb","createdAt":"2017-08-30T10:24:43.354Z","desc":"一个滑动关闭Activity的组件，可以设置上下左右滑动关闭","images":["http://img.gank.io/d6ce9191-6f9a-4aa5-96cd-b175fb8eef3e"],"publishedAt":"2017-08-31T08:22:07.982Z","source":"web","type":"Android","url":"https://github.com/gongwen/SwipeBackLayout","used":true,"who":"龚文"},{"_id":"59a64ec7421aa901c1c0a8ab","createdAt":"2017-08-30T13:36:07.559Z","desc":"腾讯团队开源的 致力于提高项目 UI 开发效率的解决方案","publishedAt":"2017-08-31T08:22:07.982Z","source":"chrome","type":"Android","url":"http://qmuiteam.com/android/page/index.html","used":true,"who":"代码家"},{"_id":"59a64ef7421aa901c85e5fe7","createdAt":"2017-08-30T13:36:55.542Z","desc":"Glide 图像变换功能库。","publishedAt":"2017-08-31T08:22:07.982Z","source":"chrome","type":"Android","url":"https://github.com/wasabeef/glide-transformations","used":true,"who":"代码家"},{"_id":"59a6beb7421aa901c1c0a8b1","createdAt":"2017-08-30T21:33:43.862Z","desc":"Android WebView 详解","publishedAt":"2017-08-31T08:22:07.982Z","source":"web","type":"Android","url":"http://reezy.me/p/20170515/android-webview/","used":true,"who":"ezy"},{"_id":"59a4ea09421aa901b9dc4652","createdAt":"2017-08-29T12:14:01.783Z","desc":"在任何非 MIUI 设备上体验小米系统级推送。","images":["http://img.gank.io/1c974dca-f68d-4925-826e-863ac8a40d48"],"publishedAt":"2017-08-29T12:19:18.634Z","source":"chrome","type":"Android","url":"https://github.com/Trumeet/MiPushFramework","used":true,"who":"代码家"},{"_id":"59a4ea9d421aa901b9dc4655","createdAt":"2017-08-29T12:16:29.115Z","desc":"Box 效果的 EditText，很漂亮哦。","images":["http://img.gank.io/c55c1de8-ff13-47a9-b624-4b2dac5e91a0"],"publishedAt":"2017-08-29T12:19:18.634Z","source":"chrome","type":"Android","url":"https://github.com/HITGIF/TextFieldBoxes","used":true,"who":"代码家"},{"_id":"599e2d51421aa901c85e5fbd","createdAt":"2017-08-24T09:35:13.750Z","desc":"物联网来了，你还不会蓝牙开发？","publishedAt":"2017-08-24T12:43:10.124Z","source":"web","type":"Android","url":"http://url.cn/5ymK3Ps","used":true,"who":"陈宇明"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 59a65dd2421aa901c85e5fe9
         * createdAt : 2017-08-30T14:40:18.444Z
         * desc : Android版ARKit
         * publishedAt : 2017-09-01T12:55:52.582Z
         * source : web
         * type : Android
         * url : https://github.com/google-ar/arcore-android-sdk
         * used : true
         * who : JackHang
         * images : ["http://img.gank.io/d6ce9191-6f9a-4aa5-96cd-b175fb8eef3e"]
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
