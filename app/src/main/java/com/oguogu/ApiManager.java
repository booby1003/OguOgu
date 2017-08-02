package com.oguogu;

import com.oguogu.vo.VoHomeList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016-11-25.
 */

public interface ApiManager {
    public static final String TEST_UTL = "http://api.mm.moumou.co.kr/api/std/GetBoardList/";

    @GET("home_list")
    Call<VoHomeList>getHomeList(@Query("USERID") String userid);
}
