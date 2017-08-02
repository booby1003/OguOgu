package com.oguogu.vo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-07-03.
 */
public class VoSearchList extends VoBase{

    public static final int TYPE_BEST_SEARCH_INFO = 0;
    public static final int TYPE_MY_SEARCH_INFO = 1;
    public static final int TYPE_TITLE = 2;

    private ArrayList<VoSearchInfo> Data = new ArrayList<VoSearchInfo>();

    private static VoSearchList instance;

    public  VoSearchList() {
    }

    public ArrayList<VoSearchInfo> getData() {

        return Data;
    }

    public static synchronized VoSearchList getInstance() {
        if (instance == null)
            instance = new VoSearchList();
        return instance;
    }

    public class VoSearchInfo {
        private String name;
        private int type;

        public VoSearchInfo(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
