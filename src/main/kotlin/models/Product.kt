package models

import com.google.gson.annotations.SerializedName

data class ProductsRoot(
    @SerializedName("products") var products: ArrayList<Product> = arrayListOf()
)

data class Product(
    @SerializedName("name") var name: String? = null,
    @SerializedName("contain_articles") var containArticles: ArrayList<ContainArticles> = arrayListOf()
)
