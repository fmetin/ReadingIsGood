package com.fmetin.readingisgood.mapper;

import com.fmetin.readingisgood.dto.CreateBookRequestDto;
import com.fmetin.readingisgood.dto.OrderDetailResponseDto;
import com.fmetin.readingisgood.dto.OrderDetailsDto;
import com.fmetin.readingisgood.entity.Book;
import com.fmetin.readingisgood.entity.Order;
import com.fmetin.readingisgood.entity.OrderDetail;
import com.fmetin.readingisgood.shared.LocalDateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Autowired
    protected LocalDateTimeUtil localDateTimeUtil;

    @Mapping(target = "createdDate", expression = "java(localDateTimeUtil.now())")
    public abstract Order mapOrderDetailsDtoToOrder(OrderDetailsDto orderDetailsDto);

    public abstract OrderDetail mapOrderToOrderDetail(Order order);
    public abstract OrderDetailResponseDto mapOrderToOrderDetail(OrderDetail orderDetail);
    public abstract List<OrderDetailResponseDto> mapOrderDetailListToOrderDetailResponseList(List<OrderDetail> orderDetailList);


}
