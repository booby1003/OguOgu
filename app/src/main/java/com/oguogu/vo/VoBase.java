package com.oguogu.vo;

/**
 * Created by Administrator on 2016-05-18.
 */
public class VoBase {
    public static final int RESULT_SUCCESS = 200;

    public static final int TYPE_STORY = 1;
    public static final int TYPE_CAFE = 2;
    public static final int TYPE_HOSPITAL = 3;
    public static final int TYPE_PLAYGROUND = 4;

    private String resultMsg;
    private int resultCode;
    private String img_domain;

    public String getResultMsg() {
        return resultMsg;
    }
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    public int getResultCode() {
        return resultCode;
    }
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
    public boolean isSuccess(){
        if(resultCode == RESULT_SUCCESS){
            return true;
        }
        return false;
    }
}
