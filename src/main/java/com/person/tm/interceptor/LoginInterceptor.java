package com.person.tm.interceptor;

import com.person.tm.pojo.Admin;
import com.person.tm.pojo.User;
import com.person.tm.service.CategoryService;
import com.person.tm.service.OrderItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

//拦截器。每次请求不同Controller时，进行都要进行拦截，查看是否已经登录。
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderItemService orderItemService;

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     * 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();

        //不需要进行拦截的Controller。
        String[] noNeedAuthPage = new String[]{
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"
        };
        //  System.out.println("-----------contextPath:" + contextPath);

        String uri = request.getRequestURI();
        // System.out.println("------------------uri:" + uri);
        uri = StringUtils.remove(uri, contextPath);
        // System.out.println("------------------uri:" + uri);
        //用户：判断uri 是否是以/fore（前端）  为开始
        if (uri.startsWith("/fore")) {
            //如果是则，截取/fore 后面的字符串
            String method = StringUtils.substringAfterLast(uri, "/fore");

            //将noNeedAuthPage 转换为list集合
            //如果noNeedAuthPage 里面的字符串 不等于 传进来的 字符串，则说明是需要登录，所以要验证当前是否登录了
            if (!Arrays.asList(noNeedAuthPage).contains(method)) {
                User user = (User) session.getAttribute("user");
                if (user == null) {
                    //当前没有登录，则进入登录页面
                    response.sendRedirect("loginPage");
                    return false;

                }
            }
        }
        //
        return true;
    }


    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * <p>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
