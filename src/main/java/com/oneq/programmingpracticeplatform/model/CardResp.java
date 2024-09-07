package com.oneq.programmingpracticeplatform.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CardResp {
    private CardParamMap cardData;
    private CardParamMap userPrivateData;
    private String cardTemplateId;
    private String outTrackId;
    private CardOptions cardOptions;

    @Data
    @Accessors(chain = true)
    public static class CardParamMap {
        private String title;
        private String desc;
        private String link;
        private String pic;
    }

    @Data
    @Accessors(chain = true)
    public static class CardOptions {
        private boolean supportForward;
        private String lastMessage;
    }
}
