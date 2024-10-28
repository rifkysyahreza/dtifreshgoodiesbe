package com.example.FreshGoodiesBackEnd.cart;

import com.example.FreshGoodiesBackEnd.cart.entity.CartItem;
import com.example.FreshGoodiesBackEnd.cart.service.CartService;
import com.example.FreshGoodiesBackEnd.product.entity.Product;
import com.example.FreshGoodiesBackEnd.product.service.ProductService;
import com.example.FreshGoodiesBackEnd.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService service, ProductService productService) {
        this.cartService = service;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Response<List<CartItem>>> getCartItem() {
        List<CartItem> cartItems = cartService.getAll();

        if (cartItems.isEmpty()) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Cart is empty");
        }

        return Response.successfulResponse("Cart item list", cartItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CartItem>> getCartItemById(@PathVariable String id) {
        CartItem foundCartItem = cartService.getById(id);

        if (foundCartItem == null) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Cart item not found");
        }

        return Response.successfulResponse("Cart item", foundCartItem);
    }

    @PostMapping
    public ResponseEntity<Response<CartItem>> createCartItem(@RequestBody CartItem newCartItem) {
        CartItem createdCartItem = cartService.create(newCartItem);
        Product checkProduct = productService.getById(newCartItem.getProductId());

        if (newCartItem.getQuantity() < 0){
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Quantity must be a positive number");
        }

        if (checkProduct == null) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Product not found");
        }

        return Response.successfulResponse(HttpStatus.CREATED.value(), "Cart item", createdCartItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CartItem>> updateCartItem(@PathVariable String id, @RequestBody CartItem updatedCartItem) {
        CartItem foundCartItem = cartService.getById(id);

        if (foundCartItem == null) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Cart item not found");
        }

        if (updatedCartItem.getId() != null) {
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Id cannot be updated");
        }

        if (updatedCartItem.getProductId() != null) {
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Product id cannot be updated");
        }

        if (updatedCartItem.getQuantity() == null || updatedCartItem.getQuantity() < 0) {
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Quantity must be a positive number");
        }

        CartItem updatedCartItemResult = cartService.update(id, updatedCartItem);
        return Response.successfulResponse(HttpStatus.OK.value(), "Cart item updated", updatedCartItemResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<CartItem>> deleteCartItem(@PathVariable String id) {
        CartItem deletedCartitem = cartService.getById(id);

        if (deletedCartitem == null) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Cart item not found");
        }

        cartService.delete(id);
        return Response.successfulResponse(HttpStatus.OK.value(), "Cart item deleted", deletedCartitem);
    }
}
