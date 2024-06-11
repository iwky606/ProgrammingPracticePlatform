package com.oneq.programmingpracticeplatform.model.dto.problemsets;

import com.oneq.programmingpracticeplatform.model.entity.problemsets.SetsInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class EditSetsInfoRequest implements Serializable {
    private long id;
    private Long openTime;
    private Long endTime;
    private SetsInfo info;
    private static final long serialVersionUID = 1;
}
