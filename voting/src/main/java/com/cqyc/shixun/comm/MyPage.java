package com.cqyc.shixun.comm;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author:
 * @Date:
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class MyPage<T> extends Page<T> {

    private String selectStr;
    private int code=0;
    private String msg;
    private Long count;//总条数
    private List<T> data = new ArrayList<>();
    public MyPage(long current, long size) {
        super(current, size);
    }
}
