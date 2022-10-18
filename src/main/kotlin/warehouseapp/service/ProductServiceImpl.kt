package warehouseapp.service

import org.springframework.stereotype.Service
import warehouseapp.model.ContainArticles
import warehouseapp.model.Inventory
import warehouseapp.model.InventoryRoot
import warehouseapp.model.Product
import warehouseapp.model.ProductsRoot
import warehouseapp.utils.FileOperations.Companion.inventoryFile
import warehouseapp.utils.FileOperations.Companion.productsFile
import warehouseapp.utils.FileOperations.Companion.readInventoryFromFile
import warehouseapp.utils.FileOperations.Companion.readProductsFromFile
import warehouseapp.utils.FileOperations.Companion.writeObjectToFile

@Service
class ProductServiceImpl : ProductService {

    override fun getAvailableProducts(): ArrayList<Product> {
        val availableProducts: ArrayList<Product> = arrayListOf()
        val productsRoot: ProductsRoot = readProductsFromFile()
        val inventoryRoot: InventoryRoot = readInventoryFromFile()
        for (product: Product in productsRoot.products) {
            var isAvailable = false
            for (inventory: Inventory in inventoryRoot.inventory) {
                for (containArticle: ContainArticles in product.containArticles) {
                    isAvailable = (inventory.stock?.toInt()?.minus(containArticle.amountOf?.toInt()!!))!! > 0
                    if (!isAvailable) break
                }
                if (isAvailable) {
                    availableProducts.add(product)
                } else continue
            }
        }
        return availableProducts
    }

    override fun sellProduct(id: Int): Product? {
        val productsFile = productsFile
        val inventoryFile = inventoryFile
        val productsRoot: ProductsRoot = readProductsFromFile()
        val inventoryRoot: InventoryRoot = readInventoryFromFile()
        val products = productsRoot.products
        val product: Product = products.firstOrNull { it.id == id } ?: return null
        if (!product.isOutOfStock) {
            var isAvailable = false
            for (inventory: Inventory in inventoryRoot.inventory) {
                for (containArticle: ContainArticles in product.containArticles) {
                    if (inventory.artId == containArticle.artId) {
                        isAvailable = (inventory.stock?.toInt()?.minus(containArticle.amountOf?.toInt()!!))!! > 0
                        if (isAvailable) {
                            inventory.stock = (inventory.stock?.toInt()?.minus(containArticle.amountOf?.toInt()!!)).toString()
                        } else continue
                    }
                }
            }
            if (isAvailable) product.isOutOfStock = true
            writeObjectToFile(productsRoot, productsFile)
            writeObjectToFile(inventoryRoot, inventoryFile)
        }
        return product
    }
}
