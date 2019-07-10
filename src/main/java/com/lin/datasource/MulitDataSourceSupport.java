package com.lin.datasource;

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
        Deque<JDBCContext> arrayDeque = DEQUE_LOCAL.get();
        JDBCContext maxJDBC = getMaxOrderJDBCContext(arrayDeque);
        //都为空
        if (Objects.isNull(maxJDBC) && Objects.isNull(jdbcContext)) {
            logger.warn("the is no jdbcContext,mayBe you made a mistake");
            return null;
        }
        //有一个为空
        if (Objects.isNull(maxJDBC) || Objects.isNull(jdbcContext)) {
            return maxJDBC == null ? jdbcContext.getDataSourceBeanName() : maxJDBC.getDataSourceBeanName();
        }
        //都不为空
        return maxJDBC.getOrder() > jdbcContext.getOrder() ? maxJDBC.getDataSourceBeanName() : jdbcContext.getDataSourceBeanName();

    }

    public static void clear(HttpServletRequest request) {
        logger.info("the request:{} clear", request.getRequestURL());
        JDBCContext jdbcContext = local.get();
        if (Objects.nonNull(jdbcContext)) {
            jdbcContext.clear();
        }
        local.remove();
        DEQUE_LOCAL.remove();

    }


    /**
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
     * 上下文取出一层，如controller中一个服务涉及到多个数据服务
     */
    public static void popContext() {
        Deque<JDBCContext> deque = DEQUE_LOCAL.get();
        if (deque != null) {
            deque.pop();
//            JDBCContext context = deque.peek();
//            if (context != null) {
//                deque.pop();
////                local.set(deque.pop());
//            }
        }
    }

    /**
     * 清理全部上下文
     */
    public static void removeAllContext() {
        local.remove();
        DEQUE_LOCAL.remove();
    }


    private static JDBCContext getMaxOrderJDBCContext(Deque<JDBCContext> arrayDeque) {
        if (Objects.isNull(arrayDeque)) {
            return null;
        }
        JDBCContext max = null;
        Iterator<JDBCContext> iterator = arrayDeque.iterator();
        while (iterator.hasNext()) {
            JDBCContext jdbcContext = iterator.next();
            if (Objects.isNull(max) || max.getOrder() < jdbcContext.getOrder()) {
                max = jdbcContext;
                continue;
            }

        }
        return max;

    }


    public static JDBCContext getCurrentJDBCCurrent() {
        return local.get();
    }

    public static void removeCurrentJDBCCurrent() {
        local.remove();
    }

    public static void setCurrentJDBCCurrent(JDBCContext jdbcContext) {
        local.set(jdbcContext);
    }

}
