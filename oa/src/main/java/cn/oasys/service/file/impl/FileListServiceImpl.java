package cn.oasys.service.file.impl;

import cn.oasys.dao.attach.AttachmentMapper;
import cn.oasys.dao.file.FileListMapper;
import cn.oasys.dao.file.FilePathMapper;
import cn.oasys.pojo.attch.Attachment;
import cn.oasys.pojo.file.FileList;
import cn.oasys.pojo.file.FilePath;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.file.FileListService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("fileListService")
public class FileListServiceImpl extends BaseServiceImpl<FileList> implements FileListService {
    @Autowired
    FileListMapper fileListMapper;
    @Autowired
    AttachmentMapper attachmentMapper;
    @Autowired
    FilePathMapper filePathMapper;
    @Value("${file.root.path}")
    private String rootPath;
    @Override
    public int fileCount(Long userId) {
        return fileListMapper.fileCount(userId);
    }



    @Override
    public List<FileList> findFileByPath(Long filepath) {
        return fileListMapper.findByPathAndFileIsTrash(filepath,0L);
    }

    /**
     * 文件以及路径得同名处理
     * @param name
     * @param filepath
     * @param shuffix
     * @param num
     * @return
     */
    @Override
    public String onlyName(String name, FilePath filepath, String shuffix, int num, boolean isfile){
        Object f = null;
        if (isfile) {
            f = fileListMapper.findByFileNameAndPath(name, filepath.getPathId());
        }else{
            f = filePathMapper.findByPathNameAndParentId(name, filepath.getPathId());
        }
        if(f != null){
            int num2 = num -1;
            if(shuffix == null){
                name = name.replace("("+num2+")", "")+"("+num+")";
            }else{
                name = name.replace("."+shuffix,"").replace("("+num2+")", "")+"("+num+")"+"."+shuffix;
            }
            num += 1;
            return onlyName(name,filepath,shuffix,num,isfile);
        }
        return name;
    }

    /**
     * 保存文件以及附件
     * @param file
     * @param user
     * @param nowpath
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @Override
    public Object insertFile(MultipartFile file,User user,FilePath nowpath,boolean isfile) throws IllegalStateException, IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        File root = new File(this.rootPath,simpleDateFormat.format(new Date()));

        File savepath = new File(root,user.getUserName());
        //System.out.println(savePath.getPath());

        if (!savepath.exists()) {
            savepath.mkdirs();
        }

        String shuffix = FilenameUtils.getExtension(file.getOriginalFilename());
//        log.info("shuffix:{}",shuffix);
        String newFileName = UUID.randomUUID().toString().toLowerCase()+"."+shuffix;
        File targetFile = new File(savepath,newFileName);
        file.transferTo(targetFile);

        if(isfile){
            FileList filelist = new FileList();
            String filename = file.getOriginalFilename();
            filename = onlyName(filename,nowpath,shuffix,1,true);
            filelist.setFileName(filename);
            filelist.setFilePath(targetFile.getAbsolutePath().replace("\\", "/").replace(this.rootPath, ""));
            filelist.setFileShuffix(shuffix);
            filelist.setSize(file.getSize());
            filelist.setUploadTime(new Date());
            filelist.setPathId(nowpath);
            filelist.setContentType(file.getContentType());
            filelist.setFileUserId(user);
            fileListMapper.insertFileList(filelist);
            return filelist;
        }else{
            Attachment attachment=new Attachment();
            attachment.setAttachmentName(file.getOriginalFilename());
            attachment.setAttachmentPath(targetFile.getAbsolutePath().replace("\\", "/").replace(this.rootPath, ""));
            attachment.setAttachmentShuffix(shuffix);
            attachment.setAttachmentSize(file.getSize());
            attachment.setAttachmentType(file.getContentType());
            attachment.setUploadTime(new Date());
            attachment.setUserId(user.getUserId()+"");
            attachment.setModel("note");
            attachmentMapper.insert(attachment);
            return attachment;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void doShare(List<Long> checkfileids) {
        for (Long fileid : checkfileids) {

            FileList filelist = fileListMapper.selectByPrimaryKey(fileid);

            filelist.setFileIsshare(1L);
            fileListMapper.insertFileList(filelist);
        }
    }

    /**
     * 根据文件id 将文件放入回收站
     * @param checkFileIds
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void trashfile(List<Long> checkFileIds, Long setIstrash, Long userid) {
        for (Long fileid : checkFileIds) {
            FileList fileList = fileListMapper.selectByPrimaryKey(fileid);
            fileList.setFileIstrash(setIstrash);
            if(userid != null){
                fileList.setPathId(null);
            }

            fileListMapper.deleteByPrimaryKey(fileList.getFileId());
        }
    }

    /**
     * 重命名业务方法
     * @param name
     * @param renamefp
     * @param nowpathid
     * @param isfile
     */
    @Override
    public void rename(String name,Long renamefp,Long nowpathid,boolean isfile) {
        if (isfile) {
            //文件名修改
            FileList fl = fileListMapper.selectByPrimaryKey(renamefp);
//            String newname = onlyName(name, fl.getPathId(), fl.getFileShuffix(), 1, isfile);
            fl.setFileName(name);
            fileListMapper.updateByPrimaryKey(fl);
        } else {
            //文件夹名修改
            FilePath fp = filePathMapper.selectByPrimaryKey(renamefp);
            FilePath filepath = filePathMapper.selectByPrimaryKey(nowpathid);
            fp.setPathName(name);
            filePathMapper.updateByPrimaryKey(fp);
        }
    }

