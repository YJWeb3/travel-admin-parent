package com.zheng.travel.admin.validator.valid;


import com.zheng.travel.admin.validator.anno.TravelURL;
import com.zheng.travel.admin.validator.utils.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class TravelUrlValidator implements ConstraintValidator<TravelURL, String> {
    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        // 1: 如果用户没输入直接返回不校验，因为空的判断交给@NotEmpty去做就行了
        if (null == url || "".equals(url) ) {
            return true;
        }

        // 2：如果校验通过就返回true,否则返回false;
        return ValidatorUtil.isURL(url);
    }

    @Override
    public void initialize(TravelURL constraintAnnotation) {
    }
}
