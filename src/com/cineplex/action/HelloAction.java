package com.cineplex.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.ActionSupport;


public class HelloAction extends ActionSupport{

	private String another ;
	public String getAnother() {
		return another;
	}
	public void setAnother(String another) {
		this.another = another;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("���е�������");
		String s ="���е���";
		another="��һ���ַ���";
		ServletActionContext.getContext().getSession().put("mystring", s);
		return SUCCESS;
	}

	
	
}