    /**
     * 文件复制
     * @param filelist
     */
    public void copyfile(FileList filelist,FilePath topath,boolean isfilein){
        System.out.println("测试路径："+filelist.getFilePath());
        File s = getFile(filelist.getFilePath());
        System.out.println("测试用户："+filelist.getFileUserId());
        User user = filelist.getFileUserId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        File root = new File(this.rootPath,simpleDateFormat.format(new Date()));
        File savepath = new File(root,user.getUserName());

        if (!savepath.exists()) {
            savepath.mkdirs();
        }

        String shuffix = filelist.getFileShuffix();
//        log.info("shuffix:{}",shuffix);
        String newFileName = UUID.randomUUID().toString().toLowerCase()+"."+shuffix;
        File t = new File(savepath,newFileName);

        copyfileio(s,t);

        FileList filelist1 = new FileList();
        String filename="";
        if(isfilein){
            filename = "拷贝 "+filelist.getFileName().replace("拷贝 ", "");
        }else{
            filename = filelist.getFileName();
        }
        filename = onlyName(filename,topath,shuffix,1,true);
        filelist1.setFileName(filename);
        filelist1.setFilePath(t.getAbsolutePath().replace("\\", "/").replace(this.rootPath, ""));
        filelist1.setFileShuffix(shuffix);
        filelist1.setSize(filelist.getSize());
        filelist1.setUploadTime(new Date());
        filelist1.setPathId(topath);
        filelist1.setContentType(filelist.getContentType());
        filelist1.setFileUserId(user);
        fileListMapper.insertFileList(filelist1);

    }

    /**
     * 得到文件
     * @param filepath
     * @return
     */
    @Override
    public File getFile(String filepath){
        return new File(this.rootPath,filepath);
    }

