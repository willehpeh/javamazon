package tech.reactiv.ecommerce.catalog.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reactiv.ecommerce.catalog.addproduct.AddProductCommand;
import tech.reactiv.ecommerce.shared.mediator.Mediator;

import java.net.URI;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final Mediator mediator;

    public CatalogController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("products")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody AddProductCommand command) {
        mediator.command(command);
        return ResponseEntity.created(URI.create("/catalog/products/" + command.productId())).build();
    }
}
