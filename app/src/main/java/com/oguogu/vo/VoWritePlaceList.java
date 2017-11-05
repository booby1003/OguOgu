package com.oguogu.vo;

import java.util.ArrayList;

/**
 * Created by 김민정 on 2017-09-26.
 */

public class VoWritePlaceList extends VoBase {

    private ArrayList<VoPlace> Data = new ArrayList<>();

    public ArrayList<VoPlace> getData() {
        return Data;
    }

    public void setData(ArrayList<VoPlace> data) {
        Data = data;
    }

    public class VoPlace {
        private String PlaceIdx;
        private String PlaceName;
        private String PlaceAddr;
        private int BoardType;
        private String PlaceTypeStr;

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

        public int getBoardType() {
            return BoardType;
        }

        public void setBoardType(int boardType) {
            BoardType = boardType;
        }

        public String getPlaceTypeStr() {
            return PlaceTypeStr;
        }

        public void setPlaceTypeStr(String placeTypeStr) {
            PlaceTypeStr = placeTypeStr;
        }
    }
}
