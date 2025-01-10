package com.example.gukrule.retrofit

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class DetailBudgetList(
    var njzofberazvhjncha: List<JsonObject>
)

data class JsonRowList(
    var row: List<JsonObject>
)

data class Njzofberazvhjncha(
    val head: List<Head>,
    val row: ArrayList<Row>
)

data class Head(
    val RESULT: RESULT,
    val list_total_count: Int
)

data class RESULT(
    val CODE: String,
    val MESSAGE: String
)

data class Row(
    val ACTV_NM: String,
    val ANEXP_BDGAMT: Long,
    val ANEXP_BDG_CAMT: Long,
    val EP_AMT: Long,
    val EXE_DATE: String,
    val FLD_NM: String,
    val FSCL_NM: String,
    val FSCL_YY: String,
    val PGM_NM: String,
    val SACTV_NM: String,
    val SECT_NM: String,
    val THISM_AGGR_EP_AMT: Long,
    val THISM_AGGR_EP_NAMT: Long
)

data class BudgetResponseData(
    val njzofberazvhjncha: List<Njzofberazvhjncha>
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
        @SerializedName("ANEXP_BDGAMT") val annualExpBdgAmt:Int?,            //
        @SerializedName("ANEXP_BDG_CAMT") val annualExpBdgCurrentAmt:Int?,   //
        @SerializedName("EP_AMT") val expenseAmt:Int?,                       //
        @SerializedName("THISM_AGGR_EP_AMT") val aggExpenseAmt:Int?,         //
        @SerializedName("THISM_AGGR_EP_NAMT") val aggExpenseNetAmt:Int?,     //
    )


data class SummaryRequestData(
    var inputs: String,
)

data class SummaryArticle(
    var generatedList: List<SummaryText>
)

data class SummaryText(
    var generated_text: String
)

data class CrawlingRequestData(
    var userIdx : Int,
    var keyword : String?,
    var page : Int
)

// CrawlingNewsApi
data class CrawlingNewList(
    var isSuccess : String,
    var code : Int,
    var message : String,
    var result : List<List<String>>
)

data class LoginData(
    var id : String,
    var password : String
)

data class LoginResponse(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : UserInfo
)

data class UserInfo(
    var userIdx : Int,
    var jwt : String
)

data class RegisterData(
    var id : String,
    var password : String,
    var passwordForCheck: String,
    var phone : String,
    var email : String,
    var nickName : String,
)

data class RegisterResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result : RegisterToken,
)

data class RegisterToken(
    var jwt: String,
    var userIdx : Int,
    )

data class NickNameCheckResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: NickNameExists
)

data class NickNameExists(
    var exists : Boolean
)

data class IdCheckResponse(
    var isSuccess: Boolean,
    var code : Int,
    var message: String,
    var result : IdExists
)

data class IdExists(
    var exists: Boolean
)

data class SelectedArticleData(
    var userIdx : Int,
    var keyword1: String,
    var keyword2: String,
    var keyword3: String,
    var keyword4: String,
    var keyword5: String,
)

data class SelectedArticleResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: SelectedArticleJWT,
)

data class SelectedArticleJWT(
    var jwt: String,
    var userIdx: Int
)

data class CrawlingArticleRequestData(
    var userIdx: Int,
    var url: String,
    var keyword: String
)


// CrawlingArticleApi
data class CrawlingArticle(
    var isSuccess: String,
    var code: Int,
    var message: String,
    var result: ArticleResult,
)

data class ArticleResult(
    var date: String? = null,
    var articleText: String? = null,
    var title: String? = null,
)

data class AccountData(
    var id : String,
    var phone : String
)

data class SmsResponse(
    var isSuccess: String,
    var code: Int,
    var message: String,
    var result : AuthData
)

data class AuthData(
    var authCode : String,
    var userIdx: Int
)

data class ModifyPwResponse(
    var isSuccess: String,
    var code: Int,
    var message: String,
    var result : UserData
)

data class PasswordData(
    var userIdx : Int,
    var password : String,
    var passwordForCheck : String
)

data class UserData(
    var userIdx: Int,
    var jwt : String
)