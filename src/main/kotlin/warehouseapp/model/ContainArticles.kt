package warehouseapp.model

import com.google.gson.annotations.SerializedName

data class ContainArticles(
    @SerializedName("art_id") val artId: String,
    @SerializedName("amount_of") val amountOf: String? = null
)
