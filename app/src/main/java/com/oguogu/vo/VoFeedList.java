package com.oguogu.vo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-07-03.
 */
public class VoFeedList extends VoBase{

    public static final int TYPE_COMMENT = 1;       //댓글
    public static final int TYPE_LIKE = 2;          //좋아요
    public static final int TYPE_STORAGE = 3;       //보관함

    private ArrayList<VoFeedInfo> Data = new ArrayList<VoFeedInfo>();

    private static VoFeedList instance;

    public VoFeedList() {
    }

    public ArrayList<VoFeedInfo> getData() {

        return Data;
    }

    public static synchronized VoFeedList getInstance() {
        if (instance == null)
            instance = new VoFeedList();
        return instance;
    }

    public class VoFeedInfo {
        private String BoardIdx;
        private String RegThunmbPath;
        private String FeedUserId;
        private int BoardType;
        private int FeedType;
        private String Date;
        private String ImgPath;

        public String getBoardIdx() {
            return BoardIdx;
        }

        public void setBoardIdx(String boardIdx) {
            BoardIdx = boardIdx;
        }

        public String getRegThunmbPath() {
            return RegThunmbPath;
        }

        public void setRegThunmbPath(String regThunmbPath) {
            RegThunmbPath = regThunmbPath;
        }

        public String getFeedUserId() {
            return FeedUserId;
        }

        public void setFeedUserId(String feedUserId) {
            FeedUserId = feedUserId;
        }

        public int getBoardType() {
            return BoardType;
        }

        public void setBoardType(int boardType) {
            BoardType = boardType;
        }

        public int getFeedType() {
            return FeedType;
        }

        public void setFeedType(int feedType) {
            FeedType = feedType;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }

        public String getImgPath() {
            return ImgPath;
        }

        public void setImgPath(String imgPath) {
            ImgPath = imgPath;
        }
    }

}
