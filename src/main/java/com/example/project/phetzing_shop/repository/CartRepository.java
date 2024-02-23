package com.example.project.phetzing_shop.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.project.phetzing_shop.model.Cart;
import com.example.project.phetzing_shop.model.Member;
import java.util.List;

public interface CartRepository extends CrudRepository<Cart,Integer>{
    List<Cart> findByMember(Member member);
}
