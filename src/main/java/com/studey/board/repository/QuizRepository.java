package com.studey.board.repository;

import com.studey.board.entity.Board;
import com.studey.board.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {

    Page<Quiz> findByTitleContaining(String searchKeyword, Pageable pageable);




    // ... (기존 코드)

}
