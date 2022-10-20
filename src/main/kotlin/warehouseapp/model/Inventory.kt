package warehouseapp.model

import com.google.gson.annotations.SerializedName

data class InventoryRoot(
    @SerializedName("inventory") val inventory: ArrayList<Inventory> = arrayListOf()
)

data class Inventory(
    @SerializedName("art_id") val artId: String,
    @SerializedName("name") val name: String? = null,
    @SerializedName("stock") var stock: String? = null
)
