package com.orders.api.service;

import com.orders.api.entity.Customer;
import com.orders.api.entity.Product;
import com.orders.api.entity.dto.OrderResponse;
import com.orders.api.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final CustomerRepository customerRepository;


    public OrderService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }

    public List<OrderResponse> getAllOrders() {
        List<Customer> customers = customerRepository.findAll();

        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Customer customer : customers) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(customer.getId());
            orderResponse.setName(customer.getName());
            orderResponse.setEmail(customer.getEmail());

            List<Product> products = customer.getProducts();

            List<OrderResponse.ProductInfo> productInfos = new ArrayList<>();
            for (Product product : products) {
                OrderResponse.ProductInfo productInfo = new OrderResponse.ProductInfo();
                productInfo.setProductName(product.getProductName());
                productInfo.setQty(product.getQty());
                productInfo.setPrice(product.getPrice());

                productInfos.add(productInfo);
            }

            orderResponse.setProducts(productInfos);
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }

    public Optional<Customer> getOrderById(int id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    public Customer createOrder(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> updateOrder(int id, Customer updatedOrder) {
        Optional<Customer> existingOrder = customerRepository.findById(id);
        if (existingOrder.isPresent()) {
            updatedOrder.setId(id);
            return Optional.of(customerRepository.save(updatedOrder));
        }
        return Optional.empty();
    }

    public boolean deleteOrder(int id) {
        Optional<Customer> order = customerRepository.findById(id);
        if (order.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

