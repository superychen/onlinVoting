package com.cqyc.shixun.domain;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableLogic;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.io.Serializable;

    import com.fasterxml.jackson.annotation.JsonView;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

    import javax.validation.constraints.NotBlank;

/**
* <p>
    * 
    * </p>
*
* @author cqyc
* @since 2019-06-17
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysUser extends Model<SysUser> {


    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "登录名不能为空")
    private String loginName;

    @NotBlank(message = "密码不能为空")
    private String passwrod;

    private String userType;

    private String votingGroup;

    //逻辑删除，正常为0，不正常为1
    @TableLogic(value = "0",delval = "1")
    private String delFlag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
