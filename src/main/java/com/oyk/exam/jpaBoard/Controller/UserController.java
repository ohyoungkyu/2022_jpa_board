package com.oyk.exam.jpaBoard.Controller;

import com.oyk.exam.jpaBoard.Dao.UserRepository;
import com.oyk.exam.jpaBoard.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/usr/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/doLogin")
    @ResponseBody
    public String doLogin(String email, String password) {

        if (email == null || email.trim().length() == 0) {
            return "이메일을 입력해주세요.";
        }
        email = email.trim();

        User user = userRepository.findByEmail(email).get();

        if(user == null) {
            return "존재하지 않는 이메일 입니다.";
        }

        if (password == null || password.trim().length() == 0) {
            return "비밀번호를 입력해주세요.";
        }
        password = password.trim();

        if(user.getPassword().equals(password) == false) {
            return "비밀번호가 맞지 않습니다. 다시 입력해주세요.";
        }

        return "%s님 환영합니다.".formatted(user.getName());
    }

    @RequestMapping("/doJoin")
    @ResponseBody
    public String doJoin(String name, String email, String password) {

        if(name == null || name.trim().length() == 0) {
            return "이름을 입력해주세요.";
        }
        name = name.trim();
        if(email == null || email.trim().length() == 0) {
            return "이메일을 입력해주세요.";
        }
        email = email.trim();

        boolean existsByEmail = userRepository.existsByEmail(email);

        if(existsByEmail) {
            return "이미 존재하는 이메일입니다.";
        }

        if(password == null || password.trim().length() == 0) {
            return "비밀번호를 입력해주세요.";
        }
        password = password.trim();

        User user = new User();
        user.setRegDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);

        userRepository.save(user);

        return "%d번 회원이 생성되었습니다.".formatted(user.getId());
    }
}
