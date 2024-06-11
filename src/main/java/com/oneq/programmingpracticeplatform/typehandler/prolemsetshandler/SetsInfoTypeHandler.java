package com.oneq.programmingpracticeplatform.typehandler.prolemsetshandler;

import com.oneq.programmingpracticeplatform.model.entity.problemsets.SetsInfo;
import com.oneq.programmingpracticeplatform.typehandler.ObjectToJsonTypeHandler;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.MappedTypes;
import springfox.documentation.swagger2.mappers.LicenseMapper;

@MappedTypes({SetsInfo.class})
public class SetsInfoTypeHandler extends ObjectToJsonTypeHandler<SetsInfo> {
    public SetsInfoTypeHandler() {
        super(SetsInfo.class);
    }
}
