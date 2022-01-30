package com.jojoldu.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Runwith(SpringRunner.class)
//테스트를 진행할 때 JUni에 내장된 실행자 외에 다른 실행자를 실행 시킴
//여기서는 SpringRunner라는 스프링 실행자를 사용
//스프링 부트 테스트와 JUni 사이에 연결자 역할
@RunWith(SpringRunner.class)
//web에 집중할 수 있는 어노테이션 , @Controller만 사용
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {


    @Autowired //스프링이 관리하는 빈(Bean)을 주입받음
    private MockMvc mvc; // 웹 API를 테스트할때 사용.

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) //MockMvc를 통해 /hello 주소로 HTTP GET요청을 함.
                .andExpect(status().isOk())//mvc.perform의 결과를 검증. Http Header의 status 검증(200,404,500 ..)
                .andExpect(content().string(hello)); //응답 본문의 내용을 검증. controller에서 "hello"를 리턴하기 떄문에 이값이 맞는지 검증
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {

        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name) //API를 테스트할때 사용될 요청 파라미터 설정, 단 String만 허용
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) //JSON 응답값을 필드별로 검증할 수 있는 메소드 , $를 기준으로 필드명을 명시
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}















