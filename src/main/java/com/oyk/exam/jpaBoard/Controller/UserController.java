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
