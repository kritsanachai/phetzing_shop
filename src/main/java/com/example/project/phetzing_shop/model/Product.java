package com.example.project.phetzing_shop.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
   
    private String name;
    private int price ;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private List<OrderDetail> order_Details;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private List<Cart> carts;



}
