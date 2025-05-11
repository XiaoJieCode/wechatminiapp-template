package cn.wscsoft.backend.common.service.impl;


import cn.wscsoft.backend.common.dto.BizException;
import cn.wscsoft.backend.common.dto.ErrorCode;
import cn.wscsoft.backend.common.dto.UserSessionInfoRecord;
import cn.wscsoft.backend.common.mapping.UserMappings;
import cn.wscsoft.backend.common.model.User;
import cn.wscsoft.backend.common.service.UserLoginService;
import cn.wscsoft.backend.common.service.UserService;
import cn.wscsoft.backend.common.service.WechatMiniAppService;
import cn.wscsoft.backend.common.util.JwtUtil;
import cn.wscsoft.backend.common.vo.LoginTokenVO;
import cn.wscsoft.backend.common.vo.LoginUserVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


/**
 * 用户登录服务
 */
@Slf4j
@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Resource
    private WechatMiniAppService wechatMiniAppService;
    @Value("${token.expire}")
    private Long tokenExpire;
    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "123456";

    @Resource
    private UserService userService;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BizException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BizException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BizException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BizException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = userService.count(queryWrapper);
            if (count > 0) {
                throw new BizException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 3. 插入数据
            User user = new User();
            user.setAccount(userAccount);
            user.setPassword(userPassword);
            boolean saveResult = userService.save(user);
            if (!saveResult) {
                throw new BizException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    /**
     * 通过账号密码登录
     */
    @Override
    public LoginTokenVO userLoginByUserAccount(String userAccount, String userPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BizException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BizException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BizException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userService.getOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BizException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        LoginUserVO loginUserVO = UserMappings.toLoginUserVO(user);
        // 3. 记录用户的登录态
        String token = JwtUtil.generateToken(String.valueOf(loginUserVO.getId()), tokenExpire);
        LoginTokenVO tokenVO = new LoginTokenVO();
        BeanUtils.copyProperties(loginUserVO, tokenVO);
        tokenVO.setToken(token);
        return tokenVO;
    }

    /**
     * 微信小程序登录
     *
     * @param code 小程序wx.login响应的code
     * @return 登录用户信息
     */
    @Override
    public LoginUserVO userLoginByMiniOpen(String code) {
        try {
            // 1.通过code、开发者appId，appSecret换取用户信息
            UserSessionInfoRecord infoRecord = wechatMiniAppService.getWechatUserSessionInfo(code);
            log.info("wechatUserSessionInfo:{}", infoRecord);
            String sessionKey = infoRecord.sessionKey();
            String openId = infoRecord.openId();
            log.info("登录用户openId:{}", openId);
            if (StringUtils.isAnyBlank(sessionKey, openId)) {
                throw new BizException(ErrorCode.SYSTEM_ERROR, "登录失败，系统错误");
            }
            // 单机锁
            synchronized (openId.intern()) {
                // 反转存储，前缀区分度比较大，利于创建前缀索引
                openId = new StringBuffer(openId).reverse().toString();

                // 查询用户是否已存在
                User user = userService.lambdaQuery().eq(User::getOpenId, openId).one();
                if (user == null) {
                    // 用户不存在，创建
                    user = new User();
                    user.setOpenId(openId);
                    user.setName("默认昵称");
                    boolean result = userService.save(user);
                    if (!result) {
                        throw new BizException(ErrorCode.SYSTEM_ERROR, "登录失败");
                    }
                    log.info("新用户注册：{}", user);
                }

                LoginUserVO loginUserVO = UserMappings.toLoginUserVO(user);
                // 3. 记录用户的登录态
                String token = JwtUtil.generateToken(String.valueOf(loginUserVO.getId()), tokenExpire);
                LoginTokenVO tokenVO = new LoginTokenVO();
                BeanUtils.copyProperties(loginUserVO, tokenVO);
                tokenVO.setToken(token);
                return tokenVO;
            }
        } catch (Exception e) {
            throw new BizException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }




}
