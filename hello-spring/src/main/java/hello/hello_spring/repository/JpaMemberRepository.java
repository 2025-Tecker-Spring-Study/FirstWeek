package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // JPA를 동작하게 하는 엔티티 매니저
    private final EntityManager em;

    // 엔티티 매니저를 주입받아서 사용
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.of(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();

    }

    @Override
    public List<Member> findAll() {
        // *과 특정 열 이름이 아닌
        // m으로 멤버 객체 자체를 조회함.
        // 기존 SQL이라면 * 혹은 id, name으로 조회한 후 m으로 매핑해줘야 한다.
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        return result;

    }
}
