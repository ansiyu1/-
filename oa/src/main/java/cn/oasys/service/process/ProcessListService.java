package cn.oasys.service.process;

import cn.oasys.pojo.ProcessList;
import cn.oasys.service.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.List;

public interface ProcessListService extends BaseService<ProcessList> {
    List<ProcessList> processBy(Long userId);
    ProcessList byUserIdAndTitle(Long userId,Long proId);

    String numberTo(Double money);

    ProcessList selectThisApplication(Long proId);


    PageInfo<ProcessList> findByUserId(Long userId, int page, int size);

    ProcessList selectId(Long id);

    int insertProcess(ProcessList pro);
}
