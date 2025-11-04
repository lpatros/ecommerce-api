package com.lpatros.ecommerce_api.dto.order;

import com.lpatros.ecommerce_api.entity.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPatch {

    private OrderStatus orderStatus;

    private String trackingCode;
}
