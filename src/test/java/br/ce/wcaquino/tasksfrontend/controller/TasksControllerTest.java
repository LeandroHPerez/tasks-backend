package br.ce.wcaquino.tasksfrontend.controller;

import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.controller.TaskController;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TasksControllerTest {
	
	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	TaskController taskController = new TaskController();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao(){
		Task task = new Task();
		//task.setTask("Descricao");
		task.setDueDate(LocalDate.now());
		
		try {
			taskController.save(task);
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task task = new Task();
		task.setTask("Descricao");
		//task.setDueDate(LocalDate.now());
		try {
			taskController.save(task);
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task task = new Task();
		task.setTask("Descricao");
		task.setDueDate(LocalDate.of(2010, 1, 1));
		
		try {
			taskController.save(task);
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task task = new Task();
		task.setTask("Descricao");
		task.setDueDate(LocalDate.now());		

		taskController.save(task);
		
		Mockito.verify(taskRepo).save(task); //verifica que o metodo save do taskRepo foi chamado e que recebeu um objeto do tipo task
	}

}
