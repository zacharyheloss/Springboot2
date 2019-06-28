package com.zachary.springboot.blog.pushlian.damain;

import java.io.Serializable;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "blog",type = "blog")
public class Esblog implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Esblog() {//��ֱֹ��ʹ��
		
	}
	
	public Esblog(String title,String summary,String content) {
		this.title=title;
		this.summary=summary;
		this.content=content;
	}
	
	/**
	 * ���I
	 */
	@Id
	private String id;
	
	/**
	 * ���}
	 */
	private String title;
	
	/**
	 * ժҪ
	 */
	private String summary;
	
	/**
	 * ����
	 */
	private String content;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return String.format("EsBlog[id='%s',title='%s',summary='%s',content='%s']", 
				id,title,summary,content);
	}

}
