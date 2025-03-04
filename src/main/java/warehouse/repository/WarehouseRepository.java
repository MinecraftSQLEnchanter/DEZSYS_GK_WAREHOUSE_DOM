package warehouse.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import warehouse.model.Warehouse;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
    Warehouse findByName(String name);
}