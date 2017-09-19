package com.oguogu.vo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-07-03.
 */
public class VoStoryDetail extends VoDetail{

    private static VoStoryDetail instance;

    private VoStoryDetail() {
    }

    public static synchronized VoStoryDetail getInstance() {
        if (instance == null)
            instance = new VoStoryDetail();
        return instance;
    }

    private String board_idx;
    private String board_title;
    private String board_content;
    private String board_date;
    private String storeName;
    private String storeAddr;
    private int boardType;
    private int isLike;
    private String regUserNickname;
    private String regUserThumbPath;
    private String regStoreUserNickname;
    private String total_like_cnt;
    private String total_comment_cnt;
    private String register_userid;

    private ArrayList<VoImagePath> store_img_list = new ArrayList<VoImagePath>();
    private ArrayList<VoImagePath> my_img_list = new ArrayList<VoImagePath>();
    private ArrayList<VoComment> comment_list = new ArrayList<VoComment>();

    public void clear(){
        instance = null;
    }

    public String getBoard_idx() {
        return board_idx;
    }

    public void setBoard_idx(String board_idx) {
        this.board_idx = board_idx;
    }

    public String getBoard_title() {
        return board_title;
    }

    public void setBoard_title(String board_title) {
        this.board_title = board_title;
    }

    public String getBoard_content() {
        return board_content;
    }

    public void setBoard_content(String board_content) {
        this.board_content = board_content;
    }

    public String getBoard_date() {
        return board_date;
    }

    public void setBoard_date(String board_date) {
        this.board_date = board_date;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr) {
        this.storeAddr = storeAddr;
    }

    public int getBoardType() {
        return boardType;
    }

    public void setBoardType(int boardType) {
        this.boardType = boardType;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public String getRegUserNickname() {
        return regUserNickname;
    }

    public void setRegUserNickname(String regUserNickname) {
        this.regUserNickname = regUserNickname;
    }

    public String getRegUserThumbPath() {
        return regUserThumbPath;
    }

    public void setRegUserThumbPath(String regUserThumbPath) {
        this.regUserThumbPath = regUserThumbPath;
    }

    public String getRegStoreUserNickname() {
        return regStoreUserNickname;
    }

    public void setRegStoreUserNickname(String regStoreUserNickname) {
        this.regStoreUserNickname = regStoreUserNickname;
    }

    public String getRegister_userid() {
        return register_userid;
    }

    public void setRegister_userid(String register_userid) {
        this.register_userid = register_userid;
    }

    public String getTotal_like_cnt() {
        return total_like_cnt;
    }

    public void setTotal_like_cnt(String total_like_cnt) {
        this.total_like_cnt = total_like_cnt;
    }

    public String getTotal_comment_cnt() {
        return total_comment_cnt;
    }

    public void setTotal_comment_cnt(String total_comment_cnt) {
        this.total_comment_cnt = total_comment_cnt;
    }

    public ArrayList<VoComment> getComment_list() {
        return comment_list;
    }

    public void setComment_list(ArrayList<VoComment> comment_list) {
        this.comment_list = comment_list;
    }

    public ArrayList<VoImagePath> getStore_img_list() {
        return store_img_list;
    }

    public void setStore_img_list(ArrayList<VoImagePath> store_img_list) {
        this.store_img_list = store_img_list;
    }

    public ArrayList<VoImagePath> getMy_img_list() {
        return my_img_list;
    }

    public void setMy_img_list(ArrayList<VoImagePath> my_img_list) {
        this.my_img_list = my_img_list;
    }



}
