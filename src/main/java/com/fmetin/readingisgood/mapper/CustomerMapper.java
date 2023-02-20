package com.fmetin.readingisgood.mapper;


import com.fmetin.readingisgood.annotation.EncodedMapping;
import com.fmetin.readingisgood.dto.CreateCustomerRequestDto;
import com.fmetin.readingisgood.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PasswordEncoderMapper.class})
public interface CustomerMapper {
    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    Customer mapCustomerToCreateCustomerRequestDto(CreateCustomerRequestDto requestDto);

}
