package com.ewcms.common.entity.enums;

/**
 * @author wuzhijun
 */
public enum SexEnum {
	MALE("男"), FEMALE("女");

    private final String info;

    private SexEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
