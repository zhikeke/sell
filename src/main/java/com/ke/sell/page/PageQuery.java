package com.ke.sell.page;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/7 17:01
 */
public class PageQuery {
    @Getter
    @Setter
    @Min(value = 0, message = "当前页码不合法")
    private int page = 0;

    @Getter
    @Setter
    @Min(value = 1, message = "每页展示数量不合法")
    private int size = 10;

    @Setter
    private int offset;

    public int getOffset() {
        return page * size;
    }
}
