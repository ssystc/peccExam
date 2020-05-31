package com.pecc.dj.exam.constant;

public enum ErrorDefEnum {
	NORMAL_CODE("000000", "运行正常..."),

	USER_PASS_ERROR("000001", "用户名或密码错误！"),
	
	USER_UPDATE_ERROR("000002", "密码修改失败！"),
	
	DZB_ERROR("001001", "数据库中不含有该党支部标识或党支部表为空!"),
	
	HAND_ANSWER_ERROR("001002", "上传答案失败"),
	
	QUERY_SCORE_ERROR("001003", "查询成绩异常"),
	
	CANDIDATE_ID_ERROR("001004", "考生ID不存在于数据库考试答案信息表中！"),
	
	USER_ID_ERROR("001005", "考生ID不存在于用户信息表中！"),
	
	SELECT_NETX_EXAM_ERROR("001006", "选择下次考试dzb标识失败！"),
	
	DZB_EXISTED("001007", "dzb标识已存在！"),
	
	GENERATE_EXAM_PAPER_ERROR("002001", "生成考卷失败！"),
	
	ADD_CANDIDATE_ERROR("002002", "为考卷添加考生名单失败!"),
	
	GET_QUESTIONS_BY_EXAM_PAPER_ID_ERROR("002003", "根据考卷ID获取题目失败!"),
	
	EXAM_NAME_EXISTED("002004", "该考卷名称已存在"),
	
	UPLOAD_FILE_ISNULL("003001", "上传文件为空！"),
	
	UPLOAD_FILE_PARSE_ERROR("003002", "上传文件解析失败！"),
	
	COLUMN_HEAD_ERROR("003003", "题目excel列头错误，应该分别为ID,TYPE,QUESTION,OPTIONS,ANSWER,SCORE!"),

    PLATFORM_ERROR("005001", "考试系统异常，请稍后重试！");
    
    private String code;

    private String message;

    ErrorDefEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
	
}
