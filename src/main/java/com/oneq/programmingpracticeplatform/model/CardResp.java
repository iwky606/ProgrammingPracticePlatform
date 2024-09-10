package com.oneq.programmingpracticeplatform.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class CardResp {
    private CardDataModel cardData;
    private CardDataModel userPrivateData;
    private String cardTemplateId;
    private String outTrackId;
    private CardOptions cardOptions;

    @Data
    @Accessors(chain = true)
    public static class CardDataModel {
        private Map<String,String> cardParamMap;
    }

    @Data
    @Accessors(chain = true)
    public static class CardOptions {
        private boolean supportForward;
        private String lastMessage;
    }
}
