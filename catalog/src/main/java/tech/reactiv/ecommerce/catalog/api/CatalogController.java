package tech.reactiv.ecommerce.catalog.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reactiv.ecommerce.catalog.addproduct.AddProductCommand;
import tech.reactiv.ecommerce.catalog.lookupproduct.LookupProductRequest;
import tech.reactiv.ecommerce.catalog.product.views.ProductView;
import tech.reactiv.ecommerce.catalog.schedulepromotion.SchedulePromotionCommand;
import tech.reactiv.ecommerce.catalog.search.SearchCatalogRequest;
import tech.reactiv.ecommerce.shared.mediator.Mediator;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final Mediator mediator;

    public CatalogController(Mediator mediator) {
        this.mediator = mediator;
    }

    @GetMapping("products")
    public ResponseEntity<List<ProductView>> allProducts() {
        return ResponseEntity.ok(mediator.query(new SearchCatalogRequest()));
    }

    @PostMapping("products")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody AddProductCommand command) {
        mediator.command(command);
        return ResponseEntity.created(URI.create("/catalog/products/" + command.productId())).build();
    }

    @GetMapping("products/{id}")
    public ResponseEntity<ProductView> lookupProduct(@PathVariable String id) {
        var productView = mediator.query(new LookupProductRequest(UUID.fromString(id)));
        return productView.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("promotions")
    public ResponseEntity<Void> schedulePromotion(@Valid @RequestBody SchedulePromotionCommand command) {
        mediator.command(command);
        return ResponseEntity.created(URI.create("/catalog/promotions/" + command.promotionId())).build();
    }

}
