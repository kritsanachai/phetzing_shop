package com.example.project.phetzing_shop.controller;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.phetzing_shop.model.Member;
import com.example.project.phetzing_shop.repository.MemberRepository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberRepository memberRepo;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping("")
    public ResponseEntity<Iterable<Member>> getAll() {
        Iterable data = memberRepo.findAll();
        System.out.println(data);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/byid/{id}")
    public ResponseEntity<Optional<Member>> getById(@PathVariable int id) {
        @SuppressWarnings("rawtypes")
        Optional data = memberRepo.findById(id);
        System.out.println(data);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping("/byrole/{role}")
    public ResponseEntity<List<Member>> getByRole(@PathVariable String role) {
        List data = memberRepo.findByRole(role);
        System.out.println(data);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @SuppressWarnings("null")
    @PostMapping("")
    public ResponseEntity<Member> create(@RequestBody Member dataMember) {
        Member newMember = memberRepo.save(dataMember);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMember);
    }

    @Getter @Setter @NoArgsConstructor
    public static class dataEditMember {
        private String name;
        private String email;
        private String address;
        
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<Map<String, Object>> create(@PathVariable int id, @RequestBody dataEditMember member) {
        Optional<Member> data = memberRepo.findById(id);
        Map<String, Object> responseMap = new HashMap<>();
        if (data.isPresent()) {
            Member oldMember = data.get();
            oldMember.setName(member.getName());
            oldMember.setEmail(member.getEmail());
            oldMember.setAddress(member.getAddress());

            memberRepo.save(oldMember);
            responseMap.put("message", "Edit car id " + id + " complate");
            responseMap.put("data", oldMember);

        } else {
            responseMap.put("message", "Cannot find car id " + id);
        }
         ResponseEntity<Map<String, Object>> res = ResponseEntity.ok(responseMap);
         return res; 

         
    }

    @SuppressWarnings("null")
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable int id) {
        // carRepository.deleteById(id);
        // return ResponseEntity.ok("Delete data complate");

        Optional<Member> optionalMember = memberRepo.findById(id);
        Map<String, String> responseMap = new HashMap<>();

        if (optionalMember.isPresent()) {
            // has data
            memberRepo.delete(optionalMember.get());
            responseMap.put("message", "Delete data complete");
        } else {
            responseMap.put("message", "Cannot find car id " + id);
        }
        ResponseEntity<Map<String, String>> response = ResponseEntity.ok(responseMap);

        return response;
    }

    @PatchMapping("/editRole/{id}/{status}")
    public ResponseEntity<Map<String, Object>> updatePassword(@PathVariable int id, @PathVariable String status) {
        Optional<Member> oldMember = memberRepo.findById(id);
        Map<String, Object> responseMap = new HashMap<>();
        if (oldMember.isPresent()) {
            Member member = oldMember.get();
            member.setRole(status);
            memberRepo.save(member);
            responseMap.put("message", "Edit Member id " + id + " complate");
            responseMap.put("data", member );
        } else {
            responseMap.put("message", "Cannot find car id " + id);
        }
        ResponseEntity<Map<String, Object>> response = ResponseEntity.ok(responseMap);

        return response;
    }

}
