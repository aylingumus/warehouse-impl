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
    private val productsRoot = gson.fromJson(productsJsonString, ProductsRoot::class.java)
    private val inventoryRoot = gson.fromJson(inventoryJsonString, InventoryRoot::class.java)

    override fun getAvailableProducts(): ArrayList<Product> {
        val availableProducts: ArrayList<Product> = arrayListOf()
        try {
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
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException("File not found.")
        } catch (e: IOException) {
            throw IOException("General I/O exception: " + e.message)
        }
        return availableProducts
    }

    override fun sellProduct(name: String): String {
        val currentInventoryJsonString: String
        val currentProductsJsonString: String
        val inventoryFile = File("./src/main/resources/inventory.json")
        val productsFile = File("./src/main/resources/products.json")
        try {
            val products = productsRoot.products
            val product: Product = products.first { it.name.equals(name) }
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
            if (isAvailable) {
                products.remove(product)
            }
            currentInventoryJsonString = gson.toJson(inventoryRoot)
            inventoryFile.writeText(currentInventoryJsonString, Charsets.UTF_8)

            currentProductsJsonString = gson.toJson(productsRoot)
            productsFile.writeText(currentProductsJsonString, Charsets.UTF_8)
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException("File not found.")
        } catch (e: IOException) {
            throw IOException("General I/O exception: " + e.message)
        }
        return "Product sold."
    }
}
