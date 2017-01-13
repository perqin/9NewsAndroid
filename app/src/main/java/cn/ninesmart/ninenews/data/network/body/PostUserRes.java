package cn.ninesmart.ninenews.data.network.body;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class PostUserRes {
    public String uid;
    public String auth;
    public Info info;

    public static class Info {
        public String nickname;
        public String group;
        public String regdate;
        public String avatar;
    }
}
