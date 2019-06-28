package com.cqyc.shixun.mapper;

import com.cqyc.shixun.comm.MyPage;
import com.cqyc.shixun.domain.StarGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqyc.shixun.domain.vo.StarGroupVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
@Repository
public interface StarGroupMapper extends BaseMapper<StarGroup> {

    MyPage<StarGroupVo> selectStarGroup(@Param("myPage") MyPage<StarGroupVo> myPage);

    List<StarGroup> selGroup();
}
