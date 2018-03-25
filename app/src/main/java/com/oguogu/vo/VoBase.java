package com.oguogu.vo;

/**
 * Created by Administrator on 2016-05-18.
 */
public class VoBase {
    public static final int RESULT_SUCCESS = 707;

    public static final int BOARD_TYPE_PLACE = 1;
    public static final int BOARD_TYPE_STORY = 2;
    public static final int PLACE_TYPE_RESTAURANT = 1;
    public static final int PLACE_TYPE_HOSPITAL = 2;
    public static final int PLACE_TYPE_PLAYGROUND = 3;
    public static final int PLACE_TYPE_HOTEL = 4;

    private String RESULT_MSG;
    private int RESULT_CODE;
    private String img_domain;

    public String getResultMsg() {
        return RESULT_MSG;
    }
    public void setResultMsg(String resultMsg) {
        this.RESULT_MSG = resultMsg;
    }
    public int getResultCode() {
        return RESULT_CODE;
    }
    public void setResultCode(int resultCode) {
        this.RESULT_CODE = resultCode;
    }
    public boolean isSuccess(){
        if(RESULT_CODE == RESULT_SUCCESS){
            return true;
        }
        return false;
    }
}
