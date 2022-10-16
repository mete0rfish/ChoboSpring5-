package assembler;

import com.spring5fs.sp5chap03.ChangePasswordService;
import com.spring5fs.sp5chap03.MemberDao;
import com.spring5fs.sp5chap03.MemberRegisterService;


/*
조립기(Assembler)는 객체를 생성하고, 의존 객체를 주입하는 기능

 */
public class Assembler {

    private MemberDao memberDao;
    private MemberRegisterService regSvc;
    private ChangePasswordService pwdSvc;

    public Assembler(){
        memberDao = new MemberDao();
        regSvc = new MemberRegisterService(memberDao); //의존 주입
        pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao); //의존 주입
    }

    public MemberDao getMemberDao(){
        return memberDao;
    }

    public MemberRegisterService getMemberRegisterService(){
        return regSvc;
    }

    public ChangePasswordService getChangePasswordService(){
        return pwdSvc;
    }



}
