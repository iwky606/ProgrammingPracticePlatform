package com.oneq.programmingpracticeplatform.typehandler;

import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemSolution;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(ProblemSolution.class)
public class ProblemSolutionTypeHandler extends ObjectToJsonTypeHandler<ProblemSolution> {
    public ProblemSolutionTypeHandler() {
        super(ProblemSolution.class);
    }
}
