package com.hieucoder.coderlo.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hieucoder.coderlo.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    @Modifying
    @Transactional
    @Query("UPDATE Token t SET t.status = :status WHERE t.userName = :userName")
    Integer updateStatusByUserName(@Param("status") Integer status, @Param("userName") String userName);
}
