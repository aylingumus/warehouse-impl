package warehouseapp.repository

import org.springframework.data.mongodb.repository.MongoRepository
import warehouseapp.model.Inventory

interface InventoryRepository : MongoRepository<Inventory, String>
