package cn.wscsoft.backend.common.annos;


import cn.wscsoft.backend.common.enums.UserRoleEnum;

import java.lang.annotation.*;

/**
 * AuthedApi
 * 表示当前接口需要用户身份认证，可以控制是否登录和角色访问权限
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthedApi {

    /**
     * 是否必须登录
     */
    boolean login() default true;

    /**
     * 允许访问的角色列表，比如 {"ADMIN", "USER"}
     */
    String[] rolesStr() default {};

    /**
     * 允许访问的角色列表，比如 {"ADMIN", "USER"}
     */
    UserRoleEnum[] roles();
}
