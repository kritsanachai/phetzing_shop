package com.example.project.phetzing_shop.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id ;

    @Column(nullable=false, unique=true)
    private String name ;

    @Column(nullable=false, unique=true)
    private String  email ;

    @Column(nullable=false)
    private String password ;

    @Column(nullable=false)
    private String address;

    @Column(nullable=false)
    private String role;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    
    private List<Cart> carts;
    public Member(String name, String email, String password, String address, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }



}
