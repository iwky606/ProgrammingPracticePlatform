package com.oneq.programmingpracticeplatform.typehandler.enumhandler;

import com.oneq.programmingpracticeplatform.model.enums.ProblemVisibleEnum;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes({ProblemVisibleEnum.class})
public class ProblemVisibleHandler extends BaseEnumHandler<ProblemVisibleEnum> {
    public ProblemVisibleHandler() {
        super(ProblemVisibleEnum.class);
    }
}
