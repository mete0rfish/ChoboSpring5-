package com.spring5fs.sp5chap03;

import java.time.LocalDateTime;

public class MemberRegisterService {
    private MemberDao memberDao;

    public MemberRegisterService(MemberDao memberDao){
        this.memberDao = memberDao;
    }

    public Long register(RegisterRequest req){
        Member member = memberDao.selectByEmail(req.getEmail());
        if(member!=null){ //이메일이 중복되면
            throw new DuplicateMemberException("dup email "+req.getEmail());
        }
        // 이메일 중복되지 않으면, 새로운 맴베 만듬.
        Member newMember = new Member(
                req.getEmail(), req.getPassword(), req.getName(),
                LocalDateTime.now()
        );
        //map에 저장
        memberDao.insert(newMember);
        return newMember.getId();
    }
}
