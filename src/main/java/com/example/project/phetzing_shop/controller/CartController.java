package com.example.project.phetzing_shop.controller;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.phetzing_shop.model.Cart;
import com.example.project.phetzing_shop.model.Member;
import com.example.project.phetzing_shop.model.Product;
import com.example.project.phetzing_shop.repository.MemberRepository;
import com.example.project.phetzing_shop.repository.CartRepository;
import com.example.project.phetzing_shop.repository.ProductRepository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private ProductRepository productRepo;


    @GetMapping("{memberId}")
    public Iterable<Cart> getByMemberId(@PathVariable int memberId) {
        return cartRepo.findByMember(null);
    }
    
    @Getter @Setter @NoArgsConstructor
    public static class requestCart{
        private int productId;
        private int qty;
    }

    @PostMapping("{memberId}")
    public ResponseEntity<Cart> create(@PathVariable int memberId,@RequestBody requestCart reCart) {

        Optional<Member> dataMember = memberRepo.findById(memberId);
        Optional<Product> dataProduct = productRepo.findById(reCart.getProductId());
        Cart cart = new Cart();

        Member member = dataMember.get();
        Product product = dataProduct.get();
        cart.setMember(member);
        cart.setProduct(product);

        Cart newCart = cartRepo.save(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCart);
    }



    @SuppressWarnings("null")
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable int id) {

        Optional<Cart> optionalCart = cartRepo.findById(id);
        Map<String, String> responseMap = new HashMap<>();

        if (optionalCart.isPresent()) {
            cartRepo.delete(optionalCart.get());
            responseMap.put("message", "Delete data complete");
        } else {
            responseMap.put("message", "Cannot find car id " + id);
        }
        ResponseEntity<Map<String, String>> response = ResponseEntity.ok(responseMap);

        return response;
    }



}
