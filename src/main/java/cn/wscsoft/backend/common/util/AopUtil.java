package cn.wscsoft.backend.common.util;

import org.springframework.aop.framework.AopContext;

import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class AopUtil {
    /**
     * 通过当前 AOP 代理对象执行 lambda 方法。
     *
     * @param _obj 当前类类型
     * @param caller 方法调用逻辑，使用代理对象执行
     * @param <T> 当前类类型
     */
    public static <T> void callViaProxy(T _obj, Consumer<T> caller) {
        // 获取当前 AOP 代理对象
        T _this = (T) AopContext.currentProxy();
        caller.accept(_this);
    }

    public static <T, A> void callViaProxy(T _this, ConsumerWithArg<T, A> caller, A arg) {
        T proxy = (T) AopContext.currentProxy();
        caller.accept(proxy, arg);
    }

    @FunctionalInterface
    public interface ConsumerWithArg<T, A> {
        void accept(T t, A a);
    }
}
