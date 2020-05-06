package com.virgo.common.validate;

import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@Component
public class ValidateUtils {
    @Resource
    private Validator validator;

    public void validate(Object object, Class<?>... groups) {
        Set<?> createViolations = validator.validate(object, groups);
        if (!CollectionUtils.isEmpty(createViolations)) {
            log.error("validate error {} data {}", createViolations, object);
            throw new BusinessException(ResultEnum.PARAM_ERROR);
        }
    }
}
