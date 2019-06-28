package com.cqyc.shixun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cqyc.shixun.comm.MyPage;
import com.cqyc.shixun.comm.exception.ExceptionEnums;
import com.cqyc.shixun.comm.exception.YcException;
import com.cqyc.shixun.domain.Results;
import com.cqyc.shixun.domain.StarGroup;
import com.cqyc.shixun.domain.Superstar;
import com.cqyc.shixun.domain.vo.StarGroupVo;
import com.cqyc.shixun.mapper.ResultsMapper;
import com.cqyc.shixun.mapper.StarGroupMapper;
import com.cqyc.shixun.mapper.SuperstarMapper;
import com.cqyc.shixun.service.IStarGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
public class StarGroupServiceImpl extends ServiceImpl<StarGroupMapper, StarGroup> implements IStarGroupService {

    @Autowired
    private StarGroupMapper starGroupMapper;

    @Autowired
    private SuperstarMapper superstarMapper;

    @Autowired
    private ResultsMapper resultsMapper;

    /**
     * 先分类查询ids是否选手已经被分组了，然后在查询组号是否已经被占用，最后进行插入
     */
    //开启事务
    @Transactional
    public void insertGroup(Integer[] ids, Integer starGroup,String description) {

        //查询组号是否已经被占用,如果被占用提示用户
        List<StarGroup> starGroups = starGroupMapper.selectList(new QueryWrapper<StarGroup>().lambda().eq(StarGroup::getStarGroup,starGroup));
        if(!CollectionUtils.isEmpty(starGroups)){
            throw new YcException(ExceptionEnums.STAR_GROUP_HAS_BENN_EXIST);
        }

        //分类查询ids是否选手已经被分组了
        List<Superstar> superstars = superstarMapper.selectBatchIds(Arrays.asList(ids));
        for (Superstar superstar : superstars) {
            if (superstar.getIsGroup() == 1) {
                throw new YcException(ExceptionEnums.SUPERSTAR_HAS_BENN_GROUP);
            }else {
                //进行插入分组内容
                StarGroup starGroup1 = new StarGroup();
                starGroup1.setStarGroup(starGroup);
                starGroup1.setStarId(superstar.getId());
                starGroup1.setDescription(description);
                starGroupMapper.insert(starGroup1);

                Results results = new Results();
                results.setStarId(superstar.getId());
                results.setGroupNum(starGroup);
                results.setResultNum(0);
                resultsMapper.insert(results);

                //修改选手的信息，将选手的is_group修改为1，表示已经插入的选手已经被分组
                Superstar superstar1 = new Superstar();
                superstar1.setId(superstar.getId());
                superstar1.setIsGroup(1);
                superstarMapper.updateById(superstar1);
            }
        }
    }


    /**
     * 查询所有的成功分组后的选手
     */
    public MyPage<StarGroupVo> selectStarGroup(String starGroup, Integer page, Integer limit) {
        MyPage<StarGroupVo> myPage = new MyPage<>(page, limit);
        if (StringUtils.isNotBlank(starGroup)) {
            myPage.setSelectStr(starGroup);
        }
        return starGroupMapper.selectStarGroup(myPage);
    }

    /**
     *  根绝star_id去修改分组信息
     */
    @Transactional
    public void updateGroup(StarGroup starGroup) {
        int i = starGroupMapper.updateById(starGroup);

        if(StringUtils.isNotBlank(starGroup.getGroupImg())){
            StarGroup starGroup1 = new StarGroup();
            //修改相关组号的图片
            starGroup1.setStarGroup(starGroup.getStarGroup());
            starGroup1.setGroupImg(starGroup.getGroupImg());
            starGroupMapper.update(starGroup1,new UpdateWrapper<StarGroup>().lambda().eq(StarGroup::getStarGroup,starGroup.getStarGroup()));
        }

        if (i != 1) {
            throw new YcException(ExceptionEnums.STAR_GROUP_UPDATE_ERROR);
        }
    }

    /**
     *  删除对应的数据
     */
    @Transactional
    public void deleteGroup(Integer starId) {
        int i = starGroupMapper.deleteById(starId);
        if (i != 1) {
            throw new YcException(ExceptionEnums.STAR_GROUP_DELETE_ERROR);
        }

        Superstar superstar = new Superstar();
        superstar.setId(starId);
        superstar.setIsGroup(0);
        int i1 = superstarMapper.updateById(superstar);

        Results results = new Results();
        results.setStarId(starId);
        resultsMapper.deleteEntity(results);

        if (i1 != 1) {
            throw new YcException(ExceptionEnums.SUPER_STAR_UPDATE_ERROR);
        }
    }

    /**
     *  分组查询出每一组组号，显示在最开始的界面
     */
    public List<StarGroup> selGroup() {
        List<StarGroup> starGroups = starGroupMapper.selGroup();
        System.out.println(Collections.unmodifiableCollection(starGroups));
        return starGroups;
    }

    /**
     *  查询选手pk场次的姓名
     */
    public List<Superstar> selStarName(Integer starGroup) {
        List<StarGroup> starGroups = starGroupMapper.selectList(new QueryWrapper<StarGroup>().setEntity(new StarGroup().setStarGroup(starGroup)));
        //遍历每组有多少的选手id，然后根据id查询
        ArrayList<Integer> ids = new ArrayList<>();
        for (StarGroup group : starGroups) {
            ids.add(group.getStarId());
        }
        //根据每个查询出来的id查询每个选手信息
        List<Superstar> superstars = superstarMapper.selectBatchIds(ids);
        System.out.println(Collections.unmodifiableCollection(superstars));
        return superstars;
    }

}
