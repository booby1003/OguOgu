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
        private String Title;
        private String Board_idx;
        private String StoreName;
        private String Addr;
        private int BoardType;
        private boolean IsLike;
        private boolean IsBookmark;
        private String ImgPath;
        private String RegUserID;
        private String RegThunmbPath;
        private String RegDate;

        public void clear(){
            instance = null;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getBoard_idx() {
            return Board_idx;
        }

        public void setBoard_idx(String board_idx) {
            Board_idx = board_idx;
        }

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String storeName) {
            StoreName = storeName;
        }

        public String getAddr() {
            return Addr;
        }

        public void setAddr(String addr) {
            Addr = addr;
        }

        public int getBoardType() {
            return BoardType;
        }

        public void setBoardType(int boardType) {
            BoardType = boardType;
        }

        public boolean isLike() {
            return IsLike;
        }

        public void setLike(boolean like) {
            IsLike = like;
        }

        public boolean isBookmark() {
            return IsBookmark;
        }

        public void setBookmark(boolean bookmark) {
            IsBookmark = bookmark;
        }

        public String getImgPath() {
            return ImgPath;
        }

        public void setImgPath(String imgPath) {
            ImgPath = imgPath;
        }

        public String getRegUserID() {
            return RegUserID;
        }

        public void setRegUserID(String regUserID) {
            RegUserID = regUserID;
        }

        public String getRegThunmbPath() {
            return RegThunmbPath;
        }

        public void setRegThunmbPath(String regThunmbPath) {
            RegThunmbPath = regThunmbPath;
        }

        public String getRegDate() {
            return RegDate;
        }

        public void setRegDate(String regDate) {
            RegDate = regDate;
        }
    }

}
