package com.dtifreshgoodies.BackEnd.cart.entity;

import com.dtifreshgoodies.BackEnd.product.entity.Product;
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
