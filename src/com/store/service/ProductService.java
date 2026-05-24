package com.store.service;

import com.store.model.Product;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class ProductService {
    private List<Product> products = new ArrayList<>();

    public void loadProducts(String filename) {
        try (Stream<String> lines = Files.lines(Path.of(filename))) {
            products = lines
                .map(line -> line.split(","))
                .map(arr -> new Product(
                    Integer.parseInt(arr[0].trim()),
                    arr[1].trim(),
                    Double.parseDouble(arr[2].trim()),
                    Integer.parseInt(arr[3].trim())
                ))
                .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error loading products: " + e);
        }
    }

    public List<Product> listAll() { return products; }

    public Product getProductById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void addProduct(Product product) { products.add(product); }

    public void updateStock(int id, int newQuantity) {
        Product p = getProductById(id);
        if (p != null) p.setQuantity(newQuantity);
    }

    public void removeProduct(int id) {
        products.removeIf(p -> p.getId() == id);
    }

    public List<Product> searchByName(String keyword) {
        return products.stream()
            .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<Product> filterByPrice(double min, double max) {
        return products.stream()
            .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
            .collect(Collectors.toList());
    }
}