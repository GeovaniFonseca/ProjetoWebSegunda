package com.orders.api.repository;

import com.orders.api.entity.Product;
import com.orders.api.entity.dto.OrderResponse;
import com.orders.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query("SELECT new com.orders.api.entity.dto.OrderResponse(c.id, c.name, c.email, p.productName, p.qty, p.price) FROM Customer c JOIN c.products p")
    public List<OrderResponse> getJoinInformation();



    public List<Customer> findByName(String name);
}



