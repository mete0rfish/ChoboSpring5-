package spring;

/*
setter 를 이용한 의존 주입!!

 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberInfoPrinter {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberPrinter printer;

    public void printMemberInfo(String email){
        Member member = memberDao.selectByEmail(email);
        if(member == null){
            System.out.println("데이터 없음.");
            return;
        }

        printer.print(member);
    }

    // setter를 이용한 의존 주입
    @Autowired
    public void setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao;
    }

    // setter를 이용한 의존 주입
    @Autowired
    @Qualifier("printer") // MemberPrinter1 인 "printer" 사용
    public void setPrinter(MemberPrinter printer){
        this.printer = printer;
    }
}
