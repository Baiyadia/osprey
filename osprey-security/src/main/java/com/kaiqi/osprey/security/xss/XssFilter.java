package com.kaiqi.osprey.security.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangs
 * @title: XssFilter
 * @package com.kaiqi.osprey.security.xss
 * @description: TODO
 * @date 2022-01-14 11:17
 */
public class XssFilter implements Filter {

    public static final String FILTER_POLICY = "filterPolicy";
    public static final String EXCLUDE_URL_PATTERNS = "excludeUrlPatterns";

    // XSS处理Map
    private static Map<String, String> xssMap = new LinkedHashMap();

    public void init(FilterConfig filterConfig) throws ServletException {

        String filterPolicy = filterConfig.getInitParameter(FILTER_POLICY);
        String excludeUrlPatterns = filterConfig.getInitParameter(EXCLUDE_URL_PATTERNS);

        // 含有脚本： script
        xssMap.put("[s|S][c|C][r|R][i|C][p|P][t|T]", "");
        // 含有脚本 javascript
        xssMap.put("[\\\"\\\'][\\s]*[j|J][a|A][v|V][a|A][s|S][c|C][r|R][i|I][p|P][t|T]:(.*)[\\\"\\\']", "\"\"");
        // 含有函数： eval
        xssMap.put("[e|E][v|V][a|A][l|L]\\((.*)\\)", "");
        // 含有符号 <
        xssMap.put("<", "&lt;");
        // 含有符号 >
        xssMap.put(">", "&gt;");
        // 含有符号 (
        xssMap.put("\\(", "(");
        // 含有符号 )
        xssMap.put("\\)", ")");
        // 含有符号 '
        xssMap.put("'", "'");
        // 含有符号 "
        xssMap.put("\"", "\"");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 强制类型转换 HttpServletRequest
        HttpServletRequest httpReq = (HttpServletRequest) request;
        // 构造HttpRequestWrapper对象处理XSS
        XssHttpRequestWrapper httpReqWarp = new XssHttpRequestWrapper(httpReq, xssMap);
        //
        chain.doFilter(httpReqWarp, response);
    }

    public void destroy() {

    }
}
