package com.spring.myweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.myweb.command.FreeBoardVO;
import com.spring.myweb.freeboard.service.IFreeBoardService;
import com.spring.myweb.util.PageCreator;
import com.spring.myweb.util.PageVO;

@Controller
@RequestMapping("/freeboard")
public class FreeBoardController {
	
	@Autowired
	private IFreeBoardService service;
	
	// 목록 화면
	@GetMapping("/freeList")
	public void freeList(PageVO vo, Model model) {
		
		PageCreator pc = new PageCreator(vo, service.getTotal());
		
		System.out.println(pc.toString());
		
		model.addAttribute("boardList", service.getList(vo));
		model.addAttribute("pc", pc);
	}
	
	// 글쓰기 페이지 열어주는 메서드
	@GetMapping("/regist")
	public String regist() {
		return "freeboard/freeRegist";
	}
	
	// 글 등록 처리
	@PostMapping("/regist")
	public String regist(FreeBoardVO vo) {
		service.regist(vo);
		return "redirect:/freeboard/freeList";
	}
	
	// 글 상세보기 처리
	
	/*
	 @PathVariable은 URL 경로에 변수를 포함시켜 주는 방식
	 null 이나 공백이 들어갈 수 있는 파라미터라면 적용하지 않는 것을 추천
	 파라미터값에 .이 포함되어 있다면 .뒤의 값은 잘린다는 것을 알아두세요
	 {}안에 변수명을 지어주고, @PathVariable 괄호 안에 영역을 지목해서 값을 받아옵니다. 
	 */
	@GetMapping("/content/{bno}")
	public String getContent(@PathVariable int bno, Model model) {
		model.addAttribute("article", service.getContent(bno));
		return "freeboard/freeDetail";		
	}
	
	// 글 수정 페이지 이동 처리
	@PostMapping("/modify")
	public String modify(@ModelAttribute("article") FreeBoardVO vo) {
		return "freeboard/freeModify";
		
	}	

	// 글 수정 처리
	@PostMapping("/update")
	public String update(FreeBoardVO vo) {
		service.update(vo);
		return "redirect:/freeboard/content/" + vo.getBno();
	}
	
	// 글 삭제 처리
	@PostMapping("/delete")
	public String delete(int bno) {
		service.delete(bno);
		return "redirect:/freeboard/freeList";
	}
	
	
	
	
}
