package com.example.project.phetzing_shop.controller;

import java.util.*;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.phetzing_shop.controller.OrderController.reqInsertOrderDetail.InnerOrderDetail;
import com.example.project.phetzing_shop.model.Member;
import com.example.project.phetzing_shop.model.Order;
import com.example.project.phetzing_shop.model.OrderDetail;
import com.example.project.phetzing_shop.model.Product;
import com.example.project.phetzing_shop.repository.MemberRepository;
import com.example.project.phetzing_shop.repository.OrderRepository;
import com.example.project.phetzing_shop.repository.OrderDetailRepository;
import com.example.project.phetzing_shop.repository.ProductRepository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class OrderController {
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private OrderDetailRepository order_detailRepo;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Iterable<Member> dataOrder = memberRepo.findAll();

            responseMap.put("status", HttpStatus.OK.value());
            responseMap.put("message", "Success");
            responseMap.put("data", dataOrder);

            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {

            responseMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseMap.put("message", "Error");
            responseMap.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping("/detail")
    public ResponseEntity<Map<String, Object>> getOrder_detail(@PathVariable int orderId) {
        Map<String, Object> res = new HashMap();
        try {
            Optional<Order> dataOrder = orderRepo.findById(orderId);
            if (dataOrder.isPresent()) {
                Order order = dataOrder.get();
                Iterable dataOrderDetail = order_detailRepo.findByOrder(order);

                res.put("status", HttpStatus.OK.value());
                res.put("message", "Get Data Success");
                res.put("data", dataOrderDetail);
                return ResponseEntity.ok(res);
            }else {
                res.put("status", HttpStatus.BAD_REQUEST.value());
                res.put("message", "Not Found can't Insert Order ");
                return ResponseEntity.ok(res);
            }

        } catch (Exception e) {
            res.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("message", "Error");
            res.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class OrderRequest {
        private LocalDateTime date;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping("/create/{memberId}")
    public ResponseEntity<Map<String, Object>> createOrder(@PathVariable int memberId,
            @RequestBody OrderRequest orderRequest) {

        Map<String, Object> res = new HashMap();
        try {
            Optional<Member> memberOptional = memberRepo.findById(memberId);

            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();
                Order order = new Order();

                order.setMember(member);
                orderRepo.save(order);

                res.put("status", HttpStatus.OK.value());
                res.put("message", "Insert Data Success");
                res.put("orderId", order);
                return ResponseEntity.ok(res);
            } else {
                res.put("status", HttpStatus.BAD_REQUEST.value());
                res.put("message", "Not Found can't Insert Order ");
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            res.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("message", "Error");
            res.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class reqInsertOrderDetail {

        @Getter
        @Setter
        @NoArgsConstructor
        public class InnerOrderDetail {
            private int productId;
            private int qty;

        }

        private Iterable<InnerOrderDetail> innerOrderDetails;
    }

    @PostMapping("/detail/{orderId}")
    public ResponseEntity<Map<String, Object>> InsertOrderDetail(@PathVariable int orderId,
            @RequestBody reqInsertOrderDetail reqInsertOrderDetail) {
        Map<String, Object> res = new HashMap<>();
        try {
            Optional<Order> dataOrder = orderRepo.findById(orderId);
            if (dataOrder.isPresent()) {
                Order order = dataOrder.get();

                OrderDetail order_Detail = new OrderDetail();
                for (InnerOrderDetail resIterable : reqInsertOrderDetail.getInnerOrderDetails()) {
                    Optional<Product> dataProduct = productRepo.findById(resIterable.getProductId());
                    Product product = dataProduct.get();
                    order_Detail.setOrder(order);
                    order_Detail.setProduct(product);
                    order_Detail.setQty(resIterable.getQty());
                    order_detailRepo.save(order_Detail);
                }

                res.put("status", HttpStatus.OK);
                res.put("message", "Insert Data Success");
                return ResponseEntity.ok(res);
            } else {
                res.put("status", HttpStatus.BAD_REQUEST.value());
                res.put("message", "Not Found can't Insert Order_Detail ");
                return ResponseEntity.ok(res);
            }

        } catch (Exception e) {
            res.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("message", "error");
            res.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @SuppressWarnings("null")
    @DeleteMapping("{orderId}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable int orderId) {
        Map<String, Object> res = new HashMap<>();
        try {
            Optional<Order> dataOrder = orderRepo.findById(orderId);
            if (dataOrder.isPresent()) {
                orderRepo.delete(dataOrder.get());
                res.put("status", HttpStatus.OK);
                res.put("message", "Delete Data Success");
                return ResponseEntity.ok(res);
            } else {
                res.put("status", HttpStatus.BAD_REQUEST.value());
                res.put("message", "Not Found can't Delete Order ");
                return ResponseEntity.ok(res);
            }

        } catch (Exception e) {
            res.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("message", "error");
            res.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

}