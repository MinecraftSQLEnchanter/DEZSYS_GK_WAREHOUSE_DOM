package warehouse.model;

import org.springframework.data.annotation.Id;
import java.util.List;

public class Warehouse {

    @Id
    private String id;
    private String name;
    private List<ProductData> products;

    public Warehouse() {}

    public Warehouse(String id, String name, List<ProductData> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    // Getter und Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductData> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return String.format("Warehouse[id=%s, name=%s, products=%s]", id, name, products);
    }
}