package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.format.DateTimeFormatter;

public class MemberPrinter {

    private DateTimeFormatter dateTimeFormatter;

    // 기본 생성자는 new 를 통해서 무조건 실행됨.
    public MemberPrinter(){ // required=false 면 기본 생성자를 통해서 만들어진 것 때문에 null이 아님.
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
    }

    public void print(Member member){
        if(dateTimeFormatter == null){ // setDateFormatter 없어도 dateTimeFormatter 이 null 이여도 실행 가능?
            System.out.printf(
                    "회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n", // 2022-08-11
                    member.getId(), member.getEmail(), member.getName(), member.getRegisterDateTime()
            );
        } else{
            System.out.printf(
                    "회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%sF\n", // 2022년 08월 11일
                    member.getId(), member.getEmail(), member.getName(), dateTimeFormatter.format(member.getRegisterDateTime())
            );
        }
    }

    // new a@b.c ABC abc abc
    // info a@b.c

    @Autowired(required = false)
    public void setDateFormatter(DateTimeFormatter dateTimeFormatter){
        this.dateTimeFormatter = dateTimeFormatter;
    }
}

// null 반환 : 2022-08-11
// null 미반환 : 2022년 08월 11일