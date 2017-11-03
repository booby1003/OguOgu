package com.oguogu.communication;

/**
 * Created by 김민정 on 2017-09-28.
 */

public class ConstantCommURL {

    public static boolean ISTEST = true;

    public static final String APP_DOMAIN = "http://118.107.161.87/";
    public static final String APP_TEST_DOMAIN = "http://118.107.161.87/";

    public static final String URL_API = "api/";
    public static final String URL_COMMON = "Common/";

    public static String getURL(String url, String request) {
        return (ISTEST ? APP_TEST_DOMAIN : APP_DOMAIN) + url + request;
    }

    public final static String REQUEST_GET_PLACE = "Place";
    public final static String REQUEST_GET_HOME = "HomeList";
    public final static String REQUEST_GET_FEED = "Feed";
    public final static String REQUEST_GET_STORY = "Story";

    public final static String REQUEST_TAG_PLACE = "TagPlace";
    public final static String REQUEST_TAG_HOME = "TagHome";
    public final static String REQUEST_TAG_FEED = "TagFeed";
    public final static String REQUEST_TAG_STORY = "TagStory";

}
