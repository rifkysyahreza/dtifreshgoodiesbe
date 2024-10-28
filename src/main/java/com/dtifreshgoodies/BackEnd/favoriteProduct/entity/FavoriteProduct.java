package com.dtifreshgoodies.BackEnd.favoriteProduct.entity;

import com.dtifreshgoodies.BackEnd.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteProduct {
    private Long productId;
    private Boolean isFavorite;
    private Product productDetails;
}
