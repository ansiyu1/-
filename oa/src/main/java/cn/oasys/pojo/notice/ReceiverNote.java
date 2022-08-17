package cn.oasys.pojo.notice;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name="aoa_receiver_note")
public class ReceiverNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 笔记id
     */
    @Column(name = "note_id", nullable = false)
    private Long noteId;

    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
