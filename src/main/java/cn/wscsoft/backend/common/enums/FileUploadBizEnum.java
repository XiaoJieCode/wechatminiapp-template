package cn.wscsoft.backend.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 文件上传业务类型枚举
 *
 * @author 观止
 */
@Getter
public enum FileUploadBizEnum {

    /**
     * 文件用途
     */
    UserAvatar("用户头像", "UserAvatar"),

    ;

    private final String text;
    @JsonValue
    @EnumValue
    private final String value;

    FileUploadBizEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     */
    public static FileUploadBizEnum getEnumByValue(String value) {
        if (value == null) {
            return null;
        }
        for (FileUploadBizEnum anEnum : FileUploadBizEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }


}
