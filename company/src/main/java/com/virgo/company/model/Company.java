package com.virgo.company.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Company {
    @Id
    private Long id;
    private String name;
    private String shortName;
    private String brandName;
    private String code;
    private String telPhone;
    private String phone;
    private String contactPersonPhone;
    private String address;
    private String contactPerson;   //联系人
    private String juridicalPerson; //法人
    private Status status;  //状态
    private Boolean authenticated;//实名认证
    private AuthenticatedStatus authenticatedStatus;
    private AccountType accountType;
    @CreatedBy
    private String creator;
    @LastModifiedBy
    private String revisor;
    private LocalDateTime updateTime;
    @CreatedDate
    private LocalDateTime createTime;
    @Version
    private Long version;


    private String capitalAccount;


    public enum AccountType {
        EXPERIENCE, //体验账号
        VIP,        //vip账号
        YEAR,       //年度賬戶
        SVIP        //svip账号
    }

    public enum AuthenticatedStatus {
        UN_AUTHENTICATED, //未认证
        AUTHENTICATING,        //认证中
        AUTHENTICATED,       //认证成功
        AUTHENTICATED_FAILED        //认证失败
    }


    public enum Status {
        EFFICIENT,      //生效
        OVERDUE,        //过期
        UN_EFFICIENT,   //未生效
        UN_PAY,         //未充值
        FREEZE,         //冻结
    }
}
