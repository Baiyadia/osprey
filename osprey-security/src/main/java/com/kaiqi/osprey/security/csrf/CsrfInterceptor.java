package com.kaiqi.osprey.security.csrf;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangs
 * @title: CsrfInterceptor
 * @package com.kaiqi.osprey.security.csrf
 * @description: TODO
 * @date 2022-01-14 11:14
 */
public class CsrfInterceptor extends HandlerInterceptorAdapter {

    private static String refererPattern;

    public CsrfInterceptor(String refererPattern) {
        this.refererPattern = refererPattern;
    }

    /**
     * 用于在登录前验证 _csrf 参数
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        HttpSession session = request.getSession();
//        String _csrfByForm = request.getParameter("_csrf");  //表单中的值
//        String _csrfBySession = String.valueOf(session.getAttribute("_csrf"));  //session中的值
//        session.removeAttribute("_csrf");  //使用之后从session中删掉
//
//        //验证是否存在CSRF攻击
//        if (StringUtils.isNotBlank(_csrfByForm) && StringUtils.isNotBlank(_csrfBySession) && _csrfByForm.equals(_csrfBySession)) {
//            return true;
//        } else {
//            response.setContentType("text/html;charset=utf-8");
//            response.setStatus(403);
//
//            //页面友好提示信息
//            OutputStream oStream = response.getOutputStream();
//            oStream.write("请不要重复提交请求，返回原始页面刷新后再次尝试！！！".getBytes("UTF-8"));
//
//            return false;
//        }

        //TODO check csrf
        if (false) {
            throw new CsrfInterceptException();
        }
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
