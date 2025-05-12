package cn.wscsoft.backend.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum StorageType {
    LOCAL("local"),
    MINIO("minio");

    @EnumValue
    @JsonValue
    private final String value;

    StorageType(String value) {
        this.value = value;
    }
}
