package com.oguogu.vo;

import java.util.ArrayList;

/**
 * Created by 김민정 on 2017-12-18.
 */

public class VoWriteCateList extends VoBase {
    private ArrayList<VoPlaceCategory> cateTypeList = new ArrayList<>();
    private ArrayList<VoExtraInfo> extraInfoList = new ArrayList<>();

    public ArrayList<VoPlaceCategory> getCateTypeList() {
        return cateTypeList;
    }

    public void setCateTypeList(ArrayList<VoPlaceCategory> cateTypeList) {
        this.cateTypeList = cateTypeList;
    }

    public ArrayList<VoExtraInfo> getExtraInfoList() {
        return extraInfoList;
    }

    public void setExtraInfoList(ArrayList<VoExtraInfo> extraInfoList) {
        this.extraInfoList = extraInfoList;
    }
}
