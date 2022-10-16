package warehouseapp.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.stereotype.Service
import warehouseapp.model.ContainArticles
import warehouseapp.model.Inventory
import warehouseapp.model.InventoryRoot
import warehouseapp.model.Product
import warehouseapp.model.ProductsRoot
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

@Service
class ProductServiceImpl : ProductService {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val productsJsonString: String = File("./src/main/resources/products.json").readText(Charsets.UTF_8)
    private val inventoryJsonString: String = File("./src/main/resources/inventory.json").readText(Charsets.UTF_8)

    override fun getAvailableProducts(): ArrayList<Product> {
        val productsRoot: ProductsRoot?
        val inventoryRoot: InventoryRoot?
        val availableProducts: ArrayList<Product> = arrayListOf()
        try {
            productsRoot = gson.fromJson(productsJsonString, ProductsRoot::class.java)
            inventoryRoot = gson.fromJson(inventoryJsonString, InventoryRoot::class.java)

            for (product: Product in productsRoot.products) {
                var isAvailable = false
                for (inventory: Inventory in inventoryRoot.inventory) {
                    for (containArticle: ContainArticles in product.containArticles) {
                        isAvailable =
                            containArticle.amountOf?.toInt()?.let { inventory.stock?.toInt()?.minus(it) }!! > 0
                        if (!isAvailable) break
                    }
                    if (isAvailable) {
                        availableProducts.add(product)
                    } else continue
                }
            }
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException("File not found.")
        } catch (e: IOException) {
            throw IOException("General I/O exception: " + e.message)
        }
        return availableProducts
    }
}
