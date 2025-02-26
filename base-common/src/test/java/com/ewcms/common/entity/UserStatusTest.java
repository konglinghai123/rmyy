package com.ewcms.common.entity;

/**
 * @author wu_zhijun
 */
public enum UserStatusTest {

    normal("正常状态"), blocked("封禁状态");

    private final String info;

    private UserStatusTest(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
