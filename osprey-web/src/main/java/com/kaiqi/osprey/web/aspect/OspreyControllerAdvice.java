package com.kaiqi.osprey.web.aspect;

import com.kaiqi.osprey.commons.ResponseResult;
import com.kaiqi.osprey.commons.enums.ErrorCodeEnum;
import com.kaiqi.osprey.exception.OspreyBizException;
import com.kaiqi.osprey.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangs
 * @title: OspreyControllerAdvice
 * @package com.kaiqi.osprey.web.aspect
 * @description: TODO
 * @date 2019-06-02 17:48
 */
@Slf4j
@ControllerAdvice
public class OspreyControllerAdvice {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initWebBinder(WebDataBinder binder) {
        //对日期的统一处理
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss.SSS"));
        //添加对数据的校验
        //binder.setValidator();
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttribute(HttpEntity<String> httpEntity, HttpServletRequest request, Model model) {
        // 打印请求日志
        log.info("【{}】{}?{} reqBody={}", request.getMethod(), request.getRequestURI(), request.getQueryString(), httpEntity.getBody());
        //model.addAttribute("attribute", "The Attribute");
    }

    /**
     * 捕获CustomException
     *
     * @param e
     * @return json格式类型
     */
    @ResponseBody
    @ExceptionHandler //指定拦截异常的类型
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //自定义浏览器返回状态码
    public ResponseResult customExceptionHandler(Throwable e) {
        log.error("");
        if (e instanceof OspreyBizException) {
            return ResultUtils.failure((OspreyBizException) e);
        }
        return ResultUtils.failure();
    }

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResultUtils.failure();
    }

    /**
     * 处理所有业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(OspreyBizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//自定义浏览器返回状态码
    @ResponseBody
    ResponseResult handleOspreyBizException(OspreyBizException e) {
        log.error(e.getMessage(), e);
        return ResultUtils.failure(e);
    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ResponseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ResultUtils.failure(ErrorCodeEnum.ARGUMENT_VALID_EXCEPTION);
    }

}
