package cn.ninesmart.ninenews.data.network.body;

import java.util.List;

/**
 * Author   : perqin
 * Date     : 17-1-28
 */

public class GetCommentsIdRes {
    /*
    *   "comments": [
    {
      "id": "588b3f1f4c6cb5088698367d",
      "type": "news",
      "uid": "588b7be44c6cb5088698368b",
      "content": "大家新年好！",
      "reply_num": 0,
      "dateline": 1485605080511,
      "replys": [],
      "agrees": 0,
      "againsts": 0,
      "ip": "112.26.77.154",
      "model": "Passport",
      "_id": "588c88d84c6cb508869836b2",
      "user": {
        "nickname": "骑车兜风",
        "avatar": "http://g.9smart.cn/guest.png",
        "_id": "588b7be44c6cb5088698368b"
      }
    },
    * */
    public List<Comment> comments;
    public Pager pager;

    public static class Comment {
        public String id;
        public String content;
        public String model;
        public User user;

        public static class User {
            public String _id;
            public String nickname;
            public String avatar;
        }
    }

    public static class Pager {
        public long last_dateline;
        public int next_page;
    }
}
