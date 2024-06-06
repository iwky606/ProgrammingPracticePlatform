package com.oneq.programmingpracticeplatform.model.vo;

import cn.hutool.json.JSONUtil;
import com.oneq.programmingpracticeplatform.model.entity.problem.JudgeConfig;
import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemDescription;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemSolution;
import com.oneq.programmingpracticeplatform.model.enums.ProblemVisibleEnum;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

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
    private List<Integer> judgeInputs;
    private List<Integer> judgeOutputs;
    private ProblemVisibleEnum visible;

    // 竞赛模式不展示
    private List<String> tags;
    private ProblemSolution solution;

    public static ProblemVo objToVo(Problem problem) {
        if (problem == null) {
            return null;
        }
        ProblemVo problemVo = new ProblemVo();
        BeanUtils.copyProperties(problem, problemVo);
        return problemVo;
    }

    private static final long serialVersionUID = 1L;
}
