package com.cqyc.shixun.controller;


import com.cqyc.shixun.comm.exception.ExceptionEnums;
import com.cqyc.shixun.comm.exception.YcException;
import com.cqyc.shixun.domain.Results;
import com.cqyc.shixun.domain.SysUser;
import com.cqyc.shixun.domain.VotingGroup;
import com.cqyc.shixun.service.IResultsService;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/results")
public class ResultsController {

    @Autowired
    private IResultsService resultsService;

    /**
     * 访问结果页面的时候查询数据库中的结果
     */
    @GetMapping("starPk_res")
    public ResponseEntity<List<Results>> starPkRes(@RequestParam(value = "starIds",required = false) Integer[] starIds ,
                                                   @RequestParam("starGroup") Integer starGroup){
        return  ResponseEntity.ok(resultsService.starPkRes(starIds,starGroup));
    }

    /**
     * 修改用户已经投票过的组数
     */
    @PostMapping("voting_result")
    public ResponseEntity<Object> votingResult(@RequestBody VotingGroup votingGroup, HttpSession session){
        SysUser user = (SysUser) session.getAttribute("user");
        Map<String, String> map = new HashMap<>();
        if (user == null) {
            throw new YcException(ExceptionEnums.USER_NOT_FOUND);
        }
        resultsService.votingResult(votingGroup,user);
        map.put("code","200");
        map.put("msg","用户投票成功");
        return ResponseEntity.ok(map);
    }

    /**
     *  判断当前组该用户是否投过票
     */
    @GetMapping("isVoting")
    public ResponseEntity<Object> isVoting(@RequestParam("groupNum") String groupNum,HttpSession session){
        Map<String, Boolean> map = new HashMap<>();
        SysUser user = (SysUser) session.getAttribute("user");
        if (user == null) {
            throw new YcException(ExceptionEnums.USER_NOT_FOUND);
        }
        Boolean voting = resultsService.isVoting(groupNum,user);
        if(voting == true){
            map.put("voting",true);//表示已经投票
        }else {
            map.put("voting",false);//表示还没有投票
        }
        return ResponseEntity.ok(map);
    }
}
