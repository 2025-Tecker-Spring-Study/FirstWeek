package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // 같은 메모리 멤버 레포지토리가 사용됨
    // 디펜던시 인젝션 DIY
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);  // 여기에서 MemberRepository를 주입
    }

    // 테스트 코드 실행 후 DB를 지우는 메소드
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given -> 주는 데이터 표시
        Member member = new Member();
        member.setName("spring1");
        // 만일 동일하게 spring으로 회원가입 실행시 에러
        // DB에 동일한 이름으로 저장되어 있기 때문
        // 따라서 테스트 코드가 끝나면 DB를 지워주는 메소드를 사용한다.

        //when -> 검증 부분 표시
        Long saveId = memberService.join(member);


        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

        // 위 코드는 단순 테스트 코드
        // 만일 예외가 발생하면 처리해야함
        // 가령 중복 이름의 회원이 발생했을 때 어떻게 대처할 지 테스트 코드를 또 짜야함.
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        // when
        memberService.join(member1);

//        try {
//            memberService.join(member2);
//            fail("");
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다. test");
//        }

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        // then
    }

    @Test
    void findOne() {
    }
}