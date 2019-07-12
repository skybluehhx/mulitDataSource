package com.lin.Interceptor;

import com.lin.support.MulitDataSourceSupport;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.lin.support.MulitConstant.DATA_SOURCE_PARAM_NAME;


/**
 * @author jianglinzou
 * @date 2019/7/2 下午7:15
 */
@Component
public class MulitDataSourceInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String dataSource = request.getParameter(DATA_SOURCE_PARAM_NAME);
        if (Strings.isBlank(dataSource)) {
            dataSource = request.getHeader(DATA_SOURCE_PARAM_NAME);
        }

        MulitDataSourceSupport.putDataSourceName(request, dataSource);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        MulitDataSourceSupport.clear(request);
    }

}
