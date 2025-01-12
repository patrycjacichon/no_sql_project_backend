package controller;

import model.Shoplist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ShoplistRepository;

import java.util.List;

@RestController
@RequestMapping("/api/shoplist")
public class ShoplistController {

    @Autowired
    private ShoplistRepository shoplistRepository;

    @PostMapping
    public ResponseEntity<Shoplist> addShoplist(@RequestBody Shoplist shoplist) {
        if (shoplist.getItem() == null || shoplist.getItem().isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // blad, jesli pole item jest puste
        }

        // Walidacja category (dodac jesli pusta to kategoria inne!)
        if (shoplist.getCategory() == null || shoplist.getCategory().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Walidacja quantity
        if (shoplist.getQuantity() == null) {
            shoplist.setQuantity(0);  // wartosc domyslna
        }

        // Zapisz przedmiot do bazy
        Shoplist savedShoplist = shoplistRepository.save(shoplist);
        return ResponseEntity.ok(savedShoplist);
    }

    @GetMapping
    public List<Shoplist> getShoplist() {
        return shoplistRepository.findAll();
    }

    @GetMapping("/category/{category}")
    public List<Shoplist> getShoplistByCategory(@PathVariable String category) {
        return shoplistRepository.findAll().stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoplist(@PathVariable String id) {
        if (shoplistRepository.existsById(id)) {
            shoplistRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
