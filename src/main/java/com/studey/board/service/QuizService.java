package com.studey.board.service;

import com.studey.board.entity.Board;
import com.studey.board.entity.Quiz;
import com.studey.board.repository.BoardRepository;
import com.studey.board.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    // ... (기존 코드)


    // 글 작성 처리
    public void write(Quiz quiz){
        quizRepository.save(quiz);
    }


    //게시글 리스트 처리
    public Page<Quiz> quizList(Pageable pageable){
        return quizRepository.findAll(pageable);
    }

    public Page<Quiz> quizSearchList(String searchKeyword, Pageable pageable){

        return quizRepository.findByTitleContaining(searchKeyword,pageable);
    }
    public Quiz quizView(Integer id){
        return quizRepository.findById(id).get();
    }
    //특정 게시글 삭제
    public void quizDelete(Integer id){
        quizRepository.deleteById(id);
    }


}