package com.cqyc.shixun.controller;


import com.cqyc.shixun.comm.MyPage;
import com.cqyc.shixun.comm.exception.ExceptionEnums;
import com.cqyc.shixun.comm.exception.YcException;
import com.cqyc.shixun.domain.StarGroup;
import com.cqyc.shixun.domain.Superstar;
import com.cqyc.shixun.domain.vo.StarGroupVo;
import com.cqyc.shixun.service.IStarGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/starGroup")
public class StarGroupController {

    @Autowired
    private IStarGroupService starGroupService;

    /**
     * 根据选手插入到选手组中对应的组号
     */
    @PostMapping("insertGroup")
    public ResponseEntity<Object> insertGroup(@RequestParam(value = "ids",required = false) Integer[] ids,
                                              @RequestParam(value = "starGroup") Integer starGroup,
                                              @RequestParam(value = "description") String description){
        Map<String, String> map = new HashMap<>();
        if (ids.length < 2) {
            throw new YcException(ExceptionEnums.SUPERSTAR_GROUP_IDS_ERROR);
        }
        starGroupService.insertGroup(ids,starGroup,description);
        map.put("status","OK");
        map.put("msg","分组成功");
        return ResponseEntity.ok(map);
    }

    /**
     * 查询所有的分组信息，可以进行修改删除建议不进行修改和修改
     */
    @GetMapping("selectStarGroup")
    public ResponseEntity<MyPage<StarGroupVo>> selectStarGroup(@RequestParam(value = "starGroup",required = false) String starGroup,
                                                               Integer page, Integer limit){
        MyPage<StarGroupVo> myPage = starGroupService.selectStarGroup(starGroup,page,limit);
        myPage.setData(myPage.getRecords());
        myPage.setCount(myPage.getTotal());
        return ResponseEntity.ok(myPage);
    }

    /**
     * 修改分组的信息，不推荐
     */
    @PutMapping("/updateGroup")
    public ResponseEntity<Object> updateGroup(@RequestBody StarGroup starGroup){
        Map<String, String> map = new HashMap<>();
        starGroupService.updateGroup(starGroup);
        map.put("status","OK");
        map.put("msg","修改成功");
        return ResponseEntity.ok(map);
    }

    /**
     * 删除对应的分组数据
     */
    @DeleteMapping("delGroup")
    public ResponseEntity<Object> deleteGroup(@RequestParam("starId") Integer starId){
        Map<String, String> map = new HashMap<>();
        starGroupService.deleteGroup(starId);
        map.put("status","OK");
        map.put("msg","删除成功");
        return ResponseEntity.ok(map);
    }

    /**
     *  分组查询出每一组组号，显示在最开始的界面
     */
    @GetMapping("selGroup")
    public ResponseEntity<List<StarGroup>> selGroup(){
        return ResponseEntity.ok(starGroupService.selGroup());
    }

    /**
     * 根据分组的id查询出有哪些选手
     */
    @GetMapping("selStarName")
    public ResponseEntity<List<Superstar>> selStarName(@RequestParam("starGroup") Integer starGroup){
        return ResponseEntity.ok(starGroupService.selStarName(starGroup));
    }

}
