package com.store.service;

import com.store.model.Customer;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class CustomerService {
    private List<Customer> customers = new ArrayList<>();

    public void loadCustomers(String filename) {
        try (Stream<String> lines = Files.lines(Path.of(filename))) {
            customers = lines
                .map(line -> line.split(","))
                .map(arr -> new Customer(
                    Integer.parseInt(arr[0].trim()),
                    arr[1].trim(),
                    arr[2].trim()
                ))
                .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error loading customers: " + e);
        }
    }

    public List<Customer> listAll() { return customers; }

    public Customer getCustomerById(int id) {
        return customers.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public void addCustomer(Customer customer) { customers.add(customer); }

    public void deleteCustomer(int id) {
        customers.removeIf(c -> c.getId() == id);
    }
}