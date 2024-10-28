package com.example.FreshGoodiesBackEnd.product.service;

import com.example.FreshGoodiesBackEnd.product.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ProductService {

    // DB repository mock
    private Map<Long, Product> repository = Arrays.stream(
            new Product[] {
                    new Product(){{
                        setId(1L);
                        setName("Apple");
                        setPrice(100.0);
                        setDescription("Fresh apple");
                    }},
                    new Product(){{
                        setId(2L);
                        setName("Banana");
                        setPrice(50.0);
                        setDescription("Fresh banana");
                    }},
            }
    ).collect(Collectors.toConcurrentMap(Product::getId, product -> product));

    // DB id sequence mock
    private AtomicLong sequence = new AtomicLong(2);

    public List<Product> getAll() {
        return new ArrayList<>(repository.values());
    }

    public Product getById(Long id) {
        return repository.get(id);
    }

    public Product create(Product newProduct) {
        Long key = sequence.incrementAndGet();
        newProduct.setId(key);
        repository.put(key, newProduct);
        return newProduct;
    }

    public Product update(Long id, Product updatedProduct) {
        updatedProduct.setId(id);
        repository.put(id, updatedProduct);
        return updatedProduct;
    }

    public void delete(Long id){
        repository.remove(id);
    }
}
