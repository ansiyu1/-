package cn.oasys.service.attch.impl;

import cn.oasys.dao.attach.AttachmentMapper;
import cn.oasys.pojo.attch.Attachment;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.attch.AttachmentService;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("attachmentService")
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment> implements AttachmentService {

    @Value("${file.root.path}")
    private String rootpath;
    /**
     * 上传附件
     * @throws IOException
     * @throws IllegalStateException
     */
    @Override
    public Attachment upload(MultipartFile file,User mu) throws IllegalStateException, IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        File root = new File(rootpath,simpleDateFormat.format(new Date()));
        File savepath = new File(root,mu.getUserName());

        if (!savepath.exists()) {
            savepath.mkdirs();
        }
        String fileName=file.getOriginalFilename();
        if(!StringUtil.isEmpty(fileName)){
            String suffix= FilenameUtils.getExtension(fileName);
            String newFileName = UUID.randomUUID().toString().toLowerCase()+"."+suffix;
            File targetFile = new File(savepath,newFileName);
            file.transferTo(targetFile);

            Attachment attachment=new Attachment();
            attachment.setAttachmentName(file.getOriginalFilename());
            attachment.setAttachmentPath(targetFile.getAbsolutePath().replace("\\", "/").replace(rootpath, ""));
            attachment.setAttachmentShuffix(suffix);
            attachment.setAttachmentSize(file.getSize());
            attachment.setAttachmentType(file.getContentType());
            attachment.setUploadTime(new Date());
            attachment.setUserId(mu.getUserId()+"");

            return attachment;
        }
        return null;
    }
}
