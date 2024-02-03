package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // final이 붙은 필수값을 확인해 생성자를 만들어 준다.
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
//    @Autowired private MemberRepository memberRepository; // => 필드 주입: 권장되지 않는다. 주입할 객체를 지정하기가 힘들다. 실제 코드와 관계없는 테스트 코드정도에 사용
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy; //? 구체화에 의존하지않고 추상화에만 의존하게끔
    //? 생성자주입을 쓰면 좋은점 final을 쓸 수 있다.
    //? final? 객체가 초기화시 값이 정해지면 바꿀수없다.(값이 바뀔 염려가 없음) 값이 안정해지면 바로 에러를 띄움

//    @Autowired // 수정자 주입 예제 => 이 경우 주입할 대상이 없으면 오류가 발생하는데 그럼에도 동작하게 하러면 @Autowired(required = false)로 지정
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }


    @Autowired //? 생성자가 하나면 @Autowired 생략가능
    public OrderServiceImpl(
            MemberRepository memberRepository,
//            @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy //같은 네임인 Qualifier 들 끼리 매칭된다고 생각하면 됨.
            @MainDiscountPolicy DiscountPolicy discountPolicy
    ) {
//        System.out.println("1. OrderServiceImpl.OrderServiceImpl");
        // * DI: 의존관게(의존성) 주입
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

//    @Autowired // 일반 메서드 주입(아무 메서드에 Autowired를 붙임) - 잘 사용되지 않음
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
