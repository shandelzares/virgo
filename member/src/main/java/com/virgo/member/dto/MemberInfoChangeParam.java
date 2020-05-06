package com.virgo.member.dto;

import com.virgo.member.model.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel
public class MemberInfoChangeParam {

    @ApiModelProperty("会员号")
    @NotBlank(message = "会员号不能为空")
    private String memberId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty(value = "性别", allowableValues = "MAN,WOMAN")
    @Pattern(regexp = "MAN|WOMAN")
    private Member.Sex sex;

    @ApiModelProperty("头像地址")
    private String avatarUrl;//头像地址
}
