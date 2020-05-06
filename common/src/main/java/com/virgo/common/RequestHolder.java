package com.virgo.common;

public class RequestHolder {

    private static final ThreadLocal<String> memberId = new InheritableThreadLocal<>();
    private static final ThreadLocal<String> companyCode = new InheritableThreadLocal<>();

    public static void setMemberId(String memberId) {
        RequestHolder.memberId.set(memberId);
    }

    public static String getMemberId() {
        return memberId.get();
    }

    public static void setCompanyCode(String companyCode) {
        RequestHolder.companyCode.set(companyCode);
    }

    public static String getCompanyCode() {
        return companyCode.get();
    }

    public static void clearAll() {
        memberId.remove();
        companyCode.remove();
    }
}
