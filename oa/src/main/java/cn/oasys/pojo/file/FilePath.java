package cn.oasys.pojo.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "aoa_file_path")
public class FilePath {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "path_id")
	private Long pathId;	//路径id
	
	@Column(name = "parent_id")
	private Long parentId;
	
	@Column(name = "path_name")
	private String pathName;
	
	@Column(name = "path_istrash")
	private Long pathIstrash = 0L;
	
	@Column(name = "path_user_id")
	private Long pathUserId;
	
	@OneToMany(mappedBy = "pathId")
	@JsonIgnore
	private List<FileList> fileList;

}
