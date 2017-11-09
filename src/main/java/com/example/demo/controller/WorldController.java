package com.example.demo.controller;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/world")
@Api(value = "world", description = "HW流程管理")
public class WorldController {

	private static final Logger logger = LoggerFactory.getLogger(WorldController.class);
	
	//获取默认流程引擎实例 会自动读取activiti.cfg.xml
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	@GetMapping("/deploy/classpath")
	public void deployWithClassPath() {
		Deployment deployment = processEngine.getRepositoryService() //获取部署相关实例
				.createDeployment().name("HelloWorld") //创建部署
				.addClasspathResource("/diagrams/HelloWorld.bpmn")//加载资源文件
				.addClasspathResource("/diagrams/HelloWorld.png").deploy();//部署
		logger.info("HelloWorld流程部署,ID:{},name:{},时间:{}",deployment.getId(),
				deployment.getName(),deployment.getDeploymentTime());
		
	}
	
	/**
	 * 启动流程实例
	 */
	@GetMapping("/start")
	public void start() {
		ProcessInstance processInstance = processEngine.getRuntimeService()//获取运行时相关实例
				.startProcessInstanceById("helloWorldProcess");
		logger.info("HelloWorld启动流程,ID:{},Name:{},DeploymentId:{},ProcessInstanceId:{},ProcessDefinitionId:{}",
				processInstance.getId(),processInstance.getName(),processInstance.getDeploymentId(),
				processInstance.getProcessInstanceId(),processInstance.getProcessDefinitionId());
	}
	
	/**
	 * 查看任务
	 */
	@GetMapping("/findTask")
	public void findTask() {
		List<Task> tasks = processEngine.getTaskService().//获取任务相关实例
				createTaskQuery().taskAssignee("hw受托人").list();
		for(Task task : tasks) {
			logger.info("HelloWorld流程任务,ID:{},Name:{},指定人Assignee:{},Owner:{},CreateTime:{},ProcessDefinitionId:{},ProcessInstanceId:{}",
					task.getId(),task.getName(),task.getAssignee(),task.getOwner(),
					task.getCreateTime(),task.getProcessDefinitionId(),task.getProcessInstanceId());
		}
	}
	
	/**
	 * 任务完成
	 */
	@GetMapping("/completeTask")
	public void completeTask() {
		processEngine.getTaskService()//获取任务相关实例
			.complete("2505");
	}
	
	/**
	 * 获取流程状态
	 */
	@GetMapping("/process/status")
	public void getProcessStatus() {
		ProcessInstance processInstance = processEngine.getRuntimeService()//获取运行时相关实例
			.createProcessInstanceQuery().processInstanceId("2501").singleResult();
		if(null == processInstance) {
			logger.info("HelloWorld流程已结束");
		}else {
			logger.info("HelloWorld流程正在执行");
		}
	}
}
