package hello.hello_spring;

import hello.hello_spring.aop.TimeTraceAop;
import hello.hello_spring.repository.*;
import hello.hello_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

//    private DataSource dataSource;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;

//    private EntityManager em;
//
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // memberService 컨테이너에 memberRepository 객체 주입
    @Bean
    public MemberService memberService() {
//        return new MemberService(memberRepository());
        return new MemberService(memberRepository);
    }

    // AOP 컨테이너
    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }

}


//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
        // MemberRepository의 코드들을 수정하지 않아도, 데이터베이스 코드에 dataSource를 주입하여 사용할 수 있다.
        // 스프링은 객체지향프로그램의 다형성을 컨테이너를 통해 편리하게 구현했다.
        // 이때 dependency injection이 사용된다.

//        return new JdbcTemplateMemberRepository(dataSource);

//        return new JpaMemberRepository(em);




