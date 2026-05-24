package com.store.service;

import com.store.model.*;
import java.util.*;

public class OrderService {
    private ProductService productService;
    private Map<Integer, List<CartItem>> carts = new HashMap<>();
    private List<Order> orders = new ArrayList<>();
    private int nextOrderId = 1;

    public OrderService(ProductService productService) {
        this.productService = productService;
    }

    public void addToCart(Customer customer, int productId, int quantity) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found: " + productId);
            return;
        }
        List<CartItem> cart = carts.computeIfAbsent(customer.getId(), k -> new ArrayList<>());
        for (CartItem item : cart) {
            if (item.getProduct().getId() == productId) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cart.add(new CartItem(product, quantity));
    }

    public void checkout(Customer customer) {
        List<CartItem> cart = carts.get(customer.getId());
        if (cart == null || cart.isEmpty()) {
            System.out.println("Cart is empty for " + customer.getName());
            return;
        }
        Order order = new Order(nextOrderId++, customer, new ArrayList<>(cart));
        orders.add(order);
        System.out.println("Order placed: " + order);
        cart.clear();
    }

    public List<Order> listOrders() { return orders; }
}