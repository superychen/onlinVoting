package com.cqyc.shixun.domain.vo;

import com.cqyc.shixun.domain.StarGroup;
import com.cqyc.shixun.domain.Superstar;
import lombok.Data;


/**
 * @Description:
 * @Author:
 * @Date:
 */
@Data
public class StarGroupVo extends StarGroup {
    private Superstar superstar;
}
