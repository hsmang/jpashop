package com.study.study_jpa.repository.order.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderItemQueryDto {

    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int orderCount;

    public OrderItemQueryDto(String itemName, int orderPrice, int orderCount) {
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }
}
