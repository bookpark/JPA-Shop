package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// 데이터 변경 시 해당 애노테이션이 선언되어야 함
@Transactional(readOnly = true)
@RequiredArgsConstructor // final이 있는 필드만 가지고 생성자를 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    // 쓰기에는 Transactional 선언
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Member의 name을 unique 제약 조건으로 잡아주어야 더 안전
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
//    @Transactional(readOnly = true) // JPA 최적화 (읽기)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 조회
//    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
