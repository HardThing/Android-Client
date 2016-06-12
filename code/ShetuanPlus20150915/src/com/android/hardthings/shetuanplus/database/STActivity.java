package com.android.hardthings.shetuanplus.database;

public class STActivity {

	public static final String TAG = "Activity";

	public int id;

	public String title;

	public String startDate;

	public String endDate;

	public String location;

	public String desc;

	public String founder;//发起人

	public int likes_count;//点赞数量

	public int total_donate;//打赏总数

	public String logoUrl;//活动logo

	public STActivity(String title,String logoUrl,String founder){
		this.title = title;
		this.logoUrl = logoUrl;
		this.founder = founder;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder s = new StringBuilder();
		s.append("{").append(TAG).append(":");
		s.append("id = ").append(id);
		s.append("title = ").append(title);
		s.append("startDate = ").append(startDate);
		s.append("endDate = ").append(endDate);
		s.append("location = ").append(location);
		s.append("desc = ").append(desc);
		s.append("founder = ").append(founder);
		s.append("likes_count = ").append(likes_count);
		s.append("total_donate = ").append(total_donate);
		s.append("logoUrl = ").append(logoUrl);
		s.append("}");
		return s.toString();
	}	
}
