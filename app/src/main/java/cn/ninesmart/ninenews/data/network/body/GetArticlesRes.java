package cn.ninesmart.ninenews.data.network.body;

import java.util.List;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public final class GetArticlesRes {
    public List<Article> articles;
    public List<String> categorys;
    public Pager pager;

    public static class Article {
        public String _id;
        public String topic;
    }

    public static class Pager {
    }
}
