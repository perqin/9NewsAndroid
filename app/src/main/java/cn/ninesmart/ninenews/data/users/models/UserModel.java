package cn.ninesmart.ninenews.data.users.models;

import android.net.Uri;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public class UserModel {
    private String userId;
    private String email;
    private String nickname;
    private String level;
    private Uri avatarThumbSrc;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Uri getAvatarThumbSrc() {
        return avatarThumbSrc;
    }

    public void setAvatarThumbSrc(Uri avatarThumbSrc) {
        this.avatarThumbSrc = avatarThumbSrc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
