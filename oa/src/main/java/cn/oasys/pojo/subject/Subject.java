package cn.oasys.pojo.subject;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name="aoa_subject")
public class Subject {
    @Id
    @Column(name="subject_id")
    private Long subjectId;

    @Column(name="parent_id")
    private Long parentId;

    private String name;
}
