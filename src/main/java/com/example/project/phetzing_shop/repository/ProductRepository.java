package com.example.project.phetzing_shop.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.project.phetzing_shop.model.Product;
public interface ProductRepository extends CrudRepository<Product,Integer>{

}
