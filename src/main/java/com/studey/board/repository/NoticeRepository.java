package com.studey.board.repository;

import com.studey.board.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    // 기타 메서드 추가
    Page<Notice> findByTitleContaining(String searchKeyword, Pageable pageable);
}
