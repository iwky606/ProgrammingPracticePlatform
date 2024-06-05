package com.oneq.programmingpracticeplatform.typehandler;

import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemDescription;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(ProblemDescription.class)
public class ProblemDescriptionTypeHandler extends ObjectToJsonTypeHandler<ProblemDescription> {
    public ProblemDescriptionTypeHandler() {
        super(ProblemDescription.class);
    }
}
