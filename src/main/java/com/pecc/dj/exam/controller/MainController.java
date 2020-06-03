package com.pecc.dj.exam.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pecc.dj.exam.constant.ErrorDefEnum;
import com.pecc.dj.exam.entity.DzbEntity;
import com.pecc.dj.exam.exception.ExamException;
import com.pecc.dj.exam.request.HandQuestionsRequest;
import com.pecc.dj.exam.response.CommonResponse;
import com.pecc.dj.exam.response.GetScoreResponse;
import com.pecc.dj.exam.service.ExamService;
import com.pecc.dj.exam.util.ExcelReadUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/pecc/exam")
@Api(tags = {"考试相关接口"})
public class MainController {
	
	@Autowired
	private ExamService examServiceImpl;

	@ApiOperation(value = "根据党支部获取题目")
    @GetMapping(value = "/getExam/{dzb}")
	public List<Map<String, String>> distributeQuestions(@ApiParam(value="党支部标识") @PathVariable String dzb) {
		return examServiceImpl.getQuestions(dzb, false);
    }
	
	@ApiOperation(value = "根据党支部获取题目(展示学习)")
    @GetMapping(value = "/getExamForLearn/{dzb}")
	public List<Map<String, String>> distributeQuestionsForLearn(@ApiParam(value="党支部标识") @PathVariable String dzb) {
		return examServiceImpl.getQuestions(dzb, true);
    }
	
	@ApiOperation(value = "选定下一次考试题目，即选中下次考试的dzb标识")
    @GetMapping(value = "/selectDzbForNextExam/{dzb}")
	public ResponseEntity<?> selectDzbForNextExam(@ApiParam(value="党支部标识") @PathVariable String dzb) {
		boolean flag = examServiceImpl.selectDzbForNextExam(dzb);
		if (flag) {
			return CommonResponse.success("选择下次考试题目为：“" + dzb + "”考题");
		}
		return CommonResponse.error(ErrorDefEnum.SELECT_NETX_EXAM_ERROR, HttpStatus.BAD_REQUEST);
    }
	
	@ApiOperation(value = "获取下次考试的dzb标识")
	@GetMapping(value = "/getNextExamDzb")
	public ResponseEntity<?> getNextExamDzb(){
		DzbEntity dzbEntity = examServiceImpl.getNextExamDzb();
		if(dzbEntity != null) {
			return CommonResponse.success(dzbEntity);
		}
		return CommonResponse.error(ErrorDefEnum.DZB_ERROR, HttpStatus.BAD_REQUEST);
	}
	
	
	@ApiOperation(value = "上传答案")
	@ApiImplicitParams({
        @ApiImplicitParam(name="questionSequence", dataType = "body", value = "questionSequence中是题目ID按序排列，如\"questionSequence\":\"ID2,ID1,ID3\"，第一题是ID2，第二题是ID1，第三题是ID3，中间用英文逗号隔开"),
        @ApiImplicitParam(name="candidateAnswer", dataType = "map", value = "map格式,{'题目ID1' : '题目答案', '题目ID2':'题目答案'}")
	})
    @PostMapping(value = "/uploadAnswer")
	public ResponseEntity<?> uploadAnswer(@RequestBody HandQuestionsRequest handRequest) {
		examServiceImpl.handQuestions(handRequest);
		return CommonResponse.success("答案上传成功！");
    }
	
	@ApiOperation(value = "通过用户id（即邮箱前缀）获取上一次考试的分数")
	@GetMapping(value = "/getLastScore/{userId}")
	public GetScoreResponse getScore( @ApiParam(value="考试人员Id") @PathVariable String userId) {
		GetScoreResponse responseBody = examServiceImpl.getLastScore(userId);
		return responseBody;
    }
	
	@ApiOperation(value = "通过用户id（即邮箱前缀）获取所有考试的分数")
	@GetMapping(value = "/getAllScore/{userId}")
	public List<GetScoreResponse> getAllScore( @ApiParam(value="考试人员Id") @PathVariable String userId) {
		List<GetScoreResponse> responseBody = examServiceImpl.getAllScore(userId);
		return responseBody;
    }
	
