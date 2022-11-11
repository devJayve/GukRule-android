package com.example.gukrule.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

// 싱글턴
object RetrofitClient {
    // 레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    fun initCongressRetrofit(): Retrofit {
        val url = "https://open.assembly.go.kr/portal/openapi/" //서버 주소
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(url)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getBudgetService(): BudgetApi {
        return initCongressRetrofit().create(BudgetApi::class.java)
    }

    fun initLocalRetrofit(): Retrofit {
        val url = "https://www.dorisdev.shop/" //서버 주소
        val gson = Gson()                   // 서버와 주고 받을 데이터 형식
        val clientBuilder = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl(url)
            .client(clientBuilder)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun initSummaryRetrofit(): Retrofit {
        val url = "https://api-inference.huggingface.co/"
        val gson = Gson()
        val clientBuilder = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl(url)
            .client(clientBuilder)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    interface SummaryNewsApi {
        @POST("models/gogamza/kobart-summarization")
        fun postSummaryNews(
            @Header("Authorization") Authorization: String,
            @Body() summaryRequestData: SummaryRequestData
        ) : Call<SummaryArticle>

        // * "Bearer "+ 토큰
        // *
        // * 헤더 : {"Authorization": "Bearer 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"}
        //
        // * 바디 : { "inputs" : 여기에 뉴스기사 입력 }
    }

    interface CrawlingNewsApi {
        @POST("crawling/newsList")
        fun getCrawlingNews(
            @Header("x-access-token") jwtKey: String,
            @Body() crawlingRequestData: CrawlingRequestData
        ) : Call<CrawlingNewList>
    }

    interface CrawlingArticleApi {
        @POST("crawling/article")
        fun getCrawlingArticle(
            @Header("x-access-token") jwtKey: String,
            @Body() crawlingArticleRequestData: CrawlingArticleRequestData
        ) : Call<CrawlingArticle>
    }

    interface BudgetApi {
        @GET("njzofberazvhjncha")
        fun getDetailBusiness(
            @Query("KEY") key:String = "ce1a19a050384ee5ba1d3e4fecd7d8ec", // 인증키
            @Query("Type") type:String = "json",            // 호출문서(xml,json)
//            @Query("pIndex") pIndex:Int? = null,                    // 페이지 위치
//            @Query("pSize") pSize:Int? = null,                      // 페이지 당 요청 숫자
            @Query("FSCL_YY") fsclYY:String,                // 회계년도
            @Query("EXE_DATE") exeDate:String? = null,      // 집행일
            @Query("FSCL_NM") fsclName:String? = null,      // 회계명
            @Query("FLD_NM") fldName:String? = null,        // 분야명
            @Query("SECT_NM") sectName:String? = null,      // 부문명
            @Query("PGM_NM") pgmName:String? = null,        // 프로그램명
            @Query("ACTV_NM") actvName:String? = null,      // 단위사업명
        ) : Call<BudgetResponseData>
    }

    interface AccountApi{
        @POST("/auth/check/sendSMS")
        fun postSendSms(
            @Body() accountData : AccountData
        ) : Call<SmsResponse>

        @POST("/auth/check/password")
        fun postModifyPassword(
            @Body() passwordData : PasswordData
        ) : Call<ModifyPwResponse>
    }

    interface LoginApi{
        @POST("auth/login")
        fun getLoginData(
            @Body() loginData: LoginData
        ) : Call<LoginResponse>
    }

    interface RegisterApi{
        @POST("users/signup")
        fun getRegisterData(
            @Body() registerData: RegisterData
        ) : Call<RegisterResponse>
    }

    interface RegisterApiArticle{
        @POST("users/signup/5keywords")
        fun getRegisterArticleData(
            @Header("x-access-token") jwtKey: String,
            @Body() selectedArticleData: SelectedArticleData
        ) : Call<SelectedArticleResponse>
    }
    interface NickNameCheckApi{
        @GET("users/signup/checkNickname?nickName=")
        fun getNicknameOverlap(
            @Query("nickName") nickName:String
        ) : Call<NickNameCheckResponse>
    }

    interface IdCheckApi{
        @GET("users/signup/checkId?id=")
        fun getIdOverlap(
            @Query("id") id:String
        ) : Call<IdCheckResponse>
    }
}