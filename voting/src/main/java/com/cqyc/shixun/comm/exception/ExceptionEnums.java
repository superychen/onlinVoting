package com.cqyc.shixun.comm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnums {
    USER_LOGIN_ERROR(500,"用户密码错误"),
    USER_REGISTER_ERROR(500,"用户注册失败"),
    USER_UPDATE_NOT_FOUNT(500,"用户修改时没有相关信息"),
    USER_DELETE_ERROR(500,"用户删除失败"),
    USER_UPDATE_ERROR(500,"用户修改失败"),
    SUPER_SELECT_ERROR(500,"选手查询失败"),
    UPLOAD_IMAGE_ERROR(500,"上传选手图片失败"),
    SUPER_STAR_INSERT_ERROR(500,"选手信息插入失败"),
    SUPER_STAR_UPDATE_ERROR(500,"选手信息修改失败"),
    SUPER_DELETE_ERROR(500,"选手信息删除失败"),
    SUPERSTAR_GROUP_IDS_ERROR(500,"选手分组为选择两个及其以上"),
    SUPERSTAR_HAS_BENN_GROUP(500,"分组中已经有选手被分组了哟"),
    STAR_GROUP_HAS_BENN_EXIST(500,"组号已经被占用了哟"),
    STAR_GROUP_UPDATE_ERROR(500,"分组修改失败"),
    STAR_GROUP_DELETE_ERROR(500,"分组删除失败"),
    RESULT_SELECT_NULL(500,"查询结果出错,结果为0"),
    USER_NOT_FOUND(500,"该用户可能没有找到或者没有登录"),
    USER_UPDATE_VOTING_GROUP_ERROR(500,"插入用户的投票组数失败"),
    ;
    private int code;
    private String msg;
}