	@ApiOperation(value = "通过党支部标识获取所有考生成绩")
	@GetMapping(value = "/getScoreByDzb/{dzb}")
	public List<Map<String, String>> getScoreByDzb( @ApiParam(value="dzb标识") @PathVariable String dzb) {
		List<Map<String, String>> result = examServiceImpl.getScoreByDzb(dzb);
		return result;
    }
	
	
	@ApiOperation(value = "获取党支部信息")
	@GetMapping(value = "/getDzbInfo")
	public List<DzbEntity> getDzbInfo() {
		List<DzbEntity> result = examServiceImpl.getAllDzb();
		return result;
    }
	
	@ApiOperation(value = "上传习题excel(PS：请按照固定表格样式上传xlsx文件！)")
	@PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, 
    		@RequestParam(value = "dzb", required = true) String dzb,
    		@RequestParam(value = "dzbName", required = false) String name) {
        if (file.isEmpty()) {
            return CommonResponse.error(ErrorDefEnum.UPLOAD_FILE_ISNULL, HttpStatus.BAD_REQUEST);
        }
        try {
			examServiceImpl.uploadQuestionExcel(file.getInputStream(), dzb, name);
			return CommonResponse.success("习题入库成功！");
		} catch(ExamException e){
			throw e;
		}catch (IOException e) {
			throw new ExamException(ErrorDefEnum.UPLOAD_FILE_PARSE_ERROR);
		}
    }

	
	
//	@ApiOperation(value = "获取所有考卷信息")
//	@GetMapping(value = "/getAllExamPaper")
//	public List<ExamPaperInfo> getAllExamPaper(){
//		return examServiceImpl.getAllExamPaper();
//	}
//	
//	@ApiOperation(value = "根据出题人选择的题目列表等信息生成考卷")
//    @PostMapping(value = "/generateExam")
//	public ResponseEntity<?> generateExamPaper(@RequestBody GenerateExamPaperRequest request) {
//		int examId = examServiceImpl.generateExam(request);
//		Map<String, String> succResult = new HashMap<String, String>();
//		succResult.put("examPaperId",	examId+"");
//		succResult.put("code", ErrorDefEnum.NORMAL_CODE.getCode());
//		succResult.put("message", "生成试卷成功！");
//		return CommonResponse.success(succResult);
//    }
//	
//	@ApiOperation(value = "为某一套考卷更新考生列表")
//	@ApiImplicitParams({
//        @ApiImplicitParam(name="params", dataType = "body", value = "{'examPaperId':'examPaperId', 'candidateIdList':'candidateIdList'}")
//	})
//    @PostMapping(value = "/addCandidateForExam")
//	public ResponseEntity<?> addCandidateForExam(@RequestBody Map<String, String> params) {
//		String examPaperId = params.get("examPaperId");
//		String candidateIdLlist = params.get("candidateIdList");
//		examServiceImpl.updateCandidateToExam(candidateIdLlist, examPaperId);
//		return CommonResponse.success("为考卷添加考生列表成功!");
//    }
//	
//	@ApiOperation(value = "根据考卷ID获取题目")
//    @GetMapping(value = "/getExamByExamPaperId/{examPaperId}")
//	public List<Map<String, String>> getQuestionsByExamPaperId(@ApiParam(value="考卷ID") @PathVariable String examPaperId) {
//		return examServiceImpl.generateExamByExamId(examPaperId);
//    }
//
//	@ApiOperation(value = "通过考卷ID获取所有考生成绩")
//	@GetMapping(value = "/getScoresByExamId/{examId}")
//	public List<GetScoreResponse> getScoresByExamId( @ApiParam(value="考卷ID") @PathVariable String examId) {
//		List<GetScoreResponse> result = examServiceImpl.getScoreByExamId(examId);
//		return result;
//    }
}
