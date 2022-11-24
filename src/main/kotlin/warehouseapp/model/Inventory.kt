package warehouseapp.model

import com.google.gson.annotations.SerializedName
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class InventoryRoot(
    @SerializedName("inventory") private val inventory: ArrayList<Inventory> = arrayListOf()
)

@Document("inventory")
data class Inventory(
    @SerializedName("art_id") @Id
    val artId: String,
    @SerializedName("name") private val name: String? = null,
    @SerializedName("stock") var stock: String? = null
)
