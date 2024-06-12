package com.oneq.programmingpracticeplatform.model.dto.problem;

import com.oneq.programmingpracticeplatform.model.entity.problem.JudgeConfig;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemDescription;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemSolution;
import com.oneq.programmingpracticeplatform.model.enums.ProblemVisibleEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EditProblemRequest implements Serializable {
    private long id;
    private String title;
    private ProblemDescription description;
    private List<String> tags;
    private ProblemSolution solution;
    private JudgeConfig judgeConfig;
    private List<Integer> judgeInputs;
    private List<Integer> judgeOutputs;
    private ProblemVisibleEnum visible;
    private static final long serialVersionUID = 1L;
}
