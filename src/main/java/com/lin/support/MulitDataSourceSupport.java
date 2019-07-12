package com.lin.support;

import com.lin.annotation.Name;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author jianglinzou
 * @date 2019/7/2 下午7:16
 */
public class MulitDataSourceSupport {

    //当前调用层
    private static final ThreadLocal<JDBCContext> local = new ThreadLocal<JDBCContext>();

    /**
     * The constant DEQUE_LOCAL 保存历史调用层
     */
    private static final ThreadLocal<Deque<JDBCContext>> DEQUE_LOCAL = new ThreadLocal<Deque<JDBCContext>>();


    private static Logger logger = LoggerFactory.getLogger(MulitDataSourceSupport.class);

    public static void putDataSourceName(HttpServletRequest request, String dsn) {
        logger.info("the request:{} put dataSourceName:{}", request.getRequestURL(), dsn);
        if (!Strings.isBlank(dsn)) {
            JDBCContext jdbcContext = local.get();
            if (Objects.isNull(jdbcContext)) {
                jdbcContext = new JDBCContext(dsn);
                local.set(jdbcContext);
            }
        }
    }

    public static void putDataSourceName(Method method, Name name) {
        NameMethod nameMethod = new NameMethod(method, name);
        JDBCContext jdbcContext = local.get();
        if (Objects.isNull(jdbcContext)) {
            jdbcContext = new JDBCContext();
            local.set(jdbcContext);
        }
        jdbcContext.putNameMethod(nameMethod);
    }

    //选取所有中，优先级最高的
    public static String getDataSourceName() {
        JDBCContext jdbcContext = local.get();
        if (Objects.isNull(jdbcContext)) {
            logger.warn("the is no jdbcContext,mayBe you made a mistake");
            return null;
        }
        return jdbcContext.getDataSourceBeanName();
    }

    public static void clear(HttpServletRequest request) {
        logger.info("the request:{} clear", request.getRequestURL());
        JDBCContext jdbcContext = local.get();
        if (Objects.nonNull(jdbcContext)) {
            jdbcContext.clear();
        }
        removeAllContext();

    }


    /**
     * 调用该方法前，请调用putDataSourceName或者putDataSourceName方法，
     * 先存当前上下文
     * <p>
     * 上下文往下放一层,如controller中涉及到多个数据源，多个数据服务
     */
    public static void pushContext() {
        JDBCContext context = local.get();
        if (context != null) {
            Deque<JDBCContext> deque = DEQUE_LOCAL.get();
            if (deque == null) {
                deque = new ArrayDeque<JDBCContext>();
                DEQUE_LOCAL.set(deque);
            }
            deque.push(context);
            local.set(null);
        }
    }

    /**
     *
     */
    public static JDBCContext peekContext() {
        return local.get();
    }


    public static JDBCContext getContext() {
        JDBCContext context = local.get();
        if (context == null) {
            context = new JDBCContext();
            local.set(context);
        }
        return context;
    }

    /**
     * 上下文取出一层，如controller中一个服务涉及到多个数据服务
     */
    public static void popContext() {
        Deque<JDBCContext> deque = DEQUE_LOCAL.get();
        if (deque != null) {
            JDBCContext context = deque.peek();
            if (context != null) {
                local.set(deque.pop());
            }
        }
    }

    /**
     * 清理全部上下文
     */
    public static void removeAllContext() {
        local.remove();
        DEQUE_LOCAL.remove();
    }


    /**
     * 清理上下文
     */
    public static void removeContext() {
        local.remove();
    }


    /**
     * 保存上下文
     *
     * @param method
     * @param handler handler 为ture是则处理，这也意味着，method方法上必有{@link com.lin.annotation.Name}注解
     */
    public static void doSaveJDBCContextIfNecessary(Method method, boolean handler) {
        if (handler) {
            MulitDataSourceSupport.pushContext();
        }
    }

    /**
     * 设置当前上下文
     *
     * @param method
     * @param handler handler 为ture是则处理，这也意味着，method方法上必有{@link com.lin.annotation.Name}注解
     */
    public static void dosetDataSourceNameIfNecessary(Method method, boolean handler) {
        if (handler) {
            Name name = method.getAnnotation(Name.class);
            putDataSourceName(method, name);
        }
    }

    /**
     * 移除当前上下文
     *
     * @param method
     * @param handler handler 为ture是则处理，这也意味着，method方法上必有{@link com.lin.annotation.Name}注解
     */
    public static void doRemoveContextIfNecessary(Method method, boolean handler) {
        if (handler) {
            MulitDataSourceSupport.removeContext();
        }
    }

    /**
     * 恢复上层上下文
     *
     * @param method
     * @param handler handler 为ture是则处理，这也意味着，method方法上必有{@link com.lin.annotation.Name}注解
     */
    public static void doPopContextIfNecessary(Method method, boolean handler) {
        if (handler) {
            MulitDataSourceSupport.popContext();
        }
    }


}
