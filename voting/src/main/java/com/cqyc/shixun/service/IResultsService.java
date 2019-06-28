package com.cqyc.shixun.service;

import com.cqyc.shixun.domain.Results;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqyc.shixun.domain.SysUser;
import com.cqyc.shixun.domain.VotingGroup;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
public interface IResultsService extends IService<Results> {

    List<Results> starPkRes(Integer[] starIds, Integer starGroup);

    void votingResult(VotingGroup votingGroup, SysUser user);

    Boolean isVoting(String groupNum,SysUser user);
}
