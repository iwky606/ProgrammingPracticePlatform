package com.oneq.programmingpracticeplatform.model.vo;

import com.oneq.programmingpracticeplatform.model.entity.problem.JudgeConfig;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemDescription;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemSolution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemVo implements Serializable {
    private long id;
    private long creator;
    private String title;
    private ProblemDescription description;
    private JudgeConfig judgeConfig;

    // 竞赛模式不展示
    private List<String> tags;
    private ProblemSolution solution;

    private static final long serialVersionUID = 1L;
}
