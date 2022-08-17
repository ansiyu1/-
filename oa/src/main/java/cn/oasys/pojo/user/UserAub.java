package cn.oasys.pojo.user;

import lombok.Data;

import java.util.Date;
@Data
public class UserAub {
    private Long processId;

    private String typeName;

    private Long deeplyId;    //紧急程度

    private String processName;

    private String userName;//申请人姓名

    private Date applyTime;

    private Long statusId;//审核人状态
}
