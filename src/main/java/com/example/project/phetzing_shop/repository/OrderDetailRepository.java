package com.example.project.phetzing_shop.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.project.phetzing_shop.model.Order;
import com.example.project.phetzing_shop.model.OrderDetail;
import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,Integer>{
    List<OrderDetail> findByOrder(Order order);
}
