package com.oguogu.vo;

/**
 * Created by Administrator on 2016-07-03.
 */
public class VoMyInfo extends VoBase{

    private volatile static VoMyInfo instance;

    private VoMyInfo() {
        instance = this;
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

    private String email;
    private String id;
    private String imgPath;
    private String token;
    private int loginType;

    public void clear(){
        instance = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }
}
