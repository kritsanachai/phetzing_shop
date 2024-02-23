package com.example.project.phetzing_shop.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.project.phetzing_shop.model.Order;
public interface OrderRepository extends CrudRepository<Order,Integer>{
    
}
