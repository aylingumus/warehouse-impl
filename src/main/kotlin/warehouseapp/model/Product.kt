package warehouseapp.model

import com.google.gson.annotations.SerializedName

data class ProductsRoot(
    @SerializedName("products") val products: ArrayList<Product> = arrayListOf()
)

data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String? = null,
    @SerializedName("is_out_of_stock") var isOutOfStock: Boolean = false,
    @SerializedName("contain_articles") val containArticles: ArrayList<ContainArticles> = arrayListOf()
)
