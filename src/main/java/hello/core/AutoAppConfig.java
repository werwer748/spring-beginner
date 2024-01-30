package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//? 컴포넌트 스캔은 이름 그대로 `@Component` 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록
@ComponentScan(
        //? 지정하지 않을시 ComponentScan가 붙은 컴포넌트의 패키지가 탐색 시작위치가 된다.\
        //? 권장하는 방법은 설정 정보 클래스의 위치를 최상단에 두는 것 - ex) hello.core
        //? 근데 애초에 스프링부트를 쓰면 @SpringBootApplication(프로젝트 최상단 클래스) 여기서 컴포넌트 스캔을 다 해줌.
        // 탐색할 패키지의 시작위치를 지정할 수 있다. - 모든 클래스컴포넌트를 스캔하면 시간이 오래걸려서 꼭 필요한 위치부터 탐색하도록 지정할 수 있다는 것
//        basePackages = "hello.core.member",
        // 탐색을 시작할 클래스의 패키지를 탐색 시작 위치로 지정한다
//        basePackageClasses = AutoAppConfig.class,
        // 실무에서는 굳이 필터로 걸러내거나 할 필요는 없음. 예제로 @Configuration 등록한 것들이 빈에 등록되면 안되서 추가한 것
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
//! 빈이름 겹쳐서 에러 내보기
//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
