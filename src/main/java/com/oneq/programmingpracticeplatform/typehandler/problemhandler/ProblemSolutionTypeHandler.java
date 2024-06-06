package com.oneq.programmingpracticeplatform.typehandler.problemhandler;

import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemSolution;
import com.oneq.programmingpracticeplatform.typehandler.ObjectToJsonTypeHandler;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(ProblemSolution.class)
public class ProblemSolutionTypeHandler extends ObjectToJsonTypeHandler<ProblemSolution> {
    public ProblemSolutionTypeHandler() {
        super(ProblemSolution.class);
    }
}