    /**
     * 本地文件复制
     * @param s
     * @param t
     */
    public void copyfileio(File s,File t){
        InputStream fis = null;
        OutputStream fos = null;

        try {
            fis = new BufferedInputStream(new FileInputStream(s));
            fos = new BufferedOutputStream(new FileOutputStream(t));
            byte[] buf = new byte[2048];
            int i ;
            while((i = fis.read(buf)) != -1){
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void copypath(Long mcpathid,Long topathid,boolean isfirst,Long userid){
        FilePath filepath = filePathMapper.selectByPrimaryKey(mcpathid);

        //第一个文件夹的复制
        FilePath copypath = new FilePath();
        copypath.setParentId(topathid);
        String copypathname = filepath.getPathName();
        if(isfirst){
            copypathname = "拷贝 "+filepath.getPathName().replace("拷贝 ", "");
        }
        copypath.setPathName(copypathname);
        copypath.setPathUserId(userid);
        filePathMapper.insert(copypath);

        //这一个文件夹下的文件的复制
        List<FileList> filelists = fileListMapper.findByPathAndFileIsTrash(filepath.getPathId(), 0L);
        for (FileList fileList : filelists) {
            copyfile(fileList,copypath,false);
        }

//        List<FilePath> filepathsons = filePathMapper.findByParentIdAndPathIstrash(filepath.getPathId(), 0L);
//
//        if(!filepathsons.isEmpty()){
//            for (FilePath filepathson : filepathsons) {
//                copypath(filepathson.getPathId(),copypath.getPathId(),false,userid);
//            }
//        }

    }

    /**
     * 复制和移动
     * @param fromwhere  1为移动  2 为复制
     */
    @Override
    @Transactional
    public void moveAndcopy(List<Long> mcfileids,List<Long> mcpathids,Long topathid,boolean fromwhere,Long userid){
        FilePath topath = filePathMapper.selectByPrimaryKey(topathid);
        if(fromwhere){
            System.out.println("这里是移动！！~~");
            if(!mcfileids.isEmpty()){
                System.out.println("fileid is not null");
                for (Long mcfileid : mcfileids) {
                    FileList filelist = fileListMapper.findByFileList(mcfileid);
                    String filename = onlyName(filelist.getFileName(),topath,filelist.getFileShuffix(),1,true);
                    filelist.setPathId(topath);
                    filelist.setFileName(filename);
                    fileListMapper.insertFileList(filelist);
                }
            }
            if(!mcpathids.isEmpty()){
                System.out.println("pathid is not null");
                for (Long mcpathid : mcpathids) {
                    FilePath filepath = filePathMapper.selectByPrimaryKey(mcpathid);
                    String name = onlyName(filepath.getPathName(), topath, null, 1, false);
                    filepath.setParentId(topathid);
                    filepath.setPathName(name);
                    filepath.setPathUserId(filepath.getPathUserId());
                    filepath.setPathIstrash(filepath.getPathIstrash());
                    filePathMapper.insertFilePath(filepath);
                }
            }
        }else{
            System.out.println("这里是复制！！~~");
            if(!mcfileids.isEmpty()){
                System.out.println("fileid is not null");
                for (Long mcfileid : mcfileids) {
                    FileList filelist = fileListMapper.findByFileList(mcfileid);
                    copyfile(filelist,topath,true);
                }
            }
            if(!mcpathids.isEmpty()){
                System.out.println("pathid is not null");
                for (Long mcpathid : mcpathids) {
                    copypath(mcpathid, topathid, true, userid);
                }
            }
        }
    }

    @Override
    public List<FileList> findByUserAndContentType(User user, String contenttype, Long istrash) {
        return fileListMapper.findByUserAndContentType(user.getUserId(),contenttype,istrash);
    }

    @Override
    public List<FileList> findDocument(Long userId) {
        return fileListMapper.findDocument(userId);
    }

    @Override
    public FileList findByFileList(Long mcFileId) {
        return fileListMapper.findByFileList(mcFileId);
    }

    @Override
    public List<FileList> selectByFileIsShareAndFileIsTrash(Long isShare, Long isTrash) {
        return fileListMapper.selectByFileIsShareAndFileIsTrash(isShare,isTrash);
    }


    /**
     * 保存文件以及附件
     * @param file
     * @param user
     * @param nowPath
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @Override
    public Object saveFile(MultipartFile file, User user, FilePath nowPath, boolean isFile) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        File root = new File(this.rootPath,simpleDateFormat.format(new Date()));

        File savepath = new File(root,user.getUserName());
        //System.out.println(savePath.getPath());

        if (!savepath.exists()) {
            savepath.mkdirs();
        }

        String shuffix = FilenameUtils.getExtension(file.getOriginalFilename());
//        log.info("shuffix:{}",shuffix);
        String newFileName = UUID.randomUUID().toString().toLowerCase()+"."+shuffix;
        File targetFile = new File(savepath,newFileName);
        file.transferTo(targetFile);

        if(isFile){
            FileList filelist = new FileList();
            String filename = file.getOriginalFilename();
            filename = onlyName(filename,nowPath,shuffix,1,true);
            filelist.setFileName(filename);
            filelist.setFilePath(targetFile.getAbsolutePath().replace("\\", "/").replace(this.rootPath, ""));
            filelist.setFileShuffix(shuffix);
            filelist.setSize(file.getSize());
            filelist.setUploadTime(new Date());
            filelist.setPathId(nowPath);
            filelist.setContentType(file.getContentType());
            filelist.setFileUserId(user);
            fileListMapper.insertFileList(filelist);
            return filelist;
        }else{
            Attachment attachment=new Attachment();
            attachment.setAttachmentName(file.getOriginalFilename());
            attachment.setAttachmentPath(targetFile.getAbsolutePath().replace("\\", "/").replace(this.rootPath, ""));
            attachment.setAttachmentShuffix(shuffix);
            attachment.setAttachmentSize(file.getSize());
            attachment.setAttachmentType(file.getContentType());
            attachment.setUploadTime(new Date());
            attachment.setUserId(user.getUserId()+"");
            attachment.setModel("note");
            attachmentMapper.insertSelective(attachment);
            return attachment;
        }
    }

    /**
     *修改附件
     * @param file
     * @param user
     * @param nowPath
     * @param attId
     * @return
     * @throws IOException
     */
    @Override
    public Integer updateAtt(MultipartFile file, User user, FilePath nowPath, Long attId) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        File root = new File(this.rootPath,simpleDateFormat.format(new Date()));

        File savepath = new File(root,user.getUserName());
        //System.out.println(savePath.getPath());

        if (!savepath.exists()) {
            savepath.mkdirs();
        }
        if(!file.isEmpty()){
            String shuffix = FilenameUtils.getExtension(file.getOriginalFilename());
            String newFileName = UUID.randomUUID().toString().toLowerCase()+"."+shuffix;
            File targetFile = new File(savepath,newFileName);
            file.transferTo(targetFile);


            return attachmentMapper.updateAtt(file.getOriginalFilename(),
                    targetFile.getAbsolutePath().replace("\\", "/").replace(this.rootPath, ""), shuffix, file.getSize(),
                    file.getContentType(), new Date(), attId);
        }
        return 0;
    }

    /**
     * 获取附件
     *
     * @param att
     * @return
     */
    @Override
    public File get(Attachment att) {
        return new File(this.rootPath+att.getAttachmentPath());
    }

}
