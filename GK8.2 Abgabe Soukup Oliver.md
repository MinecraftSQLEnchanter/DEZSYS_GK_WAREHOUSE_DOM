Als erstes wurde das Github proj runtergeladen und danach der mongodb container aufgesetzt via
```
docker pull mongo
docker run -d -p 27017 --name mongo mongo
docker exec -it mongo bash
mongosh
```

WarehouseController
Gemappt zu /product, das dann Daten ins Repository speichern kann und übergeben Parameter verwenden kann.
```
    @PostMapping("/product")  
    public ProductData addProduct(@RequestBody ProductData productData) {  
        return productRepository.save(productData);  
    }  
```


ProductData
Ethält Daten mit gegebener Struktur und kann sie mit der overriden toString method auch ausgeben.

```java
@Document(collection = "productdata")  
public class ProductData {  
  
    @Id  
    private String ID;  
  
    private String warehouseID;  
    private String productID;  
    private String productName;  
    private String productCategory;  
    private double productQuantity;  
   
    public ProductData() {}  
  
    public ProductData(String warehouseID, String productID, String productName, String productCategory, double productQuantity) {  
       this.warehouseID = warehouseID;  
       this.productID = productID;  
       this.productName = productName;  
       this.productCategory = productCategory;  
       this.productQuantity = productQuantity;  
    }  
  
  
  
    @Override  
    public String toString() {  
       return String.format(  
             "ProductData[ID=%s, warehouseID=%s, productID=%s, productName=%s, productCategory=%s, productQuantity=%s]",  
             ID, warehouseID, productID, productName, productCategory, productQuantity  
       );  
    }  
}
```
ProductRepository
```
package warehouse.repository;  
  
import org.springframework.data.mongodb.repository.MongoRepository;  
import warehouse.model.ProductData;  
  
import java.util.List;  
  
public interface ProductRepository extends MongoRepository<ProductData, String> {  
    List<ProductData> findByWarehouseID(String warehouseID);  
    ProductData findByProductID(String productID);  
}
```


Application
Füllt die DB mit Testdaten

```
@SpringBootApplication  
public class Application implements CommandLineRunner {  
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
```

# Code testen
## Shell Befehle
Produkte hinzufügen
```
db.productdata.insert({
    warehouseID: "1",
    productID: "00-443175",
    productName: "Bio Orangensaft Sonne",
    productCategory: "Getraenk",
    productQuantity: 2500
})
```

Produkt löschen
```
db.productdata.deleteOne({ productID: "00-443175" })
```
Produkt ändern
```
db.productdata.updateOne(
    { productID: "00-443175" },
    { $set: { productQuantity: 3000 } }
)
```

Produkte anzeigen
```
db.productdata.find()
```

Ein Produkt abfragen
```
db.productdata.findOne({ productID: "00-443175" })
```


Warehouses ansehen
```
db.warehouse.find()
```
# Ausarbeitung der Fragestellungen
- Nennen Sie 4 Vorteile eines NoSQL Repository im Gegensatz zu einem relationalen DBMS
	- Skalierbarkeit, in diesem Fall auch über mehrere Server
	- Flexibel, hier schemalose Daten
	- Hohe Leistung, lesen schreiben etc
	- Kann auch XML/JSON einfach speichern
	
- Nennen Sie 4 Nachteile eines NoSQL Repository im Gegensatz zu einem relationalen DBMS
	- Kein ACID (Atomarität, Konsistenz, Isolation, Dauerhaftigkeit)
	- Erschwerte komplexe Abfragen weil die Sprachen einfach nicht so gut sind.
	- Neuer daher weniger Doku
	- Priorisiert Verfügbarkeit und Parttoleranz
	
- Welche Schwierigkeiten ergeben sich bei der Zusammenführung der Daten?
	- Kann Inkonsistent zwischen Knoten und Datenbanken sein
	- Unterschiedliche Datenmodelle und Apis
	- Komplexere Datenintegration aufgrund von speziellen Tools
	- Redundante Daten werden auch gespeichert was vorallem das aktualisieren erschwert
	
	Nennen Sie einen Vertreter für jede Art?
- Welche Arten von NoSQL Datenbanken gibt es?
	- Dokumentorientiert
		- JSON/XML
		- MongoDB
	- Key Value
		- Schlüssel Werte Paar
		- Redis
	- Spaltenorientiert
		- Spaltenweise statt zeilenweise
		- Apache Cassandra
	- Graphdata
		- Knoten und Kanten
		- Neo4j

- Beschreiben Sie die Abkürzungen CA, CP und AP in Bezug auf das CAP Theorem
	- Cap
		- Consistency, alle Knoten sehen alle Daten gleichzeitig
		- Availability, jede Anfrage erhält eine Antwort auch bei Ausfällen
		- Partition Tolerance, auch ohne comm zwischen Knoten geht es weiter
	- CA
		- Keine Part Tolerance
	- CP
		- Keine Availability
	- AP
		- Keine Konsistenz
- Mit welchem Befehl koennen Sie den Lagerstand eines Produktes aller Lagerstandorte anzeigen.
	- in mongoshell
	- ```db.productdata.find({ productID: "00-443175" }, { warehouseID: 1, productQuantity: 1 })```
- Mit welchem Befehl koennen Sie den Lagerstand eines Produktes eines bestimmten Lagerstandortes anzeigen.
	- ```db.productdata.find({ productID: "00-443175", warehouseID: "1" }, { productQuantity: 1 })```