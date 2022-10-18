package warehouseapp.model

import com.google.gson.annotations.SerializedName

data class InventoryRoot(
    @SerializedName("inventory") var inventory: ArrayList<Inventory> = arrayListOf()
)

data class Inventory(
    @SerializedName("art_id") var artId: String,
    @SerializedName("name") var name: String? = null,
    @SerializedName("stock") var stock: String? = null
)
