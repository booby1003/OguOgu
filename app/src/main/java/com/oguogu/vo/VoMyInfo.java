package com.oguogu.vo;

/**
 * Created by Administrator on 2016-07-03.
 */
public class VoMyInfo extends VoBase{

    private static VoMyInfo instance;

    private VoMyInfo() {
    }

    public static synchronized VoMyInfo getInstance() {
        if (instance == null)
            instance = new VoMyInfo();
        return instance;
    }

    public static final int LOGIN_OGUOGU = 1;
    public static final int LOGIN_GOOGLE = 2;
    public static final int LOGIN_KAKAO = 3;
    public static final int LOGIN_FACEBOOK = 4;

    private String id;
    private String nickname;
    private String picture;
    private String token;
    private int login_type;

    public void clear(){
        instance = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLogin_type() {
        return login_type;
    }

    public void setLogin_type(int login_type) {
        this.login_type = login_type;
    }
}
