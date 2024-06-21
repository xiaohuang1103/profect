package com.example.project01.model;

import com.leon.model.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Require implements Model {
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "设备类型")
    private String type;

    @Schema(description = "描述")
    private String describe;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "文件路径")
    private String fileUrl;

    @Schema(description = "路径")
    private String url;

    @Schema(description = "包")
    private String packageName;

    @Schema(description = "更新时间")
    private Timestamp updatedAt;

    @Schema(description = "强制更新")
    private Boolean forceUpdate;

}
