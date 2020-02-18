package hustime.community.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import hustime.community.schedule.common.ScheduleFileUtils;
import hustime.community.schedule.dto.ScheduleDto;
import hustime.community.schedule.dto.ScheduleFileDto;
import hustime.community.schedule.mapper.ScheduleMapper;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	
	
	@Autowired
	private ScheduleMapper boardMapper;
	
	@Autowired
	private ScheduleFileUtils fileUtils;
	
	@Override
	public List<ScheduleDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}
	
	@Override
	public List<ScheduleDto> selectTopFiveBoardList() throws Exception {
		return boardMapper.selectTopFiveBoardList();
	}
	
	@Override
	public int selectNewBoardCount() throws Exception{
		System.out.println(boardMapper.selectNewBoardCount()+"@##########");
		return boardMapper.selectNewBoardCount();
	}
	
	@Override
	public void insertBoard(ScheduleDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardMapper.insertBoard(board);
		List<ScheduleFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false){
			boardMapper.insertBoardFileList(list);
		}
	}

	@Override
	public ScheduleDto selectBoardDetail(int boardIdx) throws Exception{
		ScheduleDto board = boardMapper.selectBoardDetail(boardIdx);
		List<ScheduleFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		
		boardMapper.updateHitCount(boardIdx);
		
		return board;
	}
	
	@Override
	public void updateBoard(ScheduleDto board) throws Exception {
		System.out.println(board);
		boardMapper.updateBoard(board);
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		boardMapper.deleteBoard(boardIdx);
	}
	
	@Override
	public ScheduleFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}
}

