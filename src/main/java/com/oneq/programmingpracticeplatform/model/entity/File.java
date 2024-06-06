package com.oneq.programmingpracticeplatform.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private Long id;
    private String fileName;
    private byte[] data;
}
