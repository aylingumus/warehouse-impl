package warehouseapp.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import warehouseapp.model.InventoryRoot
import warehouseapp.model.ProductsRoot
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.Exception

class FileOperations {

    companion object {
        private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        private const val productsPath: String = "./src/main/resources/products.json"
        private const val inventoryPath: String = "./src/main/resources/inventory.json"
        val productsFile = File(productsPath)
        val inventoryFile = File(inventoryPath)

        fun readProductsFromFile(): ProductsRoot {
            try {
                val jsonString: String = productsFile.readText(Charsets.UTF_8)
                return gson.fromJson(jsonString, ProductsRoot::class.java)
            } catch (e: JsonSyntaxException) {
                throw JsonSyntaxException("JSON syntax is invalid.")
            } catch (e: Exception) {
                throw Exception("Error message: " + e.message)
            }
        }

        fun readInventoryFromFile(): InventoryRoot {
            try {
                val jsonString: String = inventoryFile.readText(Charsets.UTF_8)
                return gson.fromJson(jsonString, InventoryRoot::class.java)
            } catch (e: JsonSyntaxException) {
                throw JsonSyntaxException("JSON syntax is invalid.")
            } catch (e: Exception) {
                throw Exception("Error message: " + e.message)
            }
        }

        fun writeObjectToFile(root: Any, file: File) {
            val currentJsonString: String
            try {
                currentJsonString = gson.toJson(root)
                file.writeText(currentJsonString, Charsets.UTF_8)
            } catch (e: FileNotFoundException) {
                throw FileNotFoundException("File not found.")
            } catch (e: IOException) {
                throw IOException("General I/O exception: " + e.message)
            }
        }
    }
}
