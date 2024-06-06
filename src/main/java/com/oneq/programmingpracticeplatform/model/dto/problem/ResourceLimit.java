package com.oneq.programmingpracticeplatform.model.dto.problem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceLimit {
    private long timeLimit;// 毫秒
    private int memoryLimit;// kb
}