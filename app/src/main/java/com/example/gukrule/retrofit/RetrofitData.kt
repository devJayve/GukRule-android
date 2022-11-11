package com.example.gukrule.retrofit

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
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
    var isSuccess: String? = null,
    var code: Int? = null,
    var message: String? = null,
    var result: List<List<String>>? = null,
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