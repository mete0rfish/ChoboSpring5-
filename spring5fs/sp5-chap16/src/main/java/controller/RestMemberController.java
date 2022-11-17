package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import spring.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestMemberController {
    private MemberDao memberDao;
    private MemberRegisterService registerService;

    @GetMapping("/api/members")
    public List<Member> members(){ // 리턴 타입으로 일반 객체 사용
        return memberDao.selectAll();
    }

    @GetMapping("/api/members/{id}")
    public ResponseEntity<Object> member(@PathVariable Long id,  // 리턴 타입으로 일반 객체 사용
                                 HttpServletResponse response) throws IOException{
        Member member = memberDao.selectById(id);
        if(member == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("no member"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(member);
    }

    @PostMapping("/api/members")
    public ResponseEntity<Object> newMember(
            @RequestBody RegisterRequest regReq) {
        try{
            Long newMemberId = registerService.regist(regReq);
            URI uri = URI.create("/api/members/" + newMemberId);
            return ResponseEntity.created(uri).build();
        } catch(DuplicateMemberException dupEx){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    public void setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao;
    }

    public void setRegisterService(MemberRegisterService registerService){
        this.registerService = registerService;
    }



    }

