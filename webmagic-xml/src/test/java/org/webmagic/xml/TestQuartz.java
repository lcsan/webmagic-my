package org.webmagic.xml;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.StatefulJob;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class TestQuartz {

	public class QuartzJob implements Job {

		@Override
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			System.out.println("aaa");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public class QuartzJob1 implements StatefulJob {

		@Override
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			System.out.println("aaa");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws SchedulerException {
		// 绑定Job和Trigger
		// 创建调度者工厂
		SchedulerFactory sf = new StdSchedulerFactory();
		// 创建一个调度者
		Scheduler sched = sf.getScheduler();

		// 构建Job
		JobDetail job = JobBuilder.newJob(QuartzJob1.class).build();
		// .withIdentity("job1", "group1")
		// 构建Trigger
		// Date runTime = DateBuilder.evenMinuteDate(new
		// Date(System.currentTimeMillis()));
		// Trigger trigger =
		// TriggerBuilder.newTrigger().withIdentity("trigger1",
		// "group1").startAt(runTime).build();
		Trigger trigger = TriggerBuilder.newTrigger()
				// .withIdentity("trigger1", "group1")
				.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();

		// 注册并进行调度
		sched.scheduleJob(job, trigger);
		// 启动调度
		sched.start();

		/*
		 * Thread.sleep(3000); sched.shutdown();
		 */
	}
}
