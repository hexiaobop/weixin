package com.hxb.message;

import java.util.ArrayList;
import java.util.List;

import com.AppConstants;

public class ImageAndText {
	
	private List<Message> articles;		
	public List<Message> getArticles() {
		articles = new ArrayList<>();
		Message message =new  Message();
		message.setAuthor("hxb");
		message.setContent("真诚无人爱,套路得人心");
		message.setTitle("这是标题");
		message.setContent_source_url(AppConstants.WEB_AUTHORIZE_URL);
		message.setContent(" 打折了,领券了,夺宝了");
		message.setShow_cover_pic(1);
		message.setThumb_media_id(AppConstants.EVERYDAYIMAGE1);
		
		Message message1 =new  Message();
		message1.setAuthor("hxb");
		message1.setContent("真诚无人爱,套路得人心");
		message1.setTitle("这是标题");
		message1.setContent_source_url(AppConstants.WEB_AUTHORIZE_URL);
		message1.setContent(" 打折了,领券了,夺宝了");
		message1.setShow_cover_pic(1);
		message1.setThumb_media_id(AppConstants.EVERYDAYIMAGE0);
		articles.add(message);
		articles.add(message1);
		return articles;
	}

	public void setArticles(List<Message> articles) {
		this.articles = articles;
	}

	public class Message
	{
		private String thumb_media_id;
		private String author;
		private String title;
		private String content_source_url ;
		private String content;
		private String digest;
		private int show_cover_pic;
		public String getThumb_media_id() {
			return thumb_media_id;
		}
		public void setThumb_media_id(String thumb_media_id) {
			this.thumb_media_id = thumb_media_id;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent_source_url() {
			return content_source_url;
		}
		public void setContent_source_url(String content_source_url) {
			this.content_source_url = content_source_url;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getDigest() {
			return digest;
		}
		public void setDigest(String digest) {
			this.digest = digest;
		}
		public int getShow_cover_pic() {
			return show_cover_pic;
		}
		public void setShow_cover_pic(int show_cover_pic) {
			this.show_cover_pic = show_cover_pic;
		}
		
		
		
	}
}
