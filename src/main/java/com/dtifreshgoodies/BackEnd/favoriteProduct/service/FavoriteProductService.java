package com.example.FreshGoodiesBackEnd.favoriteProduct.service;

import com.example.FreshGoodiesBackEnd.cart.entity.CartItem;
import com.example.FreshGoodiesBackEnd.favoriteProduct.entity.FavoriteProduct;
import com.example.FreshGoodiesBackEnd.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FavoriteProductService {

    private ProductService productService = new ProductService();

    public FavoriteProductService(ProductService productService) {
        this.productService = productService;
    }

    // DB repository mock
    private Map<Long, FavoriteProduct> repository = Arrays.stream(
            new FavoriteProduct[] {
                    new FavoriteProduct(){{
                        setProductId(1L);
                        setIsFavorite(true);
                        setProductDetails(productService.getById(getProductId()));
                    }},
                    new FavoriteProduct(){{
                        setProductId(2L);
                        setIsFavorite(false);
                        setProductDetails(productService.getById(getProductId()));
                    }},
            }
    ).collect(Collectors.toConcurrentMap(FavoriteProduct::getProductId, favoriteProduct -> favoriteProduct));

    public List<FavoriteProduct> getAll() {
        return repository.values().stream()
                .filter(FavoriteProduct::getIsFavorite)
                .collect(Collectors.toList());
    }

    public FavoriteProduct toggleFavorite(Long productId) {
        FavoriteProduct favoriteProduct = repository.get(productId);
        favoriteProduct.setIsFavorite(!favoriteProduct.getIsFavorite());
        repository.put(productId, favoriteProduct);
        return favoriteProduct;
    }
}
