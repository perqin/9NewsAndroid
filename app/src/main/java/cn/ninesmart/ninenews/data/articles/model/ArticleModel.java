package cn.ninesmart.ninenews.data.articles.model;

import cn.ninesmart.ninenews.data.network.body.GetArticlesRes;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class ArticleModel {
    // FIXME: Need better solution to deal with such paging API
    public GetArticlesRes.Pager pager;

    private String articleId;
    private String topic;
    private String category;
    private int commentCount;
    private int viewCount;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
