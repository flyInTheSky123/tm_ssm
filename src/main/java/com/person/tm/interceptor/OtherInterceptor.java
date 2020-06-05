package com.person.tm.interceptor;

import com.person.tm.pojo.Category;
import com.person.tm.pojo.OrderItem;
import com.person.tm.pojo.User;
import com.person.tm.service.CategoryService;
import com.person.tm.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 其他拦截器（拦截/fore*）：
 * 在postHandle()中：
 *  1，获取所有的分类。
 *  2，动态的获取contextPath,为前端的左上角赋值地址进行跳转到首页。
 *
 */
public class OtherInterceptor  extends HandlerInterceptorAdapter {
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;
    /**
     * 在业务处理器处理请求之前被调用(流程).
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */


    /**
     * 在处理请求之前进行操作,做以下操作
     * @param request
     * @param response
     * @param handle
     * @return
     */
    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handle){
     //   System.out.println("------------preHandle");
        return  true;

    }


    /**
     * 在处理请求之后，在视图之前做以下操作
     * @param response
     * @param request
     * @param handler
     * @param modelAndView
     */

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView)throws Exception{
        //System.out.println("------------postHandle");

        HttpSession session = request.getSession();
        //获取所有的分类，放在搜索框下面
        List<Category> cs = categoryService.list();
        session.setAttribute("cs",cs);


        /*这里是获取当前的contextPath:tmall_ssm,用与放在左上角，点击之后才能够跳转到首页，否则点击之后也仅仅停留在当前页面*/
        String contextPath = session.getServletContext().getContextPath();
        session.setAttribute("contextPath",contextPath);



    }


    /**
     * 1，可以在DispatcherService完全做完请求之后，进行资源的清理
     * 2，当拦截器报错之后，会从当前拦截器往回执行所有拦截器的afterCompletion（）
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
