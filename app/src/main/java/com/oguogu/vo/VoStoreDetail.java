package com.oguogu.vo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-07-03.
 */
public class VoStoreDetail extends VoDetail{

    private static VoStoreDetail instance;

    private VoStoreDetail() {
    }

    public static synchronized VoStoreDetail getInstance() {
        if (instance == null)
            instance = new VoStoreDetail();
        return instance;
    }

    private String id;
    private String board_idx;
    private String storeName;
    private String addr;
    private String conts;
    private int boardType;
    private int isLike;
    private int isBookmark;
    private String regUserNickname;
    private String time;
    private String tel_no;
    private String total_like_cnt;
    private String total_bookmark_cnt;
    private String total_comment_cnt;

    private ArrayList<VoComment> comment_list = new ArrayList<VoComment>();
    private ArrayList<VoRelation> relation_list = new ArrayList<VoRelation>();
    private ArrayList<VoStore> store_info_list = new ArrayList<VoStore>();
    private ArrayList<VoImagePath> img_list = new ArrayList<VoImagePath>();

    public void clear(){
        instance = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoard_idx() {
        return board_idx;
    }

    public void setBoard_idx(String board_idx) {
        this.board_idx = board_idx;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getConts() {
        return conts;
    }

    public void setConts(String conts) {
        this.conts = conts;
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

    public int getIsBookmark() {
        return isBookmark;
    }

    public void setIsBookmark(int isBookmark) {
        this.isBookmark = isBookmark;
    }

    public String getRegUserNickname() {
        return regUserNickname;
    }

    public void setRegUserNickname(String regUserNickname) {
        this.regUserNickname = regUserNickname;
    }

    public String getTel_no() {
        return tel_no;
    }

    public void setTel_no(String tel_no) {
        this.tel_no = tel_no;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal_like_cnt() {
        return total_like_cnt;
    }

    public void setTotal_like_cnt(String total_like_cnt) {
        this.total_like_cnt = total_like_cnt;
    }

    public String getTotal_bookmark_cnt() {
        return total_bookmark_cnt;
    }

    public void setTotal_bookmark_cnt(String total_bookmark_cnt) {
        this.total_bookmark_cnt = total_bookmark_cnt;
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

    public ArrayList<VoRelation> getRelation_list() {
        return relation_list;
    }

    public void setRelation_list(ArrayList<VoRelation> relation_list) {
        this.relation_list = relation_list;
    }

    public ArrayList<VoStore> getStore_info_list() {
        return store_info_list;
    }

    public void setStore_info_list(ArrayList<VoStore> store_info_list) {
        this.store_info_list = store_info_list;
    }

    public ArrayList<VoImagePath> getImg_list() {
        return img_list;
    }

    public void setImg_list(ArrayList<VoImagePath> img_list) {
        this.img_list = img_list;
    }

    public static class VoRelation {
        private String board_idx;
        private String relation_nickname;
        private String relation_thumb_path;

        public String getRelation_nickname() {
            return relation_nickname;
        }

        public String getBoard_idx() {
            return board_idx;
        }

        public void setBoard_idx(String board_idx) {
            this.board_idx = board_idx;
        }

        public void setRelation_nickname(String relation_nickname) {
            this.relation_nickname = relation_nickname;
        }

        public String getRelation_thumb_path() {
            return relation_thumb_path;
        }

        public void setRelation_thumb_path(String relation_thumb_path) {
            this.relation_thumb_path = relation_thumb_path;
        }
    }

    public static class VoStore {
        private String store_info;

        public String getStore_info() {
            return store_info;
        }

        public void setStore_info(String store_info) {
            this.store_info = store_info;
        }
    }


}
