package com.jrelax.core.web.resolver;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jrelax.kit.ObjectKit;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.jrelax.core.support.ApplicationCommon;

/**
 * 国际化支持类
 * @author zenghao
 *
 */
public class LocaleResolver extends AcceptHeaderLocaleResolver{
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Locale locale = (Locale)request.getAttribute(ApplicationCommon.LOCALE_REQUEST);
		if(ObjectKit.isNull(locale))
			locale = (Locale)request.getSession().getAttribute(ApplicationCommon.SESSION_LOCALE);
		if(ObjectKit.isNull(locale))
			return request.getLocale();
		else
			return locale;

	}
	
	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		request.setAttribute(ApplicationCommon.LOCALE_REQUEST, locale);//支持request
		request.getSession().setAttribute(ApplicationCommon.SESSION_LOCALE, locale);//支持session
	}
}
