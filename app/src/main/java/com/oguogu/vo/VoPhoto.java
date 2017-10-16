package com.oguogu.vo;

/**
 * Created by 김민정 on 2017-10-10.
 */

public class VoPhoto {

    private String boardIdx;
    private String placeIdx;
    private int boardType;
    private String imgThumbPath;
    private String imgPath;

    public String getBoardIdx() {
        return boardIdx;
    }

    public void setBoardIdx(String boardIdx) {
        this.boardIdx = boardIdx;
    }

    public String getPlaceIdx() {
        return placeIdx;
    }

    public void setPlaceIdx(String placeIdx) {
        this.placeIdx = placeIdx;
    }

    public int getBoardType() {
        return boardType;
    }

    public void setBoardType(int boardType) {
        this.boardType = boardType;
    }

    public String getImgThumbPath() {
        return imgThumbPath;
    }

    public void setImgThumbPath(String imgThumbPath) {
        this.imgThumbPath = imgThumbPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
