package tech.reactiv.ecommerce.catalog;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import tech.reactiv.ecommerce.catalog.addproduct.AddProductCommand;
import tech.reactiv.ecommerce.catalog.product.ProductNotFoundException;
import tech.reactiv.ecommerce.shared.mediator.Mediator;

import java.net.URI;
import java.util.Map;

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

@ControllerAdvice
class CatalogExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }
}