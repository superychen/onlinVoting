package com.cqyc.shixun.mapper;

import com.cqyc.shixun.domain.Results;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
@Repository
public interface ResultsMapper extends BaseMapper<Results> {

    int deleteEntity( @Param("results") Results results);
}
