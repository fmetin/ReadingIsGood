package com.fmetin.readingisgood.mapper;

import com.fmetin.readingisgood.annotation.EncodedMapping;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PasswordEncoderMapper {

    final PasswordEncoder passwordEncoder;

    @EncodedMapping
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }
}