package com.study.study_jpa.repository;

import com.study.study_jpa.domain.Order;
import com.study.study_jpa.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long orderId){
        return em.find(Order.class, orderId);
    }

    public List<Order> findAll(OrderSearch orderSearch){
        return em.createQuery("select o from Order  o join o.member m",
                Order.class)
                .setMaxResults(1000) // 최대 1000건
                .getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o " +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new com.study.study_jpa.repository.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o " +
                        " join o.member m " +
                        " join o.delivery d ", OrderSimpleQueryDto.class
        ).getResultList();
    }

    /**
     * fetch join 을 하면 페이징이 안된다.
     */
    public List<Order> findAllWithItem() {
        return em.createQuery(
                "select distinct o from Order o " +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" +
                        " join fetch o.orderItems oi" +
                        " join fetch oi.item i", Order.class
        ).getResultList();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {

        return em.createQuery(
                "select o from Order o " +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    //public List<Order> findAll
}
