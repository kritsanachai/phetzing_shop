package com.example.project.phetzing_shop.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.project.phetzing_shop.model.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member,Integer> {
    List<Member> findByRole(String role);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndPassword(String email, String password);
    
}
