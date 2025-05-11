package cn.wscsoft.backend.common.dto;

import lombok.Data;

/**
 * 分页请求
 *
 * @author 观止
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long size = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = "asc";
}
