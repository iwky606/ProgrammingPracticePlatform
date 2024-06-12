package com.oneq.programmingpracticeplatform.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemListVo {
    private int total;
    private List<ProblemVo> problems;
}
