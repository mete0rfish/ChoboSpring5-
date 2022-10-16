package spring;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class MemberRegisterService {
    @Autowired
    private MemberDao memberDao;

    public MemberRegisterService(){
        // 생성자 객체 주입 삭제
        // @Autowired 덕분에
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
