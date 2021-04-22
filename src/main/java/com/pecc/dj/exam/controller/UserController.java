package com.pecc.dj.exam.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pecc.dj.exam.constant.ErrorDefEnum;
import com.pecc.dj.exam.entity.AllUserInfo;
import com.pecc.dj.exam.response.CommonResponse;
import com.pecc.dj.exam.service.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pecc/user")
@Api(tags = {"用户相关接口"})
public class UserController {
	
	@Autowired
	public UserServiceImpl userService;
	
	@ApiOperation(value = "用户登录")
	@ApiImplicitParams({
        @ApiImplicitParam(name="params", dataType = "body", value = "{'userId':'userId', 'password':'password'}")
	})
    @PostMapping(value = "/auth")
	public ResponseEntity<?> authUser( @RequestBody Map<String, String> params) {
		String userId = params.get("userId");
		String password = params.get("password");
		AllUserInfo info = userService.userAuth(userId, password);
		if (info != null) {
			return CommonResponse.success(info);
		} else {
			return CommonResponse.error(ErrorDefEnum.USER_PASS_ERROR, HttpStatus.OK);
		}
    }
	
	
	@ApiOperation(value = "修改密码")
	@ApiImplicitParams({
        @ApiImplicitParam(name="params", dataType = "body", value = "{'userId':'userId', 'oldPass':'oldPass', 'newPass':'newPass'}")
	})
    @PostMapping(value = "/uptate")
	public ResponseEntity<?> updateUserPass( @RequestBody Map<String, String> params) {
		String userId = params.get("userId");
		String oldPass = params.get("oldPass");
		String newPass = params.get("newPass");
		boolean result = userService.updatePass(userId, oldPass, newPass);
        if(result){
            return CommonResponse.success("密码修改成功！");
        }
        return CommonResponse.error(ErrorDefEnum.USER_UPDATE_ERROR, HttpStatus.BAD_REQUEST);
    }

	
//	@ApiOperation(value = "用户登录(包含examId,验证该用户是否在此次考试中)")
//	@ApiImplicitParams({
//        @ApiImplicitParam(name="params", dataType = "body", value = "{'userId':'userId', 'password':'password', 'examPaperId':'examPaperId'}")
//	})
//    @PostMapping(value = "/authWithExamId")
//	public ResponseEntity<?> authWithExamId( @RequestBody Map<String, String> params) {
//		String userId = params.get("userId");
//		String password = params.get("password");
//		String examPaperId = params.get("examPaperId");
//		boolean isAuth = userService.userAuthWithExamId(userId, password, examPaperId);
//		if (isAuth == true) {
//			return CommonResponse.success("用户名密码验证成功！");
//		} else {
//			return CommonResponse.error(ErrorDefEnum.USER_PASS_ERROR, HttpStatus.UNAUTHORIZED);
//		}
//    }
}
