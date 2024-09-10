package com.oneq.programmingpracticeplatform.controller;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.oneq.programmingpracticeplatform.common.DingTalkResponse;
import com.oneq.programmingpracticeplatform.model.CardResp;
import com.oneq.programmingpracticeplatform.model.dto.CardRequest;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@RestController
@Slf4j
public class TestController {

    @Resource
    private ProblemService problemService;

    @GetMapping("/test")
    public void Hello(HttpServletRequest request) {
    }

    @Autowired
    @Qualifier("stringRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping(value = "/enhance", method = {RequestMethod.GET, RequestMethod.POST})
    public DingTalkResponse<CardResp> cardTest(@RequestBody(required = false) CardRequest req) {
        CardResp cardResp = new CardResp();
        Object o = redisTemplate.opsForValue().get("templateId");
        cardResp.setCardTemplateId((String) o);
        // new CardResp.CardParamMap().setTitle("ODC").setDesc("ODC test").setPic("https://img.oneq.cc/avatar.jpg").setLink("https://odc.alipay.com")
        CardResp.CardDataModel cardDataModel = new CardResp.CardDataModel();
        Map<String, String> cardDataMap = new HashMap<>();
        cardDataMap.put("title", "ODC");
        cardDataMap.put("desc", "ODC是一个非常牛逼的平台");
        cardDataMap.put("link", "https://odc.alipay.com");
        cardDataMap.put("logo", "https://static.dingtalk.com/media/lALPM2dAsOrDkFnNA5zNA9E_977_924.png_620x10000q90.jpg");
        cardDataMap.put("isShowLink", "true");
        cardDataMap.put("appName", "ODC");
        cardDataMap.put("appHomePage", "https://odc.alipay.com");
        cardResp.setCardData(cardDataModel.setCardParamMap(cardDataMap));
        cardResp.setOutTrackId(UUID.randomUUID().toString());
        cardResp.setCardOptions(new CardResp.CardOptions().setSupportForward(true));
        try {
            log.info(JSON.toJSONString(cardResp));
            log.info(JSON.toJSONString(req));
        } catch (Exception e) {
            log.info("被调用了");
            e.printStackTrace();
            // log.error(e.toString());
        }

        return DingTalkResponse.ok(cardResp);
    }

    @RequestMapping(value = "/enhance2", method = {RequestMethod.GET, RequestMethod.POST})
    public CardResp cardTest2(@RequestBody(required = false) CardRequest req) {
        CardResp cardResp = new CardResp();
        Object o = redisTemplate.opsForValue().get("templateId");
        cardResp.setCardTemplateId((String) o);
        // new CardResp.CardParamMap().setTitle("ODC").setDesc("ODC test").setPic("https://img.oneq.cc/avatar.jpg").setLink("https://odc.alipay.com")
        CardResp.CardDataModel cardDataModel = new CardResp.CardDataModel();
        Map<String, String> cardDataMap = new HashMap<>();
        cardDataMap.put("title", "ODC");
        cardDataMap.put("desc", "ODC是一个非常牛逼的平台");
        cardDataMap.put("link", "https://odc.alipay.com");
        cardDataMap.put("logo", "https://static.dingtalk.com/media/lALPM2dAsOrDkFnNA5zNA9E_977_924.png_620x10000q90.jpg");
        cardDataMap.put("isShowLink", "true");
        cardDataMap.put("appName", "ODC");
        cardDataMap.put("appHomePage", "https://odc.alipay.com");
        cardResp.setCardData(cardDataModel.setCardParamMap(cardDataMap));
        cardResp.setOutTrackId(UUID.randomUUID().toString());
        cardResp.setCardOptions(new CardResp.CardOptions().setSupportForward(true));
        try {
            log.info(JSON.toJSONString(cardResp));
            log.info(JSON.toJSONString(req));
        } catch (Exception e) {
            log.info("被调用了");
            e.printStackTrace();
            // log.error(e.toString());
        }

        return cardResp;
    }
}
