package com.example.FreshGoodiesBackEnd.favoriteProduct.entity;

import com.example.FreshGoodiesBackEnd.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteProduct {
    private Long productId;
    private Boolean isFavorite;
    private Product productDetails;
}
