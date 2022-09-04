package com.oyk.exam.jpaBoard.Controller;

import com.oyk.exam.jpaBoard.Dao.UserRepository;
import com.oyk.exam.jpaBoard.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/usr/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/doLogin")
    @ResponseBody
    public String doLogin(String email, String password, HttpServletRequest req, HttpServletResponse resp) {

        if (email == null || email.trim().length() == 0) {
            return "이메일을 입력해주세요.";
        }
        email = email.trim();

        //User user = userRepository.findByEmail(email).orElseGet(() -> null);
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()) {
            return "존재하지 않는 이메일 입니다.";
        }

        if (password == null || password.trim().length() == 0) {
            return "비밀번호를 입력해주세요.";
        }
        password = password.trim();

        if(user.get().getPassword().equals(password) == false) {
            return "비밀번호가 맞지 않습니다. 다시 입력해주세요.";
        }

        HttpSession session = req.getSession();
        session.setAttribute("loginedUserId", user.get().getId());

        return "%s님 환영합니다.".formatted(user.get().getName());
    }

    @RequestMapping("/doLogout")
    @ResponseBody
    public String doLogout(HttpSession session) {
        boolean isLogined = false;

        if(session.getAttribute("loginedUserId") != null) {
            isLogined =true;
        }

        if(isLogined == false) {
            return "이미 로그아웃 되었습니다.";
        }

        session.removeAttribute("loginedUserId");

        return "로그아웃 되었습니다.";
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

    @RequestMapping("/me")
    @ResponseBody
    public User showMe(HttpSession session) {
        boolean isLogined = false;
        long loginedUserId = 0;

        if(session.getAttribute("loginedUserId") != null) {
            isLogined =true;
            loginedUserId = (long)session.getAttribute("loginedUserId");
        }

        if(isLogined == false) {
            return null;
        }

        Optional<User> user = userRepository.findById(loginedUserId);

        if(user.isEmpty()) {
            return null;
        }

        return user.get();
    }
}
