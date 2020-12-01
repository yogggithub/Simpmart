package com.simpmart.common.validation.validator;

import com.simpmart.common.validation.annotation.ListValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class ListValueValidator implements ConstraintValidator<ListValue, Integer> {
    private Set<Integer> valSet = new HashSet<Integer>();

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return valSet.contains(integer);
    }


    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.values();
        for (int value : values) {
            valSet.add(value);
        }
    }
}
