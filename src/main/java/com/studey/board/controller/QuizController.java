package com.studey.board.controller;


import com.studey.board.entity.Quiz;
import com.studey.board.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuizController {
    @Autowired
    private QuizService quizService;

    // ... (기존 코드)

    @GetMapping("quiz/write")
    public String quizwriteForm(){

        return "quizwrite";
    }

    @PostMapping("/quiz/writepro") //PostMapping에 들어가는 URL이랑 FOrm태그안의 URL이랑 일치해야함
    public String quizWritePro(Quiz quiz, Model model)throws Exception{
        quizService.write(quiz);
        model.addAttribute("message","글 작성이 완료");
        model.addAttribute("searchUrl","/quiz/list");
        return "message";
    }

    @GetMapping("/quiz/list")
    public String quizList(Model model,@PageableDefault
            (page = 0,size = 10,sort = "id",direction = Sort.Direction.DESC)
    Pageable pageable,String searchKeyword ){

        Page<Quiz>list=null;

        if(searchKeyword ==null){
            list= quizService.quizList(pageable);
        }else{
            list=quizService.quizSearchList(searchKeyword,pageable);
        }


        int nowPage=list.getPageable().getPageNumber()+1;
        int startPage=Math.max(nowPage-4,1);
        int endPage=Math.min(nowPage+5,list.getTotalPages());

        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "quizlist";
    }

    @GetMapping("/quiz/view") //localhost:8090/board/view?id=1
    public String quizView(Model model,Integer id){
        model.addAttribute("quiz",quizService.quizView(id));
        return "quizview";
    }
    @GetMapping("/quiz/delete")
    public String quizDelete(Integer id){
        quizService.quizDelete(id);
        return "redirect:/board/list";
    }
    @GetMapping("/quiz/modify/{id}")
    public String quizModify(@PathVariable("id")Integer id,Model model){

        model.addAttribute("quiz",quizService.quizView(id));
        return "quizmodify";
    }
    @PostMapping("/quiz/update/{id}")
    public String quizUpdate(@PathVariable("id") Integer id, Quiz board,Model model) throws Exception {
        Quiz boardTemp=quizService.quizView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        quizService.write(boardTemp);

        model.addAttribute("message","글 수정 완료");
        model.addAttribute("searchUrl","/quiz/list");


        return "message";
    }

}
