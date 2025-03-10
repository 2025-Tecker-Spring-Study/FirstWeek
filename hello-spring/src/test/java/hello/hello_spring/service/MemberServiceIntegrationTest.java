package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
    // 실행과정
    // 1. 테스트 실행 시 트랜잭션 실행
    // 2. DB에 데이터를 인서트 쿼리 실행
    // 3. 테스트가 끝난 후에 DB에 넣었던 데이터가 반영이 안되고 지워짐

class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // @Transactional을 선언했으므로 @AfterEach와 @BeforeEach를 통해 데이터를 지워줄 필요없다.

    @Test
//    @Commit
    void 회원가입() {
        //given -> 주는 데이터 표시
        Member member = new Member();
        member.setName("spring");
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