package com.pecc.dj.exam.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pecc.dj.exam.constant.ErrorDefEnum;


public class CommonResponse {

	private CommonResponse() {
        // default private constructor
    }

    public static <T> ResponseEntity<T> success() {
        return new ResponseEntity<T>(HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> success(T body) {
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    
    public static ResponseEntity<RightResponse> success(String message) {
        return new ResponseEntity<>(new RightResponse(message), HttpStatus.OK);
    }

    public static ResponseEntity<ErrResponse> error(ErrorDefEnum err, HttpStatus status) {
        return new ResponseEntity<>(new ErrResponse(err), status);
    }
    
    public static class RightResponse{
    	private String code;
    	private String message;
    	
    	public RightResponse(String message) {
    		this.code = ErrorDefEnum.NORMAL_CODE.getCode();
    		this.message = message;
    	}
    	
    	 public String getCode() {
             return code;
         }

         public void setCode(String code) {
             this.code = code;
         }

         public String getMessage() {
             return message;
         }

         public void setMessage(String message) {
             this.message = message;
         }
    }

    public static class ErrResponse {

        private String code;

        private String message;

        public ErrResponse(ErrorDefEnum err) {
            this.code = err.getCode();
            this.message = err.getMessage();
        }
        public ErrResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
