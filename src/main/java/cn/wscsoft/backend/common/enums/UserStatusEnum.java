package cn.wscsoft.backend.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户状态枚举
 *
 * @author 观止
 */
public enum UserStatusEnum {

    /**
     * 0 - 正常，1 - 封号，2 - 注销
     */
    NORMAL("正常", 0),
    BAN("封号", 1),
    DESTROY("注销", 2);

    private final String text;

    private final Integer value;

    UserStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static UserStatusEnum getEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (UserStatusEnum anEnum : UserStatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 判断是否存在该状态
     */
    public static boolean isExist(Integer value) {
        if (value == null) {
            return false;
        }
        for (UserStatusEnum anEnum : UserStatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return true;
            }
        }
        return false;
    }


    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
