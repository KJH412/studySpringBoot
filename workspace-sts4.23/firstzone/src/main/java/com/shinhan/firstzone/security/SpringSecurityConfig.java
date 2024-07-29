package com.shinhan.firstzone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //설정파일임을 의미 ..App시작 시 해석된다.
@EnableWebSecurity  //web에서 시큐리티 담당
public class SpringSecurityConfig {  
	
	//상수로 접근권한에 따른 요청주소들을 저장
	private static final String[] WHITE_LIST = {"/security/all", "/auth/**", "/v3/**", "/swagger-ui/**"}; 
	//"/v3/**", "/swagger-ui/**" => springdoc-openapi
	//http://localhost:9990/swagger-ui/index.html#/  
	private static final String[] USER_LIST = {"/security/user", "/webboard/**","/replies/**"};
	private static final String[] ADMIN_LIST = {"/security/admin"};
	private static final String[] MANAGER_LIST = {"/security/manager"};
	
	@Bean
	PasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	@Bean
	public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
		//http.csrf(c->c.disable()); //csrf토큰을 사용하지 않는다. default는 토큰을 사용함 
		//=> html에서 th:name="${_csrf.parameterName}"이런식으로 사용함. post방식일 떄!
		http.authorizeHttpRequests(auth ->{
			auth.requestMatchers(WHITE_LIST).permitAll(); //로그인 불필요 //permitAll: WHITE_LIST에 포함된 URL은 인증 없이 접근할 수 있도록 허용
			auth.requestMatchers(USER_LIST).hasRole("USER");  
			auth.requestMatchers(ADMIN_LIST).hasRole("ADMIN");  
			auth.requestMatchers(MANAGER_LIST).hasRole("MANAGER");  
			auth.anyRequest().authenticated();  //authenticated : 그 외의 모든 요청은 인증을 요구
			
		});
		//http.formLogin();
		//로그인 성공 시 loginSuccess URL로 이동
		http.formLogin(login -> 
				login.loginPage("/auth/login")
				.usernameParameter("mid") //default이름이 username, 변경하고자 한다면 반드시 설정함.
				.defaultSuccessUrl("/auth/loginSuccess")
				.permitAll()
				);
		//로그아웃 성공하면 login URL로 이동
		http.logout(out -> out.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
								.logoutSuccessUrl("/auth/login")
								.invalidateHttpSession(true)); //session에 있는 것 지움
		http.exceptionHandling(handling -> handling.accessDeniedPage("/auth/accessDenined"));
		return http.build();
	}
}
