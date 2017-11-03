package com.oguogu.vo;

import java.util.ArrayList;

/**
 * Created by 김민정 on 2017-09-26.
 */

public class VoPlaceList extends VoBase {

    private ArrayList<VoPlace> Data = new ArrayList<>();

    public ArrayList<VoPlace> getData() {
        return Data;
    }

    public void setData(ArrayList<VoPlace> data) {
        Data = data;
    }

    public class VoPlace {
        private String BoardIdx;
        private String PlaceName;
        private String PlaceAddr;
        private int BoardType;
        private String placeTypeStr;
        private boolean IsLike ;
        private boolean IsBookmark;
        private String ImgPath;
        private String RegThunmbPath;

        public String getBoardIdx() {
            return BoardIdx;
        }

        public void setBoardIdx(String boardIdx) {
            BoardIdx = boardIdx;
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

        public String getPlaceTypeStr() {
            return placeTypeStr;
        }

        public void setPlaceTypeStr(String placeTypeStr) {
            this.placeTypeStr = placeTypeStr;
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

        public String getRegThunmbPath() {
            return RegThunmbPath;
        }

        public void setRegThunmbPath(String regThunmbPath) {
            RegThunmbPath = regThunmbPath;
        }
    }
}
