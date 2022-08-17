package cn.oasys.dao.attach;

import cn.oasys.pojo.attch.Attachment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

public interface AttachmentMapper extends Mapper<Attachment> {
    @Select("update aoa_attachment_list  set aoa_attachment_list.attachment_name=#{originalFilename},aoa_attachment_list.attachment_path=#{replace},aoa_attachment_list.attachment_shuffix=#{shuffix},aoa_attachment_list.attachment_size=#{size},aoa_attachment_list.attachment_type=#{contentType},aoa_attachment_list.upload_time=#{date} where aoa_attachment_list.attachment_id=#{attId}")
    Integer updateAtt(@Param("originalFilename") String originalFilename,@Param("replace") String replace,@Param("shuffix") String shuffix, @Param("size")long size,@Param("contentType") String contentType, @Param("date")Date date,@Param("attId") Long attId);
}
