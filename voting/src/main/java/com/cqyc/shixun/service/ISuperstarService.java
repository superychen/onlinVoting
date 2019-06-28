package com.cqyc.shixun.service;

import com.cqyc.shixun.comm.MyPage;
import com.cqyc.shixun.domain.Superstar;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
public interface ISuperstarService extends IService<Superstar> {

    MyPage<Superstar> selAllSuperstars(String starName, Integer page, Integer limit);

    Superstar isSuperstarId(Integer id);

    void createStar(Superstar superstar);

    void updateStar(Superstar superstar);

    void delStar(Integer id);

    void delStars(Integer[] ids);
}
