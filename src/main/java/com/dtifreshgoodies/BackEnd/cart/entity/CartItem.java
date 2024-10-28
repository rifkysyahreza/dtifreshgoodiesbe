package com.example.FreshGoodiesBackEnd.cart.entity;

import com.example.FreshGoodiesBackEnd.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private String id;
    private Long productId;
    private Integer quantity;
    private Product product;
}
