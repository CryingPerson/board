package com.studey.board.controller;

import com.studey.board.entity.Board;
import com.studey.board.service.BoardService;
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
public class BoardController {
    @Autowired
    private BoardService boardService;

    // ... (기존 코드)

    @GetMapping("board/write")
    public String boardwriteForm(){

        return "boardwrite";
    }

    @PostMapping("/board/writepro") //PostMapping에 들어가는 URL이랑 FOrm태그안의 URL이랑 일치해야함
    public String boardWritePro(Board board, Model model)throws Exception{
        boardService.write(board);
        model.addAttribute("message","글 작성이 완료");
        model.addAttribute("searchUrl","/board/list");
        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model,@PageableDefault
            (page = 0,size = 10,sort = "id",direction = Sort.Direction.DESC)
    Pageable pageable,String searchKeyword ){

        Page<Board>list=null;

        if(searchKeyword ==null){
            list= boardService.boardList(pageable);
        }else{
            list=boardService.boardSearchList(searchKeyword,pageable);
        }


        int nowPage=list.getPageable().getPageNumber()+1;
        int startPage=Math.max(nowPage-4,1);
        int endPage=Math.min(nowPage+5,list.getTotalPages());

        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8090/board/view?id=1
    public String boardView(Model model,Integer id){
        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }
    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id")Integer id,Model model){

        model.addAttribute("board",boardService.boardView(id));
        return "boardmodify";
    }
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board,Model model) throws Exception {
        Board boardTemp=boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.write(boardTemp);

        model.addAttribute("message","글 수정 완료");
        model.addAttribute("searchUrl","/board/list");


        return "message";
    }
    @GetMapping("/board/writepage")
    public String boardWritePage() {
        return "boardwrite";
    }
    @GetMapping("/notice/writepage")
    public String noticeWritePage() {
        return "noticewrite";
    }
    @GetMapping("/quiz/writepage")
    public String quizWritePage() {
        return "quizwrite";
    }

}
