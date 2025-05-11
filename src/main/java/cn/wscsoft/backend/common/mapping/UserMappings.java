package cn.wscsoft.backend.common.mapping;


import cn.wscsoft.backend.common.model.User;
import cn.wscsoft.backend.common.vo.LoginUserVO;
import cn.wscsoft.backend.common.vo.UserVO;
import org.springframework.beans.BeanUtils;

/**
 * 用户相关model转换
 */
public class UserMappings {
    public static UserVO toUserVO(User user) {
        if (user == null) return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    public static LoginUserVO toLoginUserVO(User user) {
        if (user == null) return null;
        LoginUserVO userVO = new LoginUserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
