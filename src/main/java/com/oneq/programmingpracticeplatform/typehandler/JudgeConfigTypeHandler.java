package com.oneq.programmingpracticeplatform.typehandler;

import com.oneq.programmingpracticeplatform.model.entity.problem.JudgeConfig;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes({JudgeConfigTypeHandler.class})
public class JudgeConfigTypeHandler extends ObjectToJsonTypeHandler<JudgeConfig> {
    public JudgeConfigTypeHandler() {
        super(JudgeConfig.class);
    }
}
