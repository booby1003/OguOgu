package com.oguogu.vo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-07-03.
 */
public class VoHomeList extends VoBase{

    private ArrayList<VoHomeInfo> Data = new ArrayList<VoHomeInfo>();

    private static VoHomeList instance;

    private VoHomeList() {
    }
    public ArrayList<VoHomeInfo> getData() {

        return Data;
    }

    public static synchronized VoHomeList getInstance() {
        if (instance == null)
            instance = new VoHomeList();
        return instance;
    }

    public class VoHomeInfo {
        private String title;
        private String board_idx;
        private String storeName;
        private String addr;
        private int boardType;
        private int isLike;
        private int isBookmark;
        private String imgPath;
        private String regUserID;
        private String regThunmbPath;
        private String regDate;

        public void clear(){
            instance = null;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getBoard_idx() {
            return board_idx;
        }

        public void setBoard_idx(String board_idx) {
            this.board_idx = board_idx;
        }

        public String getRegUserID() {
            return regUserID;
        }

        public void setRegUserID(String regUserID) {
            this.regUserID = regUserID;
        }

        public String getRegThunmbPath() {
            return regThunmbPath;
        }

        public void setRegThunmbPath(String regThunmbPath) {
            this.regThunmbPath = regThunmbPath;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }
    }

}
