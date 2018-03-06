package com.jrelax.web.open.controller;

import com.jrelax.config.JRelaxSystemConfigHelper;
import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.third.captcha.color.ColorFactory;
import com.jrelax.third.captcha.color.RandomColorFactory;
import com.jrelax.third.captcha.color.SingleColorFactory;
import com.jrelax.third.captcha.filter.predefined.*;
import com.jrelax.third.captcha.service.ConfigurableCaptchaService;
import com.jrelax.third.captcha.utils.encoder.EncoderHelper;
import com.jrelax.third.captcha.word.RandomWordFactory;
import com.jrelax.web.support.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * 生成验证码
 *
 * @author zenghao
 */
@Controller
@RequestMapping("/open/captcha")
public class CaptchaController extends BaseController<Object> {
    private static ConfigurableCaptchaService captchaService = new ConfigurableCaptchaService();
    private final String defaultCharts = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
    private final int defaultLength = 4;
    static {
        Random random = new Random();
        captchaService.setColorFactory(x -> {
            int[] c = new int[3];
            int i = random.nextInt(c.length);
            for (int fi = 0; fi < c.length; fi++) {
                if (fi == i) {
                    c[fi] = random.nextInt(71);
                } else {
                    c[fi] = random.nextInt(256);
                }
            }
            return new Color(c[0], c[1], c[2]);
        });

    }

    @RequestMapping(method = {RequestMethod.GET})
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
        }
        setResponseHeaders(response);
        if(ApplicationCommon.DEBUG){
            RandomWordFactory wordFactory = (RandomWordFactory) captchaService.getWordFactory();
            wordFactory.setCharacters("1");
            wordFactory.setMaxLength(defaultLength);
            wordFactory.setMinLength(defaultLength);
            captchaService.setWordFactory(wordFactory);
            captchaService.setFilterFactory(new RippleFilterFactory());
        }else{
            //随机效果
            if(JRelaxSystemConfigHelper.getBoolean("system.captcha.style.random", true)){
                Random random = new Random();
                int type = random.nextInt(6);
                switch (type) {
                    case 0:
                        captchaService.setFilterFactory(new CurvesRippleFilterFactory(captchaService.getColorFactory()));
                        break;
                    case 1:
                        captchaService.setFilterFactory(new MarbleRippleFilterFactory());
                        break;
                    case 2:
                        captchaService.setFilterFactory(new DoubleRippleFilterFactory());
                        break;
                    case 3:
                        captchaService.setFilterFactory(new WobbleRippleFilterFactory());
                        break;
                    case 4:
                        captchaService.setFilterFactory(new DiffuseRippleFilterFactory());
                        break;
                    case 5:
                        captchaService.setFilterFactory(new RippleFilterFactory());
                        break;
                }
            }else{
                captchaService.setFilterFactory(new RippleFilterFactory());
            }

            //单一颜色效果
            if(JRelaxSystemConfigHelper.getBoolean("system.captcha.style.single", true)){
                captchaService.setColorFactory(new SingleColorFactory());
            }else{
                Random random = new Random();
                captchaService.setColorFactory(x -> {
                    int[] c = new int[3];
                    int i = random.nextInt(c.length);
                    for (int fi = 0; fi < c.length; fi++) {
                        if (fi == i) {
                            c[fi] = random.nextInt(71);
                        } else {
                            c[fi] = random.nextInt(256);
                        }
                    }
                    return new Color(c[0], c[1], c[2]);
                });
            }

            RandomWordFactory wordFactory = (RandomWordFactory) captchaService.getWordFactory();
            wordFactory.setCharacters(JRelaxSystemConfigHelper.get("system.captcha.charts", defaultCharts));
            wordFactory.setMaxLength(JRelaxSystemConfigHelper.getInt("system.captcha.length", defaultLength));
            wordFactory.setMinLength(JRelaxSystemConfigHelper.getInt("system.captcha.length", defaultLength));
            captchaService.setWordFactory(wordFactory);
        }
        String token = EncoderHelper.getChallangeAndWriteImage(captchaService, "gif", response.getOutputStream());
        token = token.toLowerCase();//不区分大小写
        session.setAttribute(ApplicationCommon.SESSION_CAPTCHA, token);//存入Session中
    }

    /**
     * 返回验证码图片
     *
     * @param response
     */
    private void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
    }
}
