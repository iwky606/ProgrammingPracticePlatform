package com.oneq.programmingpracticeplatform.controller;

import com.oneq.programmingpracticeplatform.model.CardResp;
import com.oneq.programmingpracticeplatform.model.dto.CardRequest;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@RestController
public class TestController {

    @Resource
    private ProblemService problemService;

    @GetMapping("/test")
    public void Hello(HttpServletRequest request) {
    }

    @RequestMapping(value = "/enhance", method = {RequestMethod.GET, RequestMethod.POST})
    public CardResp cardTest(@RequestBody(required = false) CardRequest req) {
        CardResp cardResp = new CardResp();
        cardResp.setCardTemplateId("ff32a16b-0d6d-4b89-87bb-5d1c853b670f");
        cardResp.setCardData(new CardResp.CardParamMap().setTitle("ODC").setDesc("ODC test").setPic("https://img.oneq.cc/avatar.jpg").setLink("https://odc.alipay.com"));
        cardResp.setOutTrackId(UUID.randomUUID().toString());
        cardResp.setCardOptions(new CardResp.CardOptions().setSupportForward(true));

        return cardResp;
    }
}
