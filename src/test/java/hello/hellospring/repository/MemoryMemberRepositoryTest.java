package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clear();
    }
    @Test
    public void save(){
        Member member = new Member();
        member.setName("hello");

        memoryMemberRepository.save(member);
        Member result = memoryMemberRepository.findById(member.getId()).get();
        Assertions.assertEquals(member, result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        memoryMemberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memoryMemberRepository.save(member2);

        Member result = memoryMemberRepository.findByName("spring1").get();
        Assertions.assertEquals(member1, result);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        memoryMemberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring1");
        memoryMemberRepository.save(member2);

        List<Member> result = memoryMemberRepository.findAll();
        Assertions.assertEquals(result.size(),2);
    }

}
