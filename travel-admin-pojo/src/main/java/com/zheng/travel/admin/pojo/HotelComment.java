package com.zheng.travel.admin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("travel_hotel_comment")
public class HotelComment  implements java.io.Serializable {

    // 主健
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    // 评论内容
    private String content;
    // 评论用户id
    private Long userid;
    // 评论用户昵称
    private String usernickname;
    // 评论用户头像
    private String useravatar;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新hijack
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 回复用户id
    private Long replyUserid;
    // 回复用户昵称
    private String replyUsernickname;
    // 回复用户头像
    private String replyUseravatar;
    // 评论的层级0第一级 1第二级
    private Long parentId;
    // 酒店ID
    private Long hotelId;
    // 点赞数
    private Integer zannum;

}