package hello.hello_spring.controller;

import org.springframework.ui.Model;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    // 만일 new() 연산자로 객체 생성 시 다른 컨테이너도 참조할 가능성이 높음
    // 다만, MemberService 클래스는 여러가지 인스턴스를 만들 필요가 없음.
    // 따라서 다음처럼 작성하면 스프링부트에 컨테이너가 하나만 생성된다.
    private final MemberService memberService;

    // 생성자 호출
    // 스프링 컨테이너에서 멤버 서비스를 호출
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;

    }
    // 다만, 현재 @Autowired를 두어도 MemberService는 그저 자바 파일이므로 실행되지 않음.
    // 따라서 멤버서비스 클래스에서 @Service를 둔다
    // 그럼 멤버서비스 클래스를 서비스로 인식하고, 스프링 컨테이너에 서비스를 등록해준다.

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMembersForm";
        // template에서 createMembersForm으로 찾아감
        // 해당 html 파일이 로딩됨
        // 이때 form 태그가 값을 입력받음.(action과 method)
        // input 태그에서 name이 key가 되어 데이터가 저장됨.
    }

    // @PostMappring : 데이터 등록 시 사용
    @PostMapping("/members/new")
    // form에 필드 값인 name이 매개값으로 들어옴
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        // 멤버 등록 확인용
        System.out.println("member : " + member.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";

//        만일 서버를 껐다가 키면 회원 목록이 제거된다.
//        데이터는 자바 메모리에 있기 때문에 데이터 베이스나 파일로서 저장해야 한다.
    }



}
