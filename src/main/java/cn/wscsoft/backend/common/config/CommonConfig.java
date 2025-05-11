package cn.wscsoft.backend.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
@Slf4j
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Configuration
@MapperScan(basePackages = "cn.wscsoft.backend.common.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class CommonConfig {
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);

        objectMapper.registerModule(module);

        return objectMapper;
    }


    @Value("${async.executor.thread.corePoolSize}")
    private int corePoolSize;
    @Value("${async.executor.thread.maxPoolSize}")
    private int maxPoolSize;
    @Value("${async.executor.thread.queueCapacity}")
    private int queueCapacity;
    @Value("${async.executor.thread.keepAliveSeconds}")
    private int keepAliveSeconds;
    @Value("${async.executor.thread.namePrefix}")
    private String namePrefix;

    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor() {
        log.info("SpringBoot的线程池配置");
        log.info("corePoolSize: {}", corePoolSize);
        log.info("maxPoolSize: {}", maxPoolSize);
        log.info("queueCapacity: {}", queueCapacity);
        log.info("keepAliveSeconds: {}", keepAliveSeconds);
        log.info("namePrefix: {}", namePrefix);

        ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();

        // 设置核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 设置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        // 设置缓冲队列大小
        executor.setQueueCapacity(queueCapacity);
        // 设置线程的最大空闲时间
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 设置线程名字的前缀
        executor.setThreadNamePrefix(namePrefix);
        // 设置拒绝策略：当线程池达到最大线程数时，如何处理新任务
        // CALLER_RUNS：在添加到线程池失败时会由主线程自己来执行这个任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 线程池初始化
        executor.initialize();

        return executor;
    }

    static class VisibleThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
        public void info() {
            ThreadPoolExecutor executor = getThreadPoolExecutor();

            String info = "线程池: " + this.getThreadNamePrefix() +
                    "中, 总任务数为: " + executor.getTaskCount() +
                    " , 已处理完的任务数为: " + executor.getCompletedTaskCount() +
                    " , 目前正在处理的任务数为: " + executor.getActiveCount() +
                    " , 缓冲队列中任务数为: " + executor.getQueue().size();

            log.info(info);
        }

        @Override
        public void execute(@NonNull Runnable task) {
            info();
            super.execute(task);
        }

        @Override
        public void execute(@NonNull Runnable task, long startTimeout) {
            info();
            super.execute(task, startTimeout);
        }

        @NonNull
        @Override
        public Future<?> submit(@NonNull Runnable task) {
            info();
            return super.submit(task);
        }

        @NonNull
        @Override
        public <T> Future<T> submit(@NonNull Callable<T> task) {
            info();
            return super.submit(task);
        }

        @NonNull
        @Override
        public ListenableFuture<?> submitListenable(@NonNull Runnable task) {
            info();
            return super.submitListenable(task);
        }

        @NonNull
        @Override
        public <T> ListenableFuture<T> submitListenable(@NonNull Callable<T> task) {
            info();
            return super.submitListenable(task);
        }
    }
    /**
     * 配置 mybatis plus 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
