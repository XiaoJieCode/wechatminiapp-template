package cn.wscsoft.backend.common.service.impl;


import cn.wscsoft.backend.common.dto.BaseResponse;
import cn.wscsoft.backend.common.dto.BizException;
import cn.wscsoft.backend.common.dto.ErrorCode;
import cn.wscsoft.backend.common.enums.UserRoleEnum;
import cn.wscsoft.backend.common.model.User;
import cn.wscsoft.backend.common.service.UserService;
import cn.wscsoft.backend.common.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@AutoConfiguration
@Service
public class AuthService {
    @Lazy
    @Resource
    private UserService userService;

    /**
     * 检查用户是否存在并拥有访问权限
     *
     * @param userId    用户 ID
     * @param rolesStr  注解中指定的角色字符串
     * @param rolesEnum 注解中指定的枚举角色
     */
    public void checkUserPermission(Long userId, String[] rolesStr, UserRoleEnum[] rolesEnum) {
        if (userId == null) {
            throw new BizException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        }

        User user = userService.getById(userId);
        if (user == null) {
            throw new BizException(ErrorCode.NOT_LOGIN_ERROR, "用户不存在");
        }

        String userRole = user.getRole();
        if (!StringUtils.hasText(userRole)) {
            throw new BizException(ErrorCode.FORBIDDEN_ERROR, "用户无角色信息");
        }

        boolean allowed = false;

        if (rolesEnum != null) {
            for (UserRoleEnum roleEnum : rolesEnum) {
                if (roleEnum.getValue().equalsIgnoreCase(userRole)) {
                    allowed = true;
                    break;
                }
            }
        }

        if (!allowed && rolesStr != null) {
            for (String roleStr : rolesStr) {
                if (roleStr.equalsIgnoreCase(userRole)) {
                    allowed = true;
                    break;
                }
            }
        }

        if (!allowed) {
            throw new BizException(ErrorCode.FORBIDDEN_ERROR, "用户无权限访问");
        }
    }
}
