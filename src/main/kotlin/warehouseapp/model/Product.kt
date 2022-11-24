package warehouseapp.model

import com.google.gson.annotations.SerializedName
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

data class ProductsRoot(
    @SerializedName("products") private val products: ArrayList<Product> = arrayListOf()
)

@Document("products")
data class Product(
    @SerializedName("product_id") @Id
    val productId: String,
    @SerializedName("name") private val name: String? = null,
    @SerializedName("is_out_of_stock") var isOutOfStock: Boolean = false,
    @SerializedName("contain_articles") @DBRef
    val containArticles: List<ContainArticles> = listOf()
)
