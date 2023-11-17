package umc.velog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.velog.domain.entity.Member;
import umc.velog.dto.board.BoardDto;
import umc.velog.dto.board.BoardRequestDto;
import umc.velog.dto.board.BoardResponseDto;
import umc.velog.dto.member.MemberDto;
import umc.velog.service.BoardService;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 전체 글 보기 페이지(홈)
    @GetMapping("")
    @ResponseBody
    public List<BoardDto> list() {
        List<BoardDto> boardList = boardService.getBoardList();
        System.out.println("boardList = " + boardList);
        return boardList;
    }

    // 상세 글 보기(게시글 페이지 이동)
    @GetMapping("/{boardId}")
    @ResponseBody
    public BoardResponseDto detail(@PathVariable("boardId") Long boardId) {
        return boardService.getPost(boardId);
    }

    // 글쓰기 페이지
    @GetMapping("/write-form")
    public String write() {
        return "board/write-form";
    }

    // 글쓰기 뒤 POST로 DB에 저장
    // 글쓰기 뒤 /list 경로로 리디렉션
    @PostMapping("/write-form")
    @ResponseBody
    public void write(@RequestPart("data") BoardRequestDto boardRequestDto,
                      @RequestPart(required = false) MultipartFile image,
                      @AuthenticationPrincipal Member writer){
        boardService.savePost(boardRequestDto, image, writer);
    }

    @GetMapping("/{memberId}/profile")
    @ResponseBody
    public List<MemberDto> getBoardByMemberId(@PathVariable Long memberId) {
        return boardService.getBoardByMemberId(memberId);
    }
}