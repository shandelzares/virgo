package com.virgo.member.dto;

import com.virgo.common.web.ValidGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class LoginParam {

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空", groups = SmsGroup.class)
    private String phone;

    @ApiModelProperty("登陆类型")
    @NotNull(message = "类型不能为空")
    private Type type;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("短信验证码")
    @NotNull(message = "验证码不能为空", groups = SmsGroup.class)
    private Integer smsCode;

    public enum Type {
        PHONE, SMS, EMAIL, QQ, WE_CHAT
    }

    public interface SmsGroup extends ValidGroup {

    }
}
