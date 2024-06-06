package com.oneq.programmingpracticeplatform.typehandler.problemhandler;

import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemDescription;
import com.oneq.programmingpracticeplatform.typehandler.ObjectToJsonTypeHandler;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(ProblemDescription.class)
public class ProblemDescriptionTypeHandler extends ObjectToJsonTypeHandler<ProblemDescription> {
    public ProblemDescriptionTypeHandler() {
        super(ProblemDescription.class);
    }
}
