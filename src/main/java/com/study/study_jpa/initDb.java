package com.study.study_jpa;

import com.study.study_jpa.domain.*;
import com.study.study_jpa.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class initDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member member = createMember("userA", "서울", "길", "111");
            em.persist(member);

            Book book = createBook("JPA1 Book", 10000);
            em.persist(book);

            Book book2 = createBook("JPA2 Book", 20000);
            em.persist(book2);

            OrderItem orderItem = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem, orderItem2);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        public void dbInit2(){
            Member member = createMember("userB", "경기", "길", "111");
            em.persist(member);

            Book book = createBook("SPRING1 Book", 10000);
            em.persist(book);

            Book book2 = createBook("SPRING2 Book", 20000);
            em.persist(book2);

            OrderItem orderItem = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem, orderItem2);
            em.persist(order);
        }
    }

    private static Book createBook(String s, int i) {
        Book book = new Book();
        book.setName(s);
        book.setPrice(i);
        book.setStockQuantity(100);
        return book;
    }
}


