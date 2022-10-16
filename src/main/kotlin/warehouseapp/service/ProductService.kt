package warehouseapp.service

import warehouseapp.model.Product

interface ProductService {
    fun getAvailableProducts(): ArrayList<Product>
}
