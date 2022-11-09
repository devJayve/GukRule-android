package com.example.gukrule.retrofit

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

    data class DetailBudgetList(
        var njzofberazvhjncha: List<JsonObject>
    )

data class JsonRowList(
    var row: List<JsonObject>
)

data class DetailBudgetData(
        @SerializedName("FSCL_YY") val fsclYY:String?,       // 회계년도
        @SerializedName("EXP_DATE") val exeDate:String?,     // 집행일
        @SerializedName("FSCL_NM") val fsclName:String?,     // 회계명
        @SerializedName("FLD_NM") val fldName:String?,       // 분야명
        @SerializedName("SECT_NM") val sectName:String?,     // 부문명
        @SerializedName("PGM_NM") val pgmName:String?,       // 프로그램명
        @SerializedName("ACTV_NM") val actvName:String?,     // 단위사업명
        @SerializedName("SACTV_NM") val sactvName:String?,   // 분야명
        @SerializedName("ANEXP_BDGAMT") val annualExpBdgAmt:Int?,            // 부문명
        @SerializedName("ANEXP_BDG_CAMT") val annualExpBdgCurrentAmt:Int?,   // 프로그램명
        @SerializedName("EP_AMT") val expenseAmt:Int?,                       // 단위사업명
        @SerializedName("THISM_AGGR_EP_AMT") val aggExpenseAmt:Int?,         // 단위사업명
        @SerializedName("THISM_AGGR_EP_NAMT") val aggExpenseNetAmt:Int?,     // 단위사업명
    )

data class CrawlingRequestData(
    var keyword : String,
    var page : Int
)

data class CrawlingNewList(
    var isSuccess : String,
    var code : Int,
    var message : String,
    var result : List<List<String>>
)

data class LoginData(
    var id : String,
    var pw : String
)

data class LoginResponse(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : UserInfo
)

data class UserInfo(
    var id : String,
    var token : Int
)

data class RegisterData(
    var id : String,
    var pw : String,
    var name : String,
    var nickname : String,
    var email : String,
    var keyword1 : String,
    var keyword2 : String,
    var keyword3 : String,
    var keyword4 : String,
    var keyword5 : String,

)

data class RegisterResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result : RegisterToken,
    var userIdx : Int
)

data class RegisterToken(
    var jwt: String
)

data class SelectedArticleData(
    var title : String,
    var selected: Boolean,
)