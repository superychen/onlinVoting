package com.cqyc.shixun.controller;


import com.cqyc.shixun.comm.MyPage;
import com.cqyc.shixun.comm.exception.ExceptionEnums;
import com.cqyc.shixun.comm.exception.YcException;
import com.cqyc.shixun.domain.Superstar;
import com.cqyc.shixun.service.ISuperstarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
@Controller
@RequestMapping("/superstar")
public class SuperstarController {

    @Autowired
    private ISuperstarService superstarService;

    /**
     * 分页获取所有选手的信息
     */
    @GetMapping("superstars")
    public ResponseEntity<MyPage<Superstar>> selAllSuperstars(@RequestParam(value = "starName",required = false) String starName,
                                                              Integer page,Integer limit){
       MyPage<Superstar> myPage = superstarService.selAllSuperstars(starName,page,limit);
       myPage.setData(myPage.getRecords());
       myPage.setCount(myPage.getTotal());
       return ResponseEntity.ok(myPage);
    }

    /**
     * 查询当前是否有选手ID
     */
    @GetMapping("superstarId")
    public ResponseEntity<Superstar> isSuperstarId(@RequestParam(value = "id",required = false) Integer id){
        if(id == null){
            return ResponseEntity.ok(new Superstar());
        }
        return ResponseEntity.ok(superstarService.isSuperstarId(id));
    }

    /**
     * 新增选手信息
     */
    @PostMapping("createStar")
    public ResponseEntity<Object> createStar(@RequestBody @Validated Superstar superstar, BindingResult result){
        Map<String, String> map = new HashMap<>();
        if(result.hasFieldErrors()){
            throw new YcException(ExceptionEnums.SUPER_STAR_INSERT_ERROR);
        }
        superstarService.createStar(superstar);
        map.put("status","OK");
        map.put("msg","选手插入成功");
        return ResponseEntity.ok(map);
    }

    /**
     * 修改选手信息
     */
    @PutMapping("updateStar")
    public ResponseEntity<Object> updateStar(@RequestBody @Validated Superstar superstar, BindingResult result){
        Map<String, String> map = new HashMap<>();
        if(result.hasFieldErrors()){
            throw new YcException(ExceptionEnums.SUPER_STAR_UPDATE_ERROR);
        }
        superstarService.updateStar(superstar);
        map.put("status","OK");
        map.put("msg","选手修改成功");
        return ResponseEntity.ok(map);
    }


    /**
     *  单个删除
     */
    @DeleteMapping("delStar")
    public ResponseEntity<Object> delStar(@RequestParam("id") Integer id){
        Map<String, String> map = new HashMap<>();
        superstarService.delStar(id);
        map.put("status","OK");
        map.put("msg","删除成功");
        return ResponseEntity.ok(map);
    }

    /**
     * 批量删除
     */
    @DeleteMapping("delStars")
    public ResponseEntity<Object> delStars(@RequestParam(value = "ids",required = false) Integer[] ids){
        Map<String, String> map = new HashMap<>();
        superstarService.delStars(ids);
        map.put("status","ok");
        map.put("msg","批量删除成功");
        return ResponseEntity.ok(map);
    }
}
