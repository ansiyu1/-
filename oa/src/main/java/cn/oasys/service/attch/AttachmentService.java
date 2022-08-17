package cn.oasys.service.attch;

import cn.oasys.pojo.attch.Attachment;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AttachmentService extends BaseService<Attachment> {
    Attachment upload(MultipartFile file, User tu) throws IOException;
}
