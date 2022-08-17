package cn.oasys.service.file;

import cn.oasys.pojo.attch.Attachment;
import cn.oasys.pojo.file.FileList;
import cn.oasys.pojo.file.FilePath;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileListService extends BaseService<FileList> {
    int fileCount(Long userId);

    List<FileList> findFileByPath(Long filepath);

    Object insertFile(MultipartFile file, User user, FilePath nowPath, boolean b) throws IOException;

    File getFile(String filepath);

    String onlyName(String name, FilePath filepath, String shuffix, int num, boolean isfile);

    void doShare(List<Long> checkFileIds);

    void trashfile(List<Long> checkFileIds, Long setIstrash, Long userid);

    void rename(String name, Long renamefp, Long pathid, boolean isfile);

    void moveAndcopy(List<Long> mcfileids, List<Long> mcpathids, Long mctoid, boolean b, Long userid);

    List<FileList> findByUserAndContentType(User user, String contenttype, Long istrash);

    List<FileList> findDocument(Long userId);

    FileList findByFileList(Long mcFileId);

    List<FileList> selectByFileIsShareAndFileIsTrash(Long isShare, Long isTrash);

    Object saveFile(MultipartFile file, User user, FilePath nowPath, boolean isFile) throws IOException;

    Integer updateAtt(MultipartFile file, User user, FilePath nowPath, Long attId) throws IOException;

    File get(Attachment att);
}
