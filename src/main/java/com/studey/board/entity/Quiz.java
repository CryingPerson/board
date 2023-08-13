package com.studey.board.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Quiz {
    @Id //pk를 의미함
    @GeneratedValue(strategy = GenerationType.IDENTITY) //전력을 어케할거냐
    private Integer id;
    private String title;
    private String content;
    private String filename;
    private String filepath;
    private String boardnumber1;
}