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

    private String BoardIdx;
    private String Title;
    private String Conts;
    private String RegDate;
    private String PlaceName;
    private String PlaceAddr;
    private int BoardType;
    private boolean IsLike;
    private String RegUserID;
    private String RegThunmbPath;
    private String TotalLikeCnt;
    private String TotalCommentCnt;

    private ArrayList<String> ImgList = new ArrayList<String>();
    private ArrayList<VoComment> CommentList = new ArrayList<VoComment>();

    public void clear(){
        instance = null;
    }


    public static void setInstance(VoStoryDetail instance) {
        VoStoryDetail.instance = instance;
    }

    public String getBoardIdx() {
        return BoardIdx;
    }

    public void setBoardIdx(String boardIdx) {
        BoardIdx = boardIdx;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getConts() {
        return Conts;
    }

    public void setConts(String conts) {
        Conts = conts;
    }

    public String getRegDate() {
        return RegDate;
    }

    public void setRegDate(String regDate) {
        RegDate = regDate;
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

    public String getTotalLikeCnt() {
        return TotalLikeCnt;
    }

    public void setTotalLikeCnt(String totalLikeCnt) {
        TotalLikeCnt = totalLikeCnt;
    }

    public String getTotalCommentCnt() {
        return TotalCommentCnt;
    }

    public void setTotalCommentCnt(String totalCommentCnt) {
        TotalCommentCnt = totalCommentCnt;
    }

    public ArrayList<String> getImgList() {
        return ImgList;
    }

    public void setImgList(ArrayList<String> imgList) {
        ImgList = imgList;
    }

    public ArrayList<VoComment> getCommentList() {
        return CommentList;
    }

    public void setCommentList(ArrayList<VoComment> commentList) {
        CommentList = commentList;
    }
}
