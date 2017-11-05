package com.oguogu.vo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-07-03.
 */
public class VoPlaceDetail extends VoDetail{

    private static VoPlaceDetail instance;

    private VoPlaceDetail() {}

    public static synchronized VoPlaceDetail getInstance() {
        if (instance == null)
            instance = new VoPlaceDetail();
        return instance;
    }

    private String id;
    private String BoardIdx;
    private String PlaceIdx;
    private String PlaceName;
    private String PlaceAddr;
    private String Conts;
    private int BoardType;
    private boolean IsLike;
    private boolean IsBookmark;
    private String RegUserId;
    private String Time;
    private String TelNo;
    private String TotalLikeCnt;
    private String TotalBookmarkCnt;
    private String TotalCommentCnt;

    private ArrayList<String> ExtraInfoList = new ArrayList<>();
    private ArrayList<String> PriceList = new ArrayList<>();

    private ArrayList<VoComment> CommentList = new ArrayList<VoComment>();
    private ArrayList<VoRelation> RelationList = new ArrayList<VoRelation>();
    private ArrayList<String> ImgList = new ArrayList<String>();

    public void clear(){
        instance = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoardIdx() {
        return BoardIdx;
    }

    public void setBoardIdx(String boardIdx) {
        BoardIdx = boardIdx;
    }

    public String getPlaceIdx() {
        return PlaceIdx;
    }

    public void setPlaceIdx(String placeIdx) {
        PlaceIdx = placeIdx;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public String getPlaceAddr() {
        return PlaceAddr;
    }

    public void setPlaceAddr(String placeAddr) {
        PlaceAddr = placeAddr;
    }

    public String getConts() {
        return Conts;
    }

    public void setConts(String conts) {
        Conts = conts;
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

    public String getRegUserId() {
        return RegUserId;
    }

    public void setRegUserId(String regUserId) {
        RegUserId = regUserId;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTelNo() {
        return TelNo;
    }

    public void setTelNo(String telNo) {
        TelNo = telNo;
    }

    public String getTotalLikeCnt() {
        return TotalLikeCnt;
    }

    public void setTotalLikeCnt(String totalLikeCnt) {
        TotalLikeCnt = totalLikeCnt;
    }

    public String getTotalBookmarkCnt() {
        return TotalBookmarkCnt;
    }

    public void setTotalBookmarkCnt(String totalBookmarkCnt) {
        TotalBookmarkCnt = totalBookmarkCnt;
    }

    public String getTotalCommentCnt() {
        return TotalCommentCnt;
    }

    public void setTotalCommentCnt(String totalCommentCnt) {
        TotalCommentCnt = totalCommentCnt;
    }

    public ArrayList<VoComment> getCommentList() {
        return CommentList;
    }

    public ArrayList<String> getExtraInfoList() {
        return ExtraInfoList;
    }

    public void setExtraInfoList(ArrayList<String> extraInfoList) {
        ExtraInfoList = extraInfoList;
    }

    public ArrayList<String> getPriceList() {
        return PriceList;
    }

    public void setPriceList(ArrayList<String> priceList) {
        PriceList = priceList;
    }

    public void setCommentList(ArrayList<VoComment> commentList) {
        CommentList = commentList;
    }

    public ArrayList<VoRelation> getRelationList() {
        return RelationList;
    }

    public void setRelationList(ArrayList<VoRelation> relationList) {
        RelationList = relationList;
    }

    public ArrayList<String> getImgList() {
        return ImgList;
    }

    public void setImgList(ArrayList<String> imgList) {
        ImgList = imgList;
    }

    public static class VoRelation {
        private String BoardIdx;
        private String RelationUserId;
        private String RelationThumbPath;

        public String getBoardIdx() {
            return BoardIdx;
        }

        public void setBoardIdx(String boardIdx) {
            BoardIdx = boardIdx;
        }

        public String getRelationUserId() {
            return RelationUserId;
        }

        public void setRelationUserId(String relationUserId) {
            RelationUserId = relationUserId;
        }

        public String getRelationThumbPath() {
            return RelationThumbPath;
        }

        public void setRelationThumbPath(String relationThumbPath) {
            RelationThumbPath = relationThumbPath;
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