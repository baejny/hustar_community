package hustime.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hustime.member.configuration.auth.LoginUser;
import hustime.member.configuration.auth.dto.SessionUser;
import hustime.member.domain.user.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {
	
	@GetMapping("/")
	public String index(Model model, @LoginUser SessionUser user) {
		if(user!=null) {
			model.addAttribute("uName", user.getName());
		}
		return "index";
	}
	
	@RequestMapping(value = "/1", method=RequestMethod.GET) 
	public String templateloginPage(Model model, @LoginUser SessionUser user) {
		System.out.println("USER: "+user);
		if(user!=null) {
			model.addAttribute("uName", user.getName());
		}
		return "index2";
	}
	
}