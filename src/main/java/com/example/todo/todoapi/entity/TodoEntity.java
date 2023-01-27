package com.example.todo.todoapi.entity;

// 일정 관리 프로그램

import com.example.todo.todoapi.userapi.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "todoId")
@Builder
@Entity
@Table(name = "tbl_todo")
public class TodoEntity {

    @Id
    // PK를 랜덤 문자열로 설정
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String todoId;

    @Column(nullable = false, length = 30)
    private String title; // 제목

    private boolean done; // 일정 완료 여부

    @CreationTimestamp
    private LocalDateTime createDate; // 일정 등록 시간

    // 회원과 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    // 연관 관계 설정은 하지만 INSERT, UPDATE 시에는 이 객체를 활용하지 않겠다
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    // 할 일 등록, 수정 시 사용할 외래키
    @Column(name = "user_id")
    private String userId;



}
