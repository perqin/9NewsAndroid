package cn.ninesmart.ninenews.data.comments.models;

import cn.ninesmart.ninenews.data.network.body.GetCommentsIdRes;
import cn.ninesmart.ninenews.data.users.models.UserModel;

/**
 * Author   : perqin
 * Date     : 17-1-27
 */

public class CommentModel {
    // FIXME: Need better solution to deal with such paging API
    public GetCommentsIdRes.Pager pager;

    private String commentId;
    private String content;
    private String device;
    private long date;
    private UserModel author;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
