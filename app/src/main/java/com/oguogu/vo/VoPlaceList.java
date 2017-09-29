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
        private String place_idx;
        private String storeName;
        private String addr;
        private int boardType;
        private String placeTypeStr;

        public String getPlace_idx() {
            return place_idx;
        }

        public void setPlace_idx(String place_idx) {
            this.place_idx = place_idx;
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

        public String getPlaceTypeStr() {
            return placeTypeStr;
        }

        public void setPlaceTypeStr(String placeTypeStr) {
            this.placeTypeStr = placeTypeStr;
        }
    }
}
