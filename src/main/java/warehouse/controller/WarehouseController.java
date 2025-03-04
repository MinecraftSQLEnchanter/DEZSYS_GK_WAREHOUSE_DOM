package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warehouse.model.ProductData;
import warehouse.model.Warehouse;
import warehouse.repository.ProductRepository;
import warehouse.repository.WarehouseRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WarehouseController {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/warehouse")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @GetMapping("/warehouse")
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @GetMapping("/warehouse/{id}")
    public Warehouse getWarehouseById(@PathVariable String id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/warehouse/{id}")
    public void deleteWarehouse(@PathVariable String id) {
        warehouseRepository.deleteById(id);
    }

    @PostMapping("/product")
    public ProductData addProduct(@RequestBody ProductData productData) {
        return productRepository.save(productData);
    }

    @GetMapping("/product")
    public List<ProductData> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/product/{id}")
    public ProductData getProductById(@PathVariable String id) {
        return productRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
    }
}