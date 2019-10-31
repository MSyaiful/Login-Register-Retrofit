package com.warehousenesia.cobaware1.Service;

import com.warehousenesia.cobaware1.Model.DataPaket;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("transaction/paket")
    Call<ArrayList<DataPaket>> getPaket();

//    @POST("auth/addagentdata")
//    Observable<String> registerAgent(@Field("fullname") String fullname,
//                                     @Field("companyName") String companyName,
//                                     @Field("address") String address,
//                                     @Field("provinsi") String provinsi,
//                                     @Field("kota") String kota,
//                                     @Field("kodepos") Integer kodepos);

}
