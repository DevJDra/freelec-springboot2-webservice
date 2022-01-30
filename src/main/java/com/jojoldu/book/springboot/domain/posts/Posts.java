package com.jojoldu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity //테이블과 링크될 클래스임을 나타낸다. 카멜케이스 이름 -> 언더스코어네이밍(_)으로 테이블 이름을 매칭
public class Posts extends BaseTimeEntity{ //Entity클래스에서는 setter를 절대 만들지 말것

    @Id //PK필드를 나타낸다
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK생성규칙, GenerationType.IDENTITY => auto_increment
    private Long id;

    @Column(length = 500, nullable = false) //테이블의 컬럼을 나타낸다.(굳이 선언하지 않아도 됨), 기본값 외에 추가로 변경이 필요한 옵션이 있을 때 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
