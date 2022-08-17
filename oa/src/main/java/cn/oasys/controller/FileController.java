package cn.oasys.controller;

import cn.oasys.pojo.file.FileList;
import cn.oasys.pojo.file.FilePath;
import cn.oasys.pojo.user.User;
import cn.oasys.service.file.FileListService;
import cn.oasys.service.file.FilePathService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class FileController {
    @Autowired
    FilePathService filePathService;
    @Autowired
    FileListService fileListService;
    /**
     * 第一次进入
     *
     * @param model
     * @return
     */
    @RequestMapping("filemanage")
    public String userManage(@SessionAttribute("user") User user, Model model) {
        FilePath filepath = filePathService.findByPathName(user.getUserName());
        if(filepath == null){
            FilePath filepath1 = new FilePath();
            filepath1.setParentId(1L);
            filepath1.setPathName(user.getUserName());
            filepath1.setPathUserId(user.getUserId());
            filePathService.insert(filepath1);
        }
        model.addAttribute("nowpath", filepath);
        model.addAttribute("paths", filePathService.selectPath(filepath.getPathId()));
        model.addAttribute("files", fileListService.findFileByPath(filepath.getPathId()));
        model.addAttribute("userrootpath",filepath);
        model.addAttribute("mcpaths",filePathService.selectPath(filepath.getPathId()));
        return "file/filemanage";
    }

    /**
     * 进入指定文件夹 的controller方法
     *
     * @param pathId
     * @param model
     * @return
     */
    @RequestMapping("filetest")
    public String text(@SessionAttribute("user")User user, @RequestParam("pathid") Long pathId, Model model) {
        FilePath userRootPath = filePathService.findByPathName(user.getUserName());

        // 查询当前目录
        FilePath filepath = filePathService.selectByPrimaryKey(pathId);
        System.out.println("测试当前目录："+filepath);
        // 查询当前目录的所有父级目录
        List<FilePath> allParentPaths = new ArrayList<>();
        filePathService.findAllParent(filepath, allParentPaths);
        Collections.reverse(allParentPaths);
        model.addAttribute("allparentpaths", allParentPaths);
        model.addAttribute("nowpath", filepath);
        model.addAttribute("paths", filePathService.selectPath(filepath.getPathId()));
        model.addAttribute("files", fileListService.findFileByPath(filepath.getPathId()));
        //复制移动显示 目录
        model.addAttribute("userrootpath",userRootPath);
        model.addAttribute("mcpaths",filePathService.selectPath(userRootPath.getPathId()));
        return "file/filemanage";
    }

    /**
     * 文件上传 controller方法
     *
     * @param file
     * @param pathid
     * @param session
     * @param model
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping("fileupload")
    public String uploadfile(@SessionAttribute("user")User user,@RequestParam("file") MultipartFile file, @RequestParam("pathid") Long pathid,
                             HttpSession session, Model model) throws IllegalStateException, IOException {

        FilePath nowPath = filePathService.selectByPrimaryKey(pathid);
        System.out.println();
        // true 表示从文件使用上传
        FileList uploadFile = (FileList) fileListService.insertFile(file, user, nowPath, true);
        System.out.println("测试"+uploadFile);
        model.addAttribute("pathid", pathid);
        return "forward:/filetest";
    }

    /**
     * 文件分享
     * @param pathid
     * @param checkfileids
     * @param model
     * @return
     */
    @RequestMapping("doshare")
    public String doshare(@RequestParam("pathid") Long pathid,
                          @RequestParam("checkfileids") List<Long> checkfileids,
                          Model model
    ){
        if (!checkfileids.isEmpty()) {
            System.out.println(checkfileids);
            fileListService.doShare(checkfileids);
        }
        model.addAttribute("pathid", pathid);
        model.addAttribute("message","分享成功");
        return "forward:/filetest";
    }

    /**
     * 删除前台选择的文件以及文件夹
     *
     * @param pathid
     * @param checkpathids
     * @param checkfileids
     * @param model
     * @return
     */
    @RequestMapping("deletefile")
    public String deletefile(@SessionAttribute("userId") Long userid,
                             @RequestParam("pathid") Long pathid,
                             @RequestParam("checkpathids") List<Long> checkpathids,
                             @RequestParam("checkfileids") List<Long> checkfileids, Model model) {
        if (!checkfileids.isEmpty()) {
            // 删除文件
            //fs.deleteFile(checkfileids);
            //文件放入回收战
            fileListService.trashfile(checkfileids, 1L,userid);
        }
        if (!checkpathids.isEmpty()) {
            // 删除文件夹
            //fs.deletePath(checkpathids);
            System.out.println("文件夹"+checkpathids);
            filePathService.trashpath(checkpathids, 1L, true);
            //fs.trashPath(checkpathids);
        }

        model.addAttribute("pathid", pathid);
        return "forward:/filetest";
    }

    /**
     * 重命名
     * @param name
     * @param renamefp
     * @param pathid
     * @param model
     * @return
     */

    @RequestMapping("rename")
    public String rename(@RequestParam("name") String name,
                         @RequestParam("renamefp") Long renamefp,
                         @RequestParam("pathid") Long pathid,
                         @RequestParam("isfile") boolean isfile,
                         Model model){

        System.out.println(name);
        //这里调用重命名方法
        fileListService.rename(name, renamefp, pathid, isfile);

        model.addAttribute("pathid", pathid);
        return "forward:/filetest";

    }

    /**
     * 移动和复制
     * @param mctoid
     * @param model
     * @return
     */
    @RequestMapping("mcto")
    public String mcto(@SessionAttribute("userId") Long userid,
                       @RequestParam("morc") boolean morc,
                       @RequestParam("mctoid") Long mctoid,
                       @RequestParam("pathid") Long pathid,
                       @RequestParam("mcfileids")List<Long> mcfileids,
                       @RequestParam("mcpathids")List<Long> mcpathids,
                       Model model){
        System.out.println("--------------------");
        System.out.println("mcfileids"+mcfileids);
        System.out.println("mcpathids"+mcpathids);

        if(morc){
            System.out.println("这里是移动！~~");
            fileListService.moveAndcopy(mcfileids,mcpathids,mctoid,true,userid);
        }else{
            System.out.println("这里是复制！~~");
            fileListService.moveAndcopy(mcfileids,mcpathids,mctoid,false,userid);
        }

        model.addAttribute("pathid", pathid);
        return "forward:/filetest";
    }

    /**
     * 新建文件夹
     *
     * @param pathid
     * @param pathname
     * @param model
     * @return
     */
    @RequestMapping("createpath")
    public String createpath(@SessionAttribute("userId") Long userid, @RequestParam("pathid") Long pathid, @RequestParam("pathname") String pathname,
                             Model model) {
        System.out.println(pathid + "aaaaaa" + pathname);
        FilePath filepath = filePathService.selectByPrimaryKey(pathid);
        String newname = fileListService.onlyName(pathname, filepath, null, 1, false);

        FilePath newfilepath = new FilePath();
        newfilepath.setParentId(pathid);
        newfilepath.setPathName(newname);
        newfilepath.setPathUserId(userid);

        System.out.println(newname);
        System.out.println(newfilepath.getParentId());
        filePathService.insert(newfilepath);

        model.addAttribute("pathid", pathid);
        return "forward:/filetest";
    }

//    /**
//     * 图片预览
//     * @param response
//     * @param fileid
//     */
//    @RequestMapping("imgshow")
//    public void imgshow(HttpServletResponse response, @RequestParam("fileid") Long fileid) {
//        FileList filelist = fileListService.findByFileList(fileid);
//        System.out.println(filelist);
//        File file = fileListService.getFile(filelist.getFilePath());
//        writefile(response, file);
//    }

    /**
     * 下载文件
     * @param response
     * @param fileid
     */
    @RequestMapping("downfile")
    public void downFile(HttpServletResponse response, @RequestParam("fileid") Long fileid) {
        try {
            FileList filelist = fileListService.findByFileList(fileid);
            File file = fileListService.getFile(filelist.getFilePath());
            response.setContentLength(filelist.getSize().intValue());
            response.setContentType(filelist.getContentType());
            response.setHeader("Content-Disposition","attachment;filename=" + new String(filelist.getFileName().getBytes("UTF-8"), "ISO8859-1"));
            writefile(response, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件 方法
     *
     * @param response
     * @param file
     * @throws IOException
     */
    public void writefile(HttpServletResponse response, File file) {
        ServletOutputStream sos = null;
        FileInputStream aa = null;
        try {
            aa = new FileInputStream(file);
            sos = response.getOutputStream();
            // 读取文件问字节码
            byte[] data = new byte[(int) file.length()];
            IOUtils.readFully(aa, data);
            // 将文件流输出到浏览器
            IOUtils.write(data, sos);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                sos.close();
                aa.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

    /**
     * 文件类型筛选显示load
     * @param user
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("filetypeload")
    public String filetypeload(@SessionAttribute("user")User user,
                               @RequestParam("type") String type,
                               Model model) {
        String contenttype;
        List<FileList> fileLists = null;
        List<FilePath> filePaths = null;
        switch (type) {

            case "document":
                fileLists = fileListService.findDocument(user.getUserId());
                System.out.println(fileLists);
                model.addAttribute("files", fileLists);
                model.addAttribute("isload",1);
                break;

            case "picture":
                contenttype = "image/%";
                fileLists = fileListService.findByUserAndContentType(user, contenttype, 0L);
                System.out.println(fileLists);
                model.addAttribute("files", fileLists);
                model.addAttribute("isload", 1);
                break;

            case "music":
                contenttype = "audio/%";
                fileLists =fileListService.findByUserAndContentType(user, contenttype, 0L);
                System.out.println(fileLists);
                model.addAttribute("files", fileLists);
                model.addAttribute("isload",1);
                break;

            case "video":
                contenttype = "video/%";
                fileLists = fileListService.findByUserAndContentType(user, contenttype, 0L);
                System.out.println(fileLists);
                model.addAttribute("files", fileLists);
                model.addAttribute("isload",1);
                break;
            case "yasuo":
                contenttype = "application/x%";
                fileLists = fileListService.findByUserAndContentType(user, contenttype, 0L);
                System.out.println(fileLists);
                model.addAttribute("files", fileLists);
                model.addAttribute("isload",1);
                break;

            case "trash":
                filePaths = filePathService.findByUserAndContentType(user.getUserId(), 1L);
                fileLists = fileListService.findByUserAndContentType(user, "",1L);

                model.addAttribute("paths", filePaths);
                model.addAttribute("files", fileLists);
                model.addAttribute("istrash", 1);
                model.addAttribute("isload",1);
                break;

            case "share":
                fileLists = fileListService.selectByFileIsShareAndFileIsTrash(1L, 0L);
                model.addAttribute("files", fileLists);
                model.addAttribute("isshare", 1);
                model.addAttribute("isload",1);
                model.addAttribute("userid",user);
                break;


            default:
                break;
        }

        model.addAttribute("type", type);
        return "file/filetypeload";
    }

    }
