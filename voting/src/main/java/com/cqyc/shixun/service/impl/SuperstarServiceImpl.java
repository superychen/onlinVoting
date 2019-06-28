package com.cqyc.shixun.service.impl;

import com.cqyc.shixun.comm.MyPage;
import com.cqyc.shixun.comm.exception.ExceptionEnums;
import com.cqyc.shixun.comm.exception.YcException;
import com.cqyc.shixun.domain.Superstar;
import com.cqyc.shixun.mapper.SuperstarMapper;
import com.cqyc.shixun.service.ISuperstarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
@Service
public class SuperstarServiceImpl extends ServiceImpl<SuperstarMapper, Superstar> implements ISuperstarService {

    @Autowired
    private SuperstarMapper superstarMapper;

    /**
     *  分页查询所有选手信息
     */
    public MyPage<Superstar> selAllSuperstars(String starName, Integer page, Integer limit) {
        MyPage<Superstar> myPage = new MyPage<>(page, limit);
        if (StringUtils.isNotBlank(starName)) {
            myPage.setSelectStr(starName);
        }
        return superstarMapper.selAllSuperstars(myPage);
    }

    /**
     * 查询当前页是否有id的值
     */
    public Superstar isSuperstarId(Integer id) {
        Superstar superstar = superstarMapper.selectById(id);
        if (superstar == null) {
            throw new YcException(ExceptionEnums.SUPER_SELECT_ERROR);
        }
        return superstar;
    }


    /**
     *  插入选手信息
     */
    @Transactional
    public void createStar(Superstar superstar) {
        int insert = superstarMapper.insert(superstar);
        if (insert != 1) {
            throw new YcException(ExceptionEnums.SUPER_STAR_UPDATE_ERROR);
        }
    }

    /**
     *  修改选手信息
     */
    @Transactional
    public void updateStar(Superstar superstar) {
        int i = superstarMapper.updateById(superstar);
        if (i != 1) {
            throw new YcException(ExceptionEnums.SUPER_STAR_UPDATE_ERROR);
        }
    }

    /**
     *  删除选手信息
     */
    @Transactional
    public void delStar(Integer id) {
        int i = superstarMapper.deleteById(id);
        if(i != 1){
            throw new YcException(ExceptionEnums.SUPER_DELETE_ERROR);
        }
    }

    /**
     * 批量删除选手信息
     */
    @Transactional
    public void delStars(Integer[] ids) {
        superstarMapper.deleteBatchIds(Arrays.asList(ids));
    }

}
