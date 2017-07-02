package com.itheima.wechat.domain;

import java.util.List;

public class NewsMessage extends BaseMessage {
	private int ArticleCount;//图文消息
	private List<News> Articles;
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<News> getArticles() {
		return Articles;
	}
	public void setArticles(List<News> articles) {
		Articles = articles;
	}
	
	
}
