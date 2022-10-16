package warehouseapp.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import warehouseapp.service.ProductService

@RestController
class ProductController(private val productService: ProductService) {

    @GetMapping("/available-products")
    fun getAvailableProducts(): ResponseEntity<*> {
        val availableProducts = productService.getAvailableProducts()
        return if (availableProducts.size > 0) {
            ResponseEntity.ok(availableProducts)
        } else ResponseEntity("No available products", HttpStatus.NO_CONTENT)
    }
}
