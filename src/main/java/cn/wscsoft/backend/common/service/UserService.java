package cn.wscsoft.backend.common.service;

import cn.wscsoft.backend.common.dto.UserQueryRequest;
import cn.wscsoft.backend.common.model.User;
import cn.wscsoft.backend.common.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户基础服务
 */
public interface UserService  extends IService<User> {
    List<UserVO> listUser(UserQueryRequest req);
}
