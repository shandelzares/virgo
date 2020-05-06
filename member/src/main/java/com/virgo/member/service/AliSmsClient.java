package com.virgo.member.service;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.virgo.common.JsonUtils;
import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.ResultEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class AliSmsClient implements SmsClient {

    @Override
    public int send(String phone) {
        int num = (int) ((Math.random() * 9 + 1) * 100000);

        String signName = "程星程易";

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIDGBiUrTX0bnI", "JRvmka92bz6RoFq5gcjGdMYCwDem0b");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", "SMS_148082982");
        request.putQueryParameter("TemplateParam", "{code:'" + num + "'}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info("sms res:{}", response);
            Map<String, String> result = JsonUtils.parse(response.getData(), new TypeReference<>() {
            });
            if (!result.get("Code").toUpperCase().equals("OK")) {
                throw new BusinessException(ResultEnum.SEND_SMS_ERROR);
            }
            return num;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.SEND_SMS_ERROR);
        }
    }
}
