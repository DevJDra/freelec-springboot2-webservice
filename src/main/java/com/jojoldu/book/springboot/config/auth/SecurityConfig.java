package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정들을 활성화 시켜줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable()//h2-console 화면을 사용하기 위해 해당옵션들을 disable한다.
                .and()
                    .authorizeRequests()//URL별 권한관리를 설정하는 옵션의 시작점. authorizeRequests()가 선언되어야지만 antMatchers 옵션을 사용가능
                    .antMatchers("/", "/css/**", "/images/**",//andMatchers => 권한 관리 대상을 지정하는 옵션, URL, HTTP 메소드 별로 관리가 가능
                            "/js/**", "/h2-console/**").permitAll() // "/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람권한을 줌
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // "/api/v1/**" 주소를 가진 API는 USER권한을 가진 사람만 가능
                    .anyRequest().authenticated()//anyRequest=> 설정된 값들 외 나머지 URL들을 나타냄 , 여기서 authenticated를 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용(로그인한 사용자)
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정의 진입점
                        .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
                            .userService(customOAuth2UserService);  //소셜 로그인 성공 시 후속조치를 진행할 UserService 인터페이스의 구현체 등록
                                                                    //리소스서버(소셜서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 가능
    }
}
