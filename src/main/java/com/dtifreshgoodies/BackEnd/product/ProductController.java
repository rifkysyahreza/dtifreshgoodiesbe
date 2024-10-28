package com.example.FreshGoodiesBackEnd.product;

import com.example.FreshGoodiesBackEnd.product.entity.Product;
import com.example.FreshGoodiesBackEnd.product.service.ProductService;
import com.example.FreshGoodiesBackEnd.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService service) {
        this.productService = service;
    }

    @GetMapping
    public ResponseEntity<Response<List<Product>>> getProduct(@RequestParam(required = false) String name, @RequestParam(required = false) String description) {

        if (name != null && description != null) {
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "You can only search by name or description");
        }

        if (description != null) {
            return Response.successfulResponse("Product list", productService.getAll().stream().filter(product -> product.getDescription().toLowerCase().contains(description.toLowerCase())).toList());
        }

        if (name != null) {
            return Response.successfulResponse("Product list", productService.getAll().stream().filter(product -> product.getName().toLowerCase().contains(name.toLowerCase())).toList());
        }

        return Response.successfulResponse("Product list", productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Optional<Product>>> getProductById(@PathVariable Long id) {
        Product foundProduct = productService.getById(id);

        if (foundProduct == null) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Product not found");
        }

        return Response.successfulResponse("Product", Optional.ofNullable(productService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<Response<Product>> createProduct(@RequestBody Product newProduct) {
        Product createdProduct = productService.create(newProduct);

        return Response.successfulResponse(HttpStatus.CREATED.value(),"Product created", createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Product>> updatedProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product oldProduct = productService.getById(id);

        if (oldProduct == null) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Product not found");
        }

        Product updated = productService.update(id, updatedProduct);
        return Response.successfulResponse(HttpStatus.OK.value(),"Product updated", updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Product>> deleteProduct(@PathVariable Long id) {
        Product foundProduct = productService.getById(id);

        if (foundProduct == null) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Product not found");
        }

        productService.delete(id);
        return Response.successfulResponse(HttpStatus.OK.value(), "This product successfully deleted", foundProduct);
    }
}
