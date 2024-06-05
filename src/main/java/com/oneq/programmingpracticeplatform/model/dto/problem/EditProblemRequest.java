package com.oneq.programmingpracticeplatform.model.dto.problem;

import com.oneq.programmingpracticeplatform.model.entity.problem.JudgeConfig;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemDescription;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemSolution;
import lombok.Data;

import java.util.List;

@Data
public class EditProblemRequest {
    private long id;
    private ProblemDescription description;
    private List<String> tags;
    private ProblemSolution solution;
    private JudgeConfig judgeConfig;
    private List<Integer> judgeInputs;
    private List<Integer> judgeOutputs;
}
