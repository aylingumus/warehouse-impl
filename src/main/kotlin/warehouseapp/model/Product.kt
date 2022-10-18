package warehouseapp.model

import com.google.gson.annotations.SerializedName

data class ProductsRoot(
    @SerializedName("products") var products: ArrayList<Product> = arrayListOf()
)

data class Product(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String? = null,
    @SerializedName("is_out_of_stock") var isOutOfStock: Boolean = false,
    @SerializedName("contain_articles") var containArticles: ArrayList<ContainArticles> = arrayListOf()
)
