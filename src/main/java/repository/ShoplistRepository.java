package repository;

import model.Shoplist;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShoplistRepository extends MongoRepository<Shoplist, String> {
    // dodatkowe metody zapytan
}
