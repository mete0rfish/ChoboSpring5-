package com.spring5fs.sp5chap03;

public class ChangePasswordService {
    private MemberDao memberDao;

    public void changePassword(String email, String oldPwd, String newPwd){
        Member member = memberDao.selectByEmail(email);
        if(member == null){
            throw new MemberNotFoundException();
        }

        member.changePassword(oldPwd, newPwd);

        memberDao.update(member);
    }

    //setter를 통한 의존 주입
    public void setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao;
    }
}
