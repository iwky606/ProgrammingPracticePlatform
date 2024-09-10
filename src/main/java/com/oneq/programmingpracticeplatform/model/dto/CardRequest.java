package com.oneq.programmingpracticeplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * {
 * "extension": "{\"url\":\"https://www.dingtalk.com/unf****ing/test?id=1\"}",
 * "corpId": "corp1",
 * "userId": "user1"
 * }
 */
@Data
public class CardRequest {
    private String extension;
    private String corpId;
    private String userId;
}
