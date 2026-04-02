package tech.reactiv.ecommerce.catalog.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.reactiv.ecommerce.shared.mediator.Mediator;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final Mediator mediator;

    public CatalogController(Mediator mediator) {
        this.mediator = mediator;
    }
}
