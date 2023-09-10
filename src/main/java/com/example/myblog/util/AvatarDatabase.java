package com.example.myblog.util;

public class AvatarDatabase {

    //头像库
   private static final String[] avatars = new String[]{
           "https://tupian.qqw21.com/article/UploadPic/2020-4/202042521473464800.jpg",
            "https://tupian.qqw21.com/article/UploadPic/2020-11/2020112022533422641.jpg",
            "https://tse4-mm.cn.bing.net/th/id/OIP-C.BRsupPiwcdppmfb1QoHf6wAAAA?pid=ImgDet&rs=1",
            "https://pic4.zhimg.com/v2-64b387b3f4024f5510eff306e1f4f1a3_r.jpg",
            "https://tupian.qqw21.com/article/UploadPic/2020-3/2020391718038080.jpg",
            "https://tupian.qqw21.com/article/UploadPic/2021-8/202182123122335866.jpg",
            "https://tupian.qqw21.com/article/UploadPic/2020-11/2020112022533387948.jpg",
            "https://tupian.qqw21.com/article/UploadPic/2020-1/20201142221364143.jpg",
            "https://tupian.qqw21.com/article/UploadPic/2020-12/20201219221822811.jpg",
            "https://tupian.qqw21.com/article/UploadPic/2020-11/2020112022533520232.jpg",
            "https://tupian.qqw21.com/article/UploadPic/2021-1/20211221364656292.jpg"
    };


    public static String getAvatar(){
        int index = (int) (Math.random() * avatars.length);
        return avatars[index];
    }

}
