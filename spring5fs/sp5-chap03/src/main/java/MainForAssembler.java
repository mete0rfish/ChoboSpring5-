import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import assembler.Assembler;
import com.spring5fs.sp5chap03.*;
import config.AppConf1;
import config.AppCtx;
import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import org.springframework.context.annotation.AnnotationConfigApplicationContextExtensionsKt;
//import spring.ChangePasswordService;
//import spring.DuplicateMemberException;
//import spring.MemberNotFoundException;
//import spring.MemberRegisterService;
//import spring.RegisterRequest;
//import spring.WrongIdPasswordException;

/*
Main for Spring!!!
 */

public class MainForAssembler {

    private static ApplicationContext ctx = null; //

    public static void main(String[] args) throws IOException {
        // 설정 파일로 부터 스프링 컨테이너 생성
        ctx = new AnnotationConfigApplicationContext(AppConf1.class);

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in)); // 콘솔입력을 위해 System.in으로 버퍼리더 생성
        while (true) {
            System.out.println("명령어를 입력하세요:");
            String command = reader.readLine(); // 콘솔에서 한 줄 입력받음
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("종료합니다.");
                break;
            }
            if (command.startsWith("new ")) {
                processNewCommand(command.split(" "));
                continue;
            } else if (command.startsWith("change ")) {
                processChangeCommand(command.split(" "));
                continue;
            } else if(command.equals("list")){
                processListCommand();
                continue;
            } else if(command.startsWith("info ")){
                processInfoCommand(command.split(" "));
                continue;
            }else if(command.equals("version")){
                processVersionCommand();
                continue;
            }
            printHelp(); //명령어 잘못 입력할 경우 도움말 실행
        }
    }

    private static Assembler assembler = new Assembler(); // 어샘블 객체 생성 => 모든 객체가 생성됨

    private static void processNewCommand(String[] arg) {
        if (arg.length != 5) {
            printHelp();
            return;
        }

        // 스프링 컨테이너로 부터 "memberRegSvc"인 빈 객체 구함.
        MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class); //회원등록객체 사용
        RegisterRequest req = new RegisterRequest(); // 회원 등록 입력 폼 만들고,
        req.setEmail(arg[1]);
        req.setName(arg[2]);
        req.setPassword(arg[3]);
        req.setConfirmPassword(arg[4]);

        if (!req.isPasswordEqualToConfirmPassword()) { //비번이 비번확인이랑 다를 때
            System.out.println("암호와 확인이 일치하지 않습니다.\n");
            return;
        }
        try {
            regSvc.register(req); // regist 없음
            System.out.println("등록했습니다.\n");
        } catch (DuplicateMemberException e) {
            System.out.println("이미 존재하는 이메일입니다.\n");
        }
    }

    private static void processChangeCommand(String[] arg) {
        if (arg.length != 4) {
            printHelp();
            return;
        }

        // 스프링 컨테이너로 부터 "changePwdSvc"인 빈 객체 구함.
        ChangePasswordService changePwdSvc =
                ctx.getBean("changePwdSvc", ChangePasswordService.class);
        try {
            changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
            System.out.println("암호를 변경했습니다.\n");
        } catch (MemberNotFoundException e) {
            System.out.println("존재하지 않는 이메일입니다.\n");
        } catch (WrongIdPasswordException e) {
            System.out.println("이메일과 암호가 일치하지 않습니다.\n");
        }
    }

    private static void processListCommand(){
        MemberListPrinter listPrinter =
                ctx.getBean("listPrinter", MemberListPrinter.class);
        listPrinter.printAll();
    }

    private static void processInfoCommand(String[] arg){
//        System.out.println("arg.lenth : " + arg.length);
        if(arg.length != 2){
            printHelp();
            return;
        }

        MemberInfoPrinter infoPrinter =
                ctx.getBean("infoPrinter", MemberInfoPrinter.class);
        infoPrinter.printMemberInfo(arg[1]);
    }

    private static void processVersionCommand(){
        VersionPrinter versionPrinter =
                ctx.getBean("versionPrinter", VersionPrinter.class);
        versionPrinter.print();
    }

    private static void printHelp() {
        System.out.println();
        System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
        System.out.println("명령어 사용법:");
        System.out.println("new 이메일 이름 암호 암호확인");
        System.out.println("change 이메일 현재비번 변경비번");
        System.out.println();
    }
}
