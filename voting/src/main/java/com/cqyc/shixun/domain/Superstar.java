package com.cqyc.shixun.domain;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableLogic;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.io.Serializable;
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
    public class Superstar extends Model<Superstar> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "选手图片不能为空")
    private String starImg;

    @NotBlank(message = "选手姓名不能为空")
    private String starName;

    @NotBlank(message = "选手年龄不能为空")
    private String starAge;

    @NotBlank(message = "选手性别不能为空")
    private String starSex;

    @NotBlank(message = "选手简介不能为空")
    private String starIntroduce;

    //逻辑删除，正常为0，不正常为1
    @TableLogic(value = "0",delval = "1")
    private String delFlag;

    private Integer isGroup;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
