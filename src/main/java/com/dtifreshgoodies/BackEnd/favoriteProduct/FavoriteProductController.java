package com.example.FreshGoodiesBackEnd.favoriteProduct;

import com.example.FreshGoodiesBackEnd.favoriteProduct.entity.FavoriteProduct;
import com.example.FreshGoodiesBackEnd.favoriteProduct.service.FavoriteProductService;
import com.example.FreshGoodiesBackEnd.product.entity.Product;
import com.example.FreshGoodiesBackEnd.product.service.ProductService;
import com.example.FreshGoodiesBackEnd.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteProductController {

    private final FavoriteProductService favoriteProductService;
    private ProductService productService = new ProductService();

    public FavoriteProductController(FavoriteProductService service, ProductService productService) {
        this.favoriteProductService = service;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Response<List<FavoriteProduct>>> getFavoriteProduct() {
        List<FavoriteProduct> favoriteProducts = favoriteProductService.getAll();

        if (favoriteProducts.isEmpty()) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Favorite product is empty");
        }

        return Response.successfulResponse("Favorite product list", favoriteProducts);
    }

    @PostMapping("/toggle")
    public ResponseEntity<Response<FavoriteProduct>> toggleFavorite(@RequestBody FavoriteProduct toggleFavoriteProduct) {
        Long productId = toggleFavoriteProduct.getProductId();
        Product checkProduct = productService.getById(productId);

        if (checkProduct == null) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Product not found");
        }

        FavoriteProduct favoriteProduct = favoriteProductService.toggleFavorite(productId);

        if (favoriteProduct.getIsFavorite()) {
            return Response.successfulResponse("Favorite product", favoriteProduct);
        }

        return Response.successfulResponse("UnFavorite product", favoriteProduct);

    }
}
