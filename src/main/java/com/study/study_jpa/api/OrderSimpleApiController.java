package com.study.study_jpa.api;

import com.study.study_jpa.domain.Address;
import com.study.study_jpa.domain.Order;
import com.study.study_jpa.domain.OrderStatus;
import com.study.study_jpa.repository.OrderRepository;
import com.study.study_jpa.repository.OrderSearch;
import com.study.study_jpa.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ( x to One 관계 )
 * Order
 * Order -> Member ( many to one )
 * Order -> Delivery ( one to one )
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /**
     * order 안에 member 가 있고 member 안에 order 가 있어서 무한루프에 들어간다.
     * @return
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleDto> ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());

        // ORDER 는 2개인데 총 쿼리는 5번 실행됨
        // N + 1 -> 1 + 회원 N + 배송 N
        //return   orders.stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
        return   orders.stream().map(order -> new OrderSimpleDto(order)).collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleDto> ordersV3() {

        // fetch join 을 사용함, jpa 에서만 사용하는 기능
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        return   orders.stream().map(order -> new OrderSimpleDto(order)).collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List <OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }

    @Data
    public class OrderSimpleDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }


}
