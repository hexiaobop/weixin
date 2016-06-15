package com.hxb.everydaytask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.hxb.message.SendAllUserTask;
import com.hxb.util.DateUtils;

/**
 * 
 * @des :每日任务启动,比如自动向用户发送推送消息
 * @author hexiaobo
 * @email absod0711@gmail.com
 * @date 2016年5月7日
 */
public class EveryDayTask {
	private static final long GAPTIME = 24 * 60 * 60 * 1000;

	public static void startTask(Class<? extends TimerTask> taskClass, int[] time, boolean isStart)
			throws InstantiationException, IllegalAccessException {
		System.out.println("start.......");
		int hour = time[0];
		int minutes = time[1];
		int second = time[2];
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, second);

		if (calendar.getTime().before(new Date())) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			System.out.println("starttime:" + DateUtils.getDateString(calendar.getTime()));
		}
		System.out.println("starttime:" + DateUtils.getDateString(calendar.getTime()));

		Timer timer = new Timer();
		TimerTask timerTask = taskClass.newInstance();
		timer.scheduleAtFixedRate(timerTask, calendar.getTime(), GAPTIME);
		System.out.println("end......");
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		startTask(SendAllUserTask.class, new int[] { 15, 52, 0 }, true);

	}
}
