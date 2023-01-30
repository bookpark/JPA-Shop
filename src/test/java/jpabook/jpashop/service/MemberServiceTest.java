package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 기본적으로 롤백 시킴
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;

    @Test
//    @Rollback(value = false) // insert query를 보기 위해 Rollback false값을 줌 또는 EntityManager를 가져와서 flush 시킴
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("park");
        //when
        Long savedId = memberService.join(member);
        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("park");
        Member member2 = new Member();
        member2.setName("park");
        //when
        memberService.join(member1);
        memberService.join(member2);
//        try {
//            memberService.join(member2);
//        } catch (IllegalStateException e) {
//            return;
//        }
        //then
        fail("예외가 발생해야 한다.");
    }
}