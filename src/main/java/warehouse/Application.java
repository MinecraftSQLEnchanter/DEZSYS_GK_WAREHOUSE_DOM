package warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import warehouse.model.ProductData;
import warehouse.repository.ProductRepository; // Import hinzufügen
import warehouse.repository.WarehouseRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private WarehouseRepository warehouseRepository; // Für Warehouse-Objekte

	@Autowired
	private ProductRepository productRepository; // Für ProductData-Objekte

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Initialize product data repository
		productRepository.deleteAll();

		// Speichern einiger Produktdaten
		productRepository.save(new ProductData("1", "00-443175", "Bio Orangensaft Sonne", "Getraenk", 2500));
		productRepository.save(new ProductData("1", "00-871895", "Bio Apfelsaft Gold", "Getraenk", 3420));
		productRepository.save(new ProductData("1", "01-926885", "Ariel Waschmittel Color", "Waschmittel", 478));
		productRepository.save(new ProductData("1", "02-234811", "Mampfi Katzenfutter Rind", "Tierfutter", 1324));
		productRepository.save(new ProductData("2", "03-893173", "Saugstauberbeutel Ingres", "Reinigung", 7390));
		System.out.println();

		// Alle Produkte abrufen
		System.out.println("ProductData found with findAll():");
		System.out.println("-------------------------------");
		for (ProductData productdata : productRepository.findAll()) {
			System.out.println(productdata);
		}
		System.out.println();

		// Einzelnes Produkt abrufen
		System.out.println("Record(s) found with ProductID(\"00-871895\"):");
		System.out.println("--------------------------------");
		System.out.println(productRepository.findByProductID("00-871895"));
		System.out.println();

		// Alle Produkte in Lager 1 abrufen
		System.out.println("Record(s) found with findByWarehouseID(\"1\"):");
		System.out.println("--------------------------------");
		for (ProductData productdata : productRepository.findByWarehouseID("1")) {
			System.out.println(productdata);
		}
		System.out.println();

		// Alle Produkte in Lager 2 abrufen
		System.out.println("Record(s) found with findByWarehouseID(\"2\"):");
		System.out.println("--------------------------------");
		for (ProductData productdata : productRepository.findByWarehouseID("2")) {
			System.out.println(productdata);
		}
	}
}