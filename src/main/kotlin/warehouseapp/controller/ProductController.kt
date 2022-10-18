package warehouseapp.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import warehouseapp.model.Product
import warehouseapp.service.ProductService

@RestController
@RequestMapping("warehouse")
class ProductController(private val productService: ProductService) {

    @GetMapping("/available-products")
    fun getAvailableProducts(): ResponseEntity<*> {
        val availableProducts = productService.getAvailableProducts()
        return if (availableProducts.size > 0) {
            ResponseEntity.ok(availableProducts)
        } else ResponseEntity("No available products.", HttpStatus.NO_CONTENT)
    }

    @PutMapping("/sell-product")
    fun sellProduct(@RequestParam id: Int): ResponseEntity<*> {
        val product: Product? = productService.sellProduct(id)
        return if (product == null) {
            ResponseEntity("Product not found.", HttpStatus.NOT_FOUND)
        } else if (product.isOutOfStock) {
            ResponseEntity("Out of stock.", HttpStatus.NO_CONTENT)
        } else ResponseEntity.ok(product)
    }
}
