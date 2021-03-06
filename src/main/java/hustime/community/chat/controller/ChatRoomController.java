package hustime.community.chat.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import hustime.community.chat.dto.ChatRoom;
import hustime.community.chat.repository.ChatRoomRepository;
import hustime.member.configuration.auth.LoginUser;
import hustime.member.configuration.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/community/chat")
public class ChatRoomController {
 
    private final ChatRoomRepository chatRoomRepository;
 
    // 채팅 리스트 화면
    @GetMapping("/room")
    public ModelAndView rooms(Model model, @LoginUser SessionUser user) {
    	ModelAndView mv = new ModelAndView("/community/chat/room");
		if (user != null) {
			mv.addObject("uName", user.getName());
		}
        return mv;
    }
    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }
    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }
    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public ModelAndView roomDetail(Model model, @PathVariable String roomId,  @LoginUser SessionUser user) {
        ModelAndView mv = new ModelAndView("community/chat/roomdetail");
        mv.addObject("roomId", roomId);
		if (user != null) {
			mv.addObject("uName", user.getName());
		}
        return mv;
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}
