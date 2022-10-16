package models

import com.google.gson.annotations.SerializedName

data class ContainArticles(
    @SerializedName("art_id") var artId: String? = null,
    @SerializedName("amount_of") var amountOf: String? = null
)
