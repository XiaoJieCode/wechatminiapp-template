package cn.wscsoft.backend.common.util;

import cn.wscsoft.backend.common.constant.ContextConstant;
import cn.wscsoft.backend.common.dto.BizException;
import cn.wscsoft.backend.common.dto.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文工具类，用于在当前线程中保存和获取用户相关信息。
 * 通过 ThreadLocal 保证每个线程拥有独立的数据副本，适用于一次请求内的数据共享。
 * 主要用于用户登录态信息的透传，例如 userId。
 */
@Slf4j
public class Context {

    /**
     * 使用 ThreadLocal 存储当前线程上下文数据（用户信息等），初始值为一个空的 HashMap。
     */
    private static final ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    /**
     * 获取当前线程上下文中的用户ID。
     *
     * @return 当前用户ID
     * @throws BizException 如果用户ID不存在（未登录或上下文未设置）
     */
    public static long getUserId() {
        Map<String, Object> map = threadLocal.get();
        Long u = (Long) map.get(ContextConstant.USER_ID_KEY);
        if (u == null) {
            // 抛出未授权异常，通常意味着未登录或上下文信息丢失
            throw new BizException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return u;
    }

    /**
     * 获取当前线程上下文中的用户ID。
     * 未登录可返回null
     *
     * @return 当前用户ID
     */

    public static Long getUserId(boolean force) {
        try {
            return getUserId();
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * 获取当前线程上下文的所有数据 Map。
     *
     * @return 当前线程上下文数据映射
     */
    public static Map<String, Object> getMap() {
        return threadLocal.get();
    }

    /**
     * 清空当前线程的上下文数据，防止线程复用导致的数据污染。
     * 通常在请求结束（如过滤器或拦截器中）调用。
     */
    public static void clear() {
        threadLocal.get().clear();
    }
}
