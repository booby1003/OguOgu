package com.oguogu.vo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-07-03.
 */
public class VoFeedList extends VoBase{

    public static final int TYPE_COMMENT = 1;
    public static final int TYPE_LIKE = 2;

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
        private String feed_thumb_path;
        private String feed_nickname;
        private int feed_type;
        private String feed_date;
        private String origin_thumb_path;

        public String getFeed_thumb_path() {
            return feed_thumb_path;
        }

        public void setFeed_thumb_path(String feed_thumb_path) {
            this.feed_thumb_path = feed_thumb_path;
        }

        public String getFeed_nickname() {
            return feed_nickname;
        }

        public void setFeed_nickname(String feed_nickname) {
            this.feed_nickname = feed_nickname;
        }

        public int getFeed_type() {
            return feed_type;
        }

        public void setFeed_type(int feed_type) {
            this.feed_type = feed_type;
        }

        public String getFeed_date() {
            return feed_date;
        }

        public void setFeed_date(String feed_date) {
            this.feed_date = feed_date;
        }

        public String getOrigin_thumb_path() {
            return origin_thumb_path;
        }

        public void setOrigin_thumb_path(String origin_thumb_path) {
            this.origin_thumb_path = origin_thumb_path;
        }
    }

}
