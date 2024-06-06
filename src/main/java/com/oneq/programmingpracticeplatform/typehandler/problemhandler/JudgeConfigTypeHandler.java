package com.oneq.programmingpracticeplatform.typehandler.problemhandler;

import com.oneq.programmingpracticeplatform.model.entity.problem.JudgeConfig;
import com.oneq.programmingpracticeplatform.typehandler.ObjectToJsonTypeHandler;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes({JudgeConfigTypeHandler.class})
public class JudgeConfigTypeHandler extends ObjectToJsonTypeHandler<JudgeConfig> {
    public JudgeConfigTypeHandler() {
        super(JudgeConfig.class);
    }
}
