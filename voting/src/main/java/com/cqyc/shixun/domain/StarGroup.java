package com.cqyc.shixun.domain;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

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
    public class StarGroup extends Model<StarGroup> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "star_id")
    private Integer starId;

    private Integer starGroup;

    private String description;

    private String groupImg;

    @Override
    protected Serializable pkVal() {
        return this.starId;
    }

}
