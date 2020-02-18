package hustime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hustime.community.free.dto.FreeDto;
import hustime.community.free.service.FreeService;
import hustime.community.notice.dto.NoticeDto;
import hustime.community.notice.service.NoticeService;
import hustime.community.schedule.dto.ScheduleDto;
import hustime.community.schedule.service.ScheduleService;
import hustime.member.configuration.auth.LoginUser;
import hustime.member.configuration.auth.dto.SessionUser;
import hustime.qna.QnaDto;
import hustime.qna.QnaService;
import hustime.qna.etc.dto.EtcDto;
import hustime.qna.etc.service.EtcService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {
   
   @Autowired
   private ScheduleService scheduleService;
   @Autowired
   private NoticeService noticeService;
   @Autowired
   private FreeService freeService;
   @Autowired
   private EtcService etcService;
   @Autowired
   private QnaService qnaService;

   
   @RequestMapping(value = "/", method=RequestMethod.GET) 
   public ModelAndView templateloginPage(Model model, @LoginUser SessionUser user) throws Exception{
      System.out.println("USER: "+user);
      ModelAndView mv = new ModelAndView("/index");
      if(user!=null) {
          mv.addObject("uName", user.getName());
      }
      int count_new_schedule = scheduleService.selectNewBoardCount();
      int count_new_notice = noticeService.selectNewBoardCount();
      int count_new_qna = qnaService.selectNewBoardCount();
      System.out.println(count_new_schedule+" @@@@@@@@@@@@@@@@@@@@");
      List<ScheduleDto> list_schedule = scheduleService.selectTopFiveBoardList();
      List<NoticeDto> list_notice = noticeService.selectTopFiveBoardList();
      List<FreeDto> list_free = freeService.selectTopFiveBoardList();
      List<EtcDto> list_etc = etcService.selectTopFiveBoardList();
      List<QnaDto> list_qna = qnaService.selectTopFiveBoardList();
      mv.addObject("count_schedule", count_new_schedule);
      mv.addObject("count_notice", count_new_notice);
      mv.addObject("count_qna", count_new_qna);
      mv.addObject("list_schedule", list_schedule);
      mv.addObject("list_notice", list_notice);
      mv.addObject("list_free", list_free);
      mv.addObject("list_etc", list_etc);
      mv.addObject("list_qna", list_qna);
      return mv;
   }

   @RequestMapping(value = "/userinfo", method=RequestMethod.GET) 
   public ModelAndView openUserinfo(Model model, @LoginUser SessionUser user){
      ModelAndView mv = new ModelAndView("/member/userProfile");
      if(user!=null) {
          mv.addObject("uName", user.getName());
          mv.addObject("uEmail", user.getEmail());
          mv.addObject("uPicture", user.getPicture());
      }
      return mv;
   }
   
}

