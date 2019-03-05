package com.vborodin.onlineshop.productservice.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UploadFileResponse {
    String name;
    String uri;
    String type;
    long size;
}
