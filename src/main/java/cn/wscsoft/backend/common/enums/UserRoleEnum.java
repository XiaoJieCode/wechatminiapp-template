package cn.wscsoft.backend.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色枚举
 *
 * @author 观止
 */
@Getter
public enum UserRoleEnum {

    USER("普通用户", "user"),
    USER_MERCHANT("商家用户", "user_merchant"),
    USER_VIP("vip用户", "user_vip"),
    ADMIN("管理员", "admin");

    private final String text;

    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static UserRoleEnum getEnumByValue(String value) {
        if (value == null) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 判断是否存在该角色
     */
    public static boolean isExist(String value) {
        if (value == null) {
            return false;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAdmin(UserRoleEnum userRoleEnum) {
        return ADMIN.getValue().equals(userRoleEnum.getValue());
    }

    public static boolean isAdmin(String userRole) {
        return ADMIN.getValue().equals(userRole);
    }
}
