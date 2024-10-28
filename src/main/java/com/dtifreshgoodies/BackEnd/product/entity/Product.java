package com.example.FreshGoodiesBackEnd.product.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private double price;
    private String Description;
}
