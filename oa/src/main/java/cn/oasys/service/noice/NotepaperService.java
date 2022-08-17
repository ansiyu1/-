package cn.oasys.service.noice;

import cn.oasys.pojo.notice.Notepaper;
import cn.oasys.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NotepaperService extends BaseService<Notepaper> {
    List<Notepaper> notepaperBy(Long userId);

    String upload(MultipartFile filePath) throws IOException;

    int insertNotepaper(Notepaper npaPer);
}
