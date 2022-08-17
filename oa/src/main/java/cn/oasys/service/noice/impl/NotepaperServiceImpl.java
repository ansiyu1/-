package cn.oasys.service.noice.impl;

import cn.oasys.dao.notice.NotepaperMapper;
import cn.oasys.pojo.notice.Notepaper;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.noice.NotepaperService;
import com.github.pagehelper.util.StringUtil;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service("notepaperService")
public class NotepaperServiceImpl extends BaseServiceImpl<Notepaper> implements NotepaperService {
    @Autowired
    NotepaperMapper notepaperMapper;
    @Value("${img.rootpath}")
    private String rootpath;

    @Override
    public List<Notepaper> notepaperBy(Long userId) {
        return notepaperMapper.notepaperBy(userId);
    }

    /**
     * 上传头像
     * @throws IOException
     * @throws IllegalStateException
     */
    @Override
    public String upload(MultipartFile file) throws IllegalStateException, IOException {
        System.out.println(file);
        File dir=new File(rootpath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        String fileName=file.getOriginalFilename();
        if(!StringUtil.isEmpty(fileName)){

            String suffix= FilenameUtils.getExtension(fileName);

            String newFileName = UUID.randomUUID().toString().toLowerCase() + "." + suffix;
            File targetFile = new File(dir,newFileName);
            file.transferTo(targetFile);
            System.out.println(newFileName+"mmm");
            String imgpath=targetFile.getPath().replace("\\", "/").replace(rootpath, "");

            System.out.println(imgpath);

            return imgpath;
        }else{
            System.out.println("测试");
            return null;
        }

    }

    @Override
    public int insertNotepaper(Notepaper npaPer) {
        return notepaperMapper.insertNotepaper(npaPer);
    }
}
