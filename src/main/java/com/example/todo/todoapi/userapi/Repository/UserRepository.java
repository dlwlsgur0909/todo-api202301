package com.example.todo.todoapi.userapi.Repository;

import com.example.todo.todoapi.userapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    // 이메일로 회원 조회
    // select  from tbl_user where email=?
    UserEntity findByEmail(String email);

    // 이메일 중복 검사
    // select count(*) from tbl_user where email=?
//    @Query("select count(*) from UserEntity u where u.email=?1") // JPQL
    boolean existsByEmail(String email);

}
