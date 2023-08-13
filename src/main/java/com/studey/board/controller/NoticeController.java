package com.studey.board.controller;

import com.studey.board.entity.Notice;
import com.studey.board.service.NoticeService;
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
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    // ... (기존 코드)

    @GetMapping("/notice/write")
    public String noticereadForm(){

        return "noticewrite";
    }

    @PostMapping("/notice/writepro") //PostMapping에 들어가는 URL이랑 FOrm태그안의 URL이랑 일치해야함
    public String boardReadPro(Notice notice, Model model)throws Exception{
        noticeService.write(notice);
        model.addAttribute("message","공지 작성 완료");
        model.addAttribute("searchUrl","/notice/list");
        return "message";
    }

    @GetMapping("/notice/list")
    public String noticeList(Model model,@PageableDefault
            (page = 0,size = 10,sort = "id",direction = Sort.Direction.DESC)
    Pageable pageable,String searchKeyword ){

        Page<Notice>list=null;

        if(searchKeyword ==null){
            list= noticeService.noticeList(pageable);
        }else{
            list=noticeService.noticeSearchList(searchKeyword,pageable);
        }


        int nowPage=list.getPageable().getPageNumber()+1;
        int startPage=Math.max(nowPage-4,1);
        int endPage=Math.min(nowPage+5,list.getTotalPages());

        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "noticelist";
    }

    @GetMapping("/notice/view") //localhost:8090/board/view?id=1
    public String noticeView(Model model,Integer id){
        model.addAttribute("board",noticeService.noticeView(id));
        return "noticeview";
    }
    @GetMapping("/notice/delete")
    public String noticeDelete(Integer id){
        noticeService.noticeDelete(id);
        return "redirect:/notice/list";
    }
    @GetMapping("/notice/modify/{id}")
    public String noticeModify(@PathVariable("id")Integer id,Model model){

        model.addAttribute("notice",noticeService.noticeView(id));
        return "noticemodify";
    }
    @PostMapping("/notice/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Notice notice,Model model) throws Exception {
        Notice boardTemp=noticeService.noticeView(id);
        boardTemp.setTitle(notice.getTitle());
        boardTemp.setContent(notice.getContent());
        noticeService.write(boardTemp);

        model.addAttribute("message","글 수정 완료");
        model.addAttribute("searchUrl","/notice/list");


        return "message";
    }

}
