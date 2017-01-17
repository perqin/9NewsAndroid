package cn.ninesmart.ninenews.data.network.body;

import java.util.List;

/**
 * Author   : perqin
 * Date     : 17-1-15
 */

public class GetArticleIdRes {
    public Article article;
    public List<Comment> comments;
    public List<Related> relateds;

    public static class Article {
        public String _id;
        public String topic;
        public String content;
        public List<Attachment> attachments;

        public static class Attachment {
            public String url;
        }
    }

    public static class Comment {}

    public static class Related {}
}
