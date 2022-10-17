package warehouseapp.service

import warehouseapp.model.Product

interface ProductService {
    fun getAvailableProducts(): ArrayList<Product>
    fun sellProduct(name: String): String
}
