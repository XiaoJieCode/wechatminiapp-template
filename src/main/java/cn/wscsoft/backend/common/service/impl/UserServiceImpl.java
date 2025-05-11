package cn.wscsoft.backend.common.service.impl;


import cn.wscsoft.backend.common.dto.UserQueryRequest;
import cn.wscsoft.backend.common.mapper.UserMapper;
import cn.wscsoft.backend.common.mapping.UserMappings;
import cn.wscsoft.backend.common.model.User;
import cn.wscsoft.backend.common.service.UserService;
import cn.wscsoft.backend.common.vo.UserVO;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户基础服务
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public List<UserVO> listUser(UserQueryRequest req) {
        return buildQueryWrapper(req).list().stream().map(UserMappings::toUserVO).toList();
    }

    private LambdaQueryChainWrapper<User> buildQueryWrapper(UserQueryRequest req) {
        LambdaQueryChainWrapper<User> query = lambdaQuery();
        if (req.getId() != null) {
            query.eq(User::getId, req.getId());
        }
        if (req.getUserName() != null) {
            query.eq(User::getName, req.getUserName());
        }
        if (req.getUserRole() != null) {
            query.eq(User::getRole, req.getUserRole());
        }
        return query;
    }

}
