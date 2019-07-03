package com.zachary.springboot.blog.pushlian.api.req;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;


public class UserReq {

	/**
	 * 用戶對象
	 */
	
	@Valid
	@NotNull
	private UserType userType;
	
	
	
	public UserType getUserType() {
		return userType;
	}



	public void setUserType(UserType userType) {
		this.userType = userType;
	}



	static class UserType{
		
		@NotNull
		private Integer idds;
		
		
		/*
		 * @NotBlank(message = "dogs不能为空！")
		 * 
		 * @Size(max=3,message="支付完成时间长度不能超过{max}位")
		 */
		private String dos;
		
		/* @Range(min = 1,max = 5,message = "字段长度不够") */
		private Long shows;
		
		/* @Pattern(regexp = "0[0123]", message = "状态只能为00或01或02或03") */
		private String status;
		
		/* @NotNull */
		private Date createTime;

		public Integer getIdds() {
			return idds;
		}

		public void setIdds(Integer idds) {
			this.idds = idds;
		}

		public String getDos() {
			return dos;
		}

		public void setDos(String dos) {
			this.dos = dos;
		}

		public Long getShows() {
			return shows;
		}

		public void setShows(Long shows) {
			this.shows = shows;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
	}
	
	public static void main(String[] args) {
		UserReq userReq=new UserReq();
		
		UserType userType=new UserType();
		userType.setIdds(1);
		
		userReq.setUserType(userType);
		
		System.out.println(JSONObject.toJSONString(userReq));
	}
}
