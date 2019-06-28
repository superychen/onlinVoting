package com.cqyc.shixun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cqyc.shixun.comm.exception.ExceptionEnums;
import com.cqyc.shixun.comm.exception.YcException;
import com.cqyc.shixun.domain.Results;
import com.cqyc.shixun.domain.Superstar;
import com.cqyc.shixun.domain.SysUser;
import com.cqyc.shixun.domain.VotingGroup;
import com.cqyc.shixun.mapper.ResultsMapper;
import com.cqyc.shixun.mapper.SuperstarMapper;
import com.cqyc.shixun.mapper.SysUserMapper;
import com.cqyc.shixun.service.IResultsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
@Service
public class ResultsServiceImpl extends ServiceImpl<ResultsMapper, Results> implements IResultsService {

    @Autowired
    private ResultsMapper resultsMapper;

    @Autowired
    private SuperstarMapper superstarMapper;

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 先从数据库获取结果,后期考虑redis
     */
    public List<Results> starPkRes(Integer[] starIds, Integer starGroup) {
        Results results = new Results();
        results.setGroupNum(starGroup);
        List<Results> results1 = resultsMapper.selectList(new QueryWrapper<Results>().lambda().setEntity(results));
        if(CollectionUtils.isEmpty(results1)){
            throw new YcException(ExceptionEnums.RESULT_SELECT_NULL);
        }
        return results1;
    }

    /**
     * 先查询出该用户已经投票的组数,然后往后面添加组数
     */
    @Transactional
    public void votingResult(VotingGroup votingGroup, SysUser user) {
        //得到选手基本信息
        Superstar superstar = superstarMapper.selectOne(new QueryWrapper<Superstar>().lambda().eq(Superstar::getStarName, votingGroup.getStarName()));
        //根据选手id插入结果的投票
        Results results1 = resultsMapper.selectOne(new QueryWrapper<Results>().lambda().eq(Results::getStarId, superstar.getId()));
        //插入新的结果
        Results results = new Results();

        results.setResultNum(results1.getResultNum() + 1);
        //修改票数
        resultsMapper.update(results,new UpdateWrapper<Results>().lambda().eq(Results::getStarId,superstar.getId()));

        //然后将这组的组号传给user，表示已经投过票了
        SysUser sysUser = new SysUser();
        sysUser.setId(user.getId());
        //将以前
        sysUser.setVotingGroup(user.getVotingGroup() + ";" + votingGroup.getVotingGroup());

        int i = userMapper.updateById(sysUser);

        if( i != 1){
            throw new YcException(ExceptionEnums.USER_UPDATE_VOTING_GROUP_ERROR);
        }
    }

    /**
     *  判断用户投票过的有没有包含当前组
     */
    public Boolean isVoting(String groupNum,SysUser user) {
        SysUser sysUser = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getId, user.getId()));
        String[] voting_group = sysUser.getVotingGroup().split(";");
        for (String s : voting_group) {
            System.out.println("s = " + s);
            //如果传过去的组号包含在用户已经投票过的组数里面,就返回true,则不允许投票
            if(StringUtils.equals(groupNum,s)){
                return true;
            }
        }
        return false;
    }
}
