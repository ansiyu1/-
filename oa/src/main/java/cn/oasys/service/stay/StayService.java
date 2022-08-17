package cn.oasys.service.stay;

import cn.oasys.pojo.stay.Stay;
import cn.oasys.service.BaseService;

import java.util.List;

public interface StayService extends BaseService<Stay> {
    List<Stay> selectStay();
}
