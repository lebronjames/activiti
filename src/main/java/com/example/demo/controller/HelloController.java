package com.example.demo.controller;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
	
	//获取默认流程引擎实例 会自动 读取activiti.cfg.xml
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deployWithClassPath() {
		Deployment deployment = processEngine.getRepositoryService() // 获取部署相关实例
				.createDeployment().name("HelloWorld")// 创建部署
				.addClasspathResource("diagrams/HelloWorld.bpmn") // 加载资源文件
				.addClasspathResource("diagrams/HelloWorld.png").deploy();// 部署
		System.out.println("流程:" + deployment.getId() + "," + deployment.getName() );

	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void start() {
		ProcessInstance processInstance = processEngine.getRuntimeService() // 运行时service
				.startProcessInstanceByKey("myFirstProcess");

		System.out.println(processInstance.getProcessDefinitionId());
		System.out.println(processInstance.getProcessInstanceId());
		System.out.println(processInstance.getName());
		System.out.println(processInstance.getId());
		System.out.println(processInstance.getDeploymentId());
	}

	/**
	 * 查看任务
	 */
	@Test
	public void findTask() {
		List<Task> tasks = processEngine.getTaskService() // 任务相关
				.createTaskQuery() // 创建任务查询
				.taskAssignee("java1234_分配给谁").list();

		for (Task task : tasks) {

			System.out.println(task.getAssignee());
			System.out.println(task.getProcessDefinitionId());
			System.out.println(task.getName());
			System.out.println(task.getCreateTime());
			System.out.println(task.getId());
		}
	}
	
	/**
	 * 完成任务
	 */
	@Test
	public void completeTask()
	{
		processEngine.getTaskService() // 任务相关
		.complete("2505");
	}
	
	@Test
	public void processState()
	{
	 ProcessInstance pi =	processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId("2501").singleResult();
	
	 if(null != pi)
	 {
		 System.out.println("流程正在执");
	 }
	 else{
		 System.out.println("流程已结束");
	 }
	}
}
