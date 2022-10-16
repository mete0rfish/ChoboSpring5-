package spring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemberDao {

    private static long nextId = 0; //아이디 인덱스

    private Map<String, Member> map = new HashMap<>(); //내부 저장소

    public Collection<Member> selectAll(){
        return map.values();
    }


    public Member selectByEmail(String email){ //입력받은 이메일 선택해서 반환
        return map.get(email);
    }

    public void insert(Member member){  //입력받은 맴버를 맵에 추가
        member.setId(++nextId);
        map.put(member.getEmail(), member);
    }

    public void update(Member member){  //해시-맵은 중복이 존재하지 않음.
        map.put(member.getEmail(), member);
    }
}
