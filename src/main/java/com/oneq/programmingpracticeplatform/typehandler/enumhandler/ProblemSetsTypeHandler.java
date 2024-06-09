package com.oneq.programmingpracticeplatform.typehandler.enumhandler;


import com.oneq.programmingpracticeplatform.model.enums.ProblemSetsTypeEnum;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes({ProblemSetsTypeEnum.class})
public class ProblemSetsTypeHandler extends BaseEnumHandler<ProblemSetsTypeEnum> {
    public ProblemSetsTypeHandler() {
        super(ProblemSetsTypeEnum.class);
    }
}
