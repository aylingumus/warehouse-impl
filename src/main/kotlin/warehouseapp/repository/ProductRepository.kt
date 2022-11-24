package warehouseapp.repository

import org.springframework.data.mongodb.repository.MongoRepository
import warehouseapp.model.Product

interface ProductRepository : MongoRepository<Product, String>
