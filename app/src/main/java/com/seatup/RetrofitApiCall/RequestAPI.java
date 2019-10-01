package com.seatup.RetrofitApiCall;

import com.seatup.commonPojo.LoginRequest;
import com.seatup.commonPojo.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Home on 02-Feb-18.
 */

public interface RequestAPI {
    String key = "/Key";

    @POST(key)
    Call<LoginResponse> postLoginRequest(@Body LoginRequest request);
}
