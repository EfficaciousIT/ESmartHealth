package com.mobi.efficacious.esmarthealth.Interface;
import com.mobi.efficacious.esmarthealth.entity.DoctorDetail;
import com.mobi.efficacious.esmarthealth.entity.PersoanlDetail;
import com.mobi.efficacious.esmarthealth.entity.Registration;
import com.mobi.efficacious.esmarthealth.entity.TblDoctorDetailsPojo;
import com.mobi.efficacious.esmarthealth.entity.TblPersoanlDetailsPojo;
import com.mobi.efficacious.esmarthealth.entity.TblRegistrationPojo;
import com.mobi.efficacious.esmarthealth.entity.TblVisitDetailsPojo;
import com.mobi.efficacious.esmarthealth.entity.VisitDetail;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataService {
    @GET("Register")
    Call<TblRegistrationPojo> verification(@Query("command") String command,
                                           @Query("vchMobileNo") String vchMobileNo);
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Register")
    Call<ResponseBody> Insert_new_Regisration(@Body Registration insert);
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("Register")
    Call<ResponseBody> Update_password(@Body Registration update);
    @GET("Register")
    Call<TblRegistrationPojo> LoginDetails(@Query("command") String command,
                                           @Query("vchMobileNo") String vchMobileNo,
                                            @Query("vchPassword") String vchPassword);


    @Multipart
    @POST("FileUpload")
    Call<ResponseBody> upload(
            @Part MultipartBody.Part file,
            @Query("extension") String extension
    );
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("PersonalDetails")
    Call<ResponseBody> Insert_Personal_details(@Body PersoanlDetail insert);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("PersonalDetails")
    Call<ResponseBody> Update_Personal_details(@Query("command") String command,
            @Body PersoanlDetail Update);

    @GET("PersonalDetails")
    Call<TblPersoanlDetailsPojo> getPersonalData(@Query("command") String command,
                                           @Query("intResId") String intResId);
    @GET("PersonalDetails")
    Call<TblPersoanlDetailsPojo> getPersonalImage(@Query("command") String command,
                                                 @Query("intResId") String intResId,
                                                @Query("int_perId")String int_perId);
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("DoctorDetails")
    Call<ResponseBody> Insert_DoctorDetails(@Body DoctorDetail insert);

    @GET("DoctorDetails")
    Call<TblDoctorDetailsPojo> getDoctorDetails(@Query("intResId") String intResId,
                                                @Query ("command") String command);
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("DoctorDetails")
    Call<ResponseBody> Update_DoctorDetails(@Body DoctorDetail update);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("DoctorVisit")
    Call<ResponseBody> Insert_VisitDetails(@Body VisitDetail insert);
    @GET("DoctorVisit")
    Call<TblVisitDetailsPojo> getAlarmDetails(@Query ("command") String command
                                                ,@Query("int_ResId") String intResId);
    @GET("DoctorVisit")
    Call<TblVisitDetailsPojo> getdrVisitDetails(@Query ("command") String command,
                                                @Query ("intDr_visitId") String intDr_visitId,
                                                @Query ("intDoc_id") String intDoc_id,
                                                @Query("int_ResId") String intResId);
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("DoctorVisit")
    Call<ResponseBody> Update_Doctorvisit(@Query ("command") String command,
                                          @Body VisitDetail update);
}
