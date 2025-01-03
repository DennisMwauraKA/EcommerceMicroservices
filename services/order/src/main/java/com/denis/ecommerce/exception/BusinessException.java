package com.denis.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    public BusinessException(String s) {
        super(s);
    }
}
