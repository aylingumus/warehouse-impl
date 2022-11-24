package warehouseapp.service

import org.springframework.stereotype.Service
import warehouseapp.model.ContainArticles
import warehouseapp.model.Inventory
import warehouseapp.model.Product
import warehouseapp.repository.InventoryRepository
import warehouseapp.repository.ProductRepository

@Service
class ProductServiceImpl(
    private val inventoryRepository: InventoryRepository,
    private val productRepository: ProductRepository
) : ProductService {

    override fun getAvailableProducts(): ArrayList<Product> {
        val availableProducts: ArrayList<Product> = arrayListOf()
        val productsRoot: MutableList<Product> = productRepository.findAll()
        val inventoryRoot: MutableList<Inventory> = inventoryRepository.findAll()
        // I created "isOutOfStock" property to check which products are in stock or not
        // Then I check the availability of articles for each product
        for (product: Product in productsRoot.filter { !it.isOutOfStock }) {
            var isAvailable = false
            for (inventory: Inventory in inventoryRoot) {
                for (containArticle: ContainArticles in product.containArticles) {
                    if (inventory.artId == containArticle.artId) {
                        isAvailable = (inventory.stock?.toInt()?.minus(containArticle.amountOf?.toInt()!!))!! >= 0
                    } else continue
                }
            }
            if (isAvailable) {
                availableProducts.add(product)
            } else continue
        }
        return availableProducts
    }

    override fun sellProduct(id: String): Product? {
        val productsRoot: MutableList<Product> = productRepository.findAll()
        val inventoryRoot: MutableList<Inventory> = inventoryRepository.findAll()
        val product: Product = productsRoot.firstOrNull { it.productId == id } ?: return null
        if (!product.isOutOfStock) {
            var isAvailable = false
            for (inventory: Inventory in inventoryRoot) {
                for (containArticle: ContainArticles in product.containArticles) {
                    if (inventory.artId == containArticle.artId) {
                        isAvailable = (inventory.stock?.toInt()?.minus(containArticle.amountOf?.toInt()!!))!! >= 0
                        if (isAvailable) {
                            inventory.stock =
                                (inventory.stock?.toInt()?.minus(containArticle.amountOf?.toInt()!!)).toString()
                            inventoryRepository.save(inventory)
                        } else continue
                    }
                }
            }
            if (!isAvailable) product.isOutOfStock = true
            productRepository.save(product)
        }
        return product
    }
}
