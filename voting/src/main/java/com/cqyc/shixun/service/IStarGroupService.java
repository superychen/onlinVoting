package com.cqyc.shixun.service;

import com.cqyc.shixun.comm.MyPage;
import com.cqyc.shixun.domain.StarGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqyc.shixun.domain.Superstar;
import com.cqyc.shixun.domain.vo.StarGroupVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
public interface IStarGroupService extends IService<StarGroup> {

    void insertGroup(Integer[] ids, Integer starGroup,String description);

    MyPage<StarGroupVo> selectStarGroup(String starGroup, Integer page, Integer limit);

    void updateGroup(StarGroup starGroup);

    void deleteGroup(Integer starId);

    List<StarGroup> selGroup();

    List<Superstar> selStarName(Integer starGroup);
}
