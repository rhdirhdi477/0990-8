package com.example.spring01.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring01.model.dto.ProductDTO;

@Controller
public class MainController {
	//로깅을 위한 변수
	private static final Logger logger=
			LoggerFactory.getLogger(MainController.class);
	// url mapping, 여기서 url매핑처리하면 HomeController에서의 매핑을 막아야함	
	// http://localhost/spring01/ => MainController의 main() 실행,post/get모두 실행됨		
	@RequestMapping("/")
	public String main(Model model) {
		// Model에 자료 저장(서블릿의 request 객체에 해당됨)			
		model.addAttribute(
				"message","홈페이지 방문을 환영합니다.");
		// main.jsp로 포워딩됨
		//   /WEB-INF/views/main.jsp		
		return "main";
	}//main()
	
	@RequestMapping("gugu.do") //url mapping
	public String gugu(Model model, 
			@RequestParam(defaultValue="2") int dan) {
		//int dan=7;
		String result="";
		for(int i=1; i<=9; i++) {
			result += dan + "x" + i + "=" + dan*i +"<br>";
		}
		//모델에 자료 저장
		model.addAttribute("result", result);
		// views/test/gugu.jsp로 포워딩
		return "test/gugu";
	}//gugu()
	
	@RequestMapping("test.do")
	public void test() {
		// void로 하면 메소드 이름과 같은 페이지로 자동 이동한다.
		//  /WEB-INF/views/test.jsp
		//return "test";
	}//test()
	
	@RequestMapping("test/doA")
	public String doA(Model model) {
		//자료저장
		model.addAttribute("message", "홈페이지 방문을 환영합니다.");
		//포워딩
		return "test/doA";
	}//doA
	
	@RequestMapping("test/doB")
	public void doB() {
		//리턴 타입이 void인 경우 메소드이름과 같은 페이지로 포워딩
		logger.info("doB 호출...");
	}//doB()
	
	//Model - 전달할 데이터
	//ModelAndView - 데이터와 포워딩할 페이지 정보 포함
	@RequestMapping("test/doC")
	public ModelAndView doC() {
		Map<String,Object> map=new HashMap<>();
		map.put("product", new ProductDTO("에어컨",2500000));
		return new ModelAndView("test/doC", "map", map);
		
	}
	
	@RequestMapping("test/doD")
	public String doD() {
		/*forward : 주소 그대로, 화면 이동, 대량의 데이터전달
		redirect : 주소 변경, 화면 이동, get방식의 소량의 데이터
		단, jsp와는 달리 스프링에서는 기본값이 forward이다.*/
		
		return "redirect:/test/doE";//다시 Controller를 호출한
		//후에 아래 test/doE 이동
	}//doD()
	
	@RequestMapping("test/doE")
	public void doE() {
		//void이기 때문에 자동적으로 doE.jsp로 포워딩
	}//doE()
	
	//ajax 처리 전용 컨트롤러(백그라운드에서 실행)
	//@RestController 스프링 4.0이상부터 사용가능(호환성 주의)
	//뷰를 리턴하는 것이 아닌 데이터 자체를 리턴할 경우
/*	@ResponseBody // dto를 json으로 변환
	@RequestMapping("test/doF")
	public ProductDTO doF() {
		return new ProductDTO("냉장고",500000);
	}*/
}
