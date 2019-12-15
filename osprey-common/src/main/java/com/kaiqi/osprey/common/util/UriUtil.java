package com.kaiqi.osprey.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author wangs
 * @date 2017/12/9
 */
@Slf4j
public class UriUtil {

    public static String UrlDecoder(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        String decUrl = UrlDecoder(url, "UTF-8");
        return decUrl == null ? UrlDecoder(url, "GBK") : decUrl;
    }

    public static String UrlDecoder(String url, String enc) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            return URLDecoder.decode(url, enc);
        } catch (UnsupportedEncodingException e) {
            log.error("url Decoder error,url:{},error:{} ", url, e);
        }
        return null;
    }
}
