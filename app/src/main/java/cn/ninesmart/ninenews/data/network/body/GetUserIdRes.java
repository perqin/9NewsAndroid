package cn.ninesmart.ninenews.data.network.body;

/**
 * Author   : perqin
 * Date     : 17-1-14
 */

public class GetUserIdRes {
    public Member member;

    public static class Member {
        public String _id;
        public String avatar;
        public String avatar_hd;
        public String email;
        public String group;
        public String nickname;
    }
}
