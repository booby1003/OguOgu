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

    public final static String REQUEST_GET_PLACE = "places";
    public final static String REQUEST_GET_PLACE_DETAIL = "places/detail";
    public final static String REQUEST_GET_HOME = "homeList";
    public final static String REQUEST_GET_FEED = "feed";
    public final static String REQUEST_GET_COMMENT = "places/comments/";
    public final static String REQUEST_GET_STORY = "story";
    public final static String REQUEST_GET_WRITE_PLACE = "writePlace";
    public final static String REQUEST_SET_WRITE_PLACE = "postWriting";
    public final static String REQUEST_SET_WRITE_COMMT = "places/comments";

    public final static String REQUEST_TAG_PLACE = "TagPlace";
    public final static String REQUEST_TAG_PLACE_DETAIL = "TagPlaceDetail";
    public final static String REQUEST_TAG_HOME = "TagHome";
    public final static String REQUEST_TAG_FEED = "TagFeed";
    public final static String REQUEST_TAG_COMMENT = "TagComment";
    public final static String REQUEST_TAG_STORY = "TagStory";
    public final static String REQUEST_TAG_WRITE_PLACE = "TagWritePlace";
    public final static String REQUEST_TAG_SET_WRITE_PLACE = "TagWritePlace";
    public final static String REQUEST_TAG_SET_WRITE_COMMT = "TagWriteCommt";

}
