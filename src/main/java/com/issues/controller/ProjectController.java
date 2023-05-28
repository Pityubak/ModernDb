package com.issues.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.issues.model.Project;
import com.issues.model.Task;
import com.issues.repository.ProjectRepository;
import com.issues.repository.TaskRepository;

@RestController
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping("/projects")
	public ResponseEntity<List<Project>> list() {
		return new ResponseEntity<>(this.projectRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/project/{id}")
	public ResponseEntity<Project> get(@PathVariable String id) {
		Optional<Project> project = this.projectRepository.findById(id);
		if (project.isPresent()) {
			return new ResponseEntity<>(project.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/project")
	public ResponseEntity<Project> create(@RequestBody Project project) {
		return new ResponseEntity<>(this.projectRepository.save(project), HttpStatus.CREATED);
	}

	@PutMapping("/project/{id}")
	public ResponseEntity<Project> update(@PathVariable String id, @RequestBody Project project) {
		Optional<Project> originalProject = this.projectRepository.findById(id);
		if (originalProject.isPresent()) {
			Project updatedProject = originalProject.get()
					.setDeadline(project.getDeadline())
					.setDescription(project.getDescription())
					.setStartTime(project.getStartTime())
					.setStatus(project.getStatus())
					.setTitle(project.getTitle())
					.setTasks(project.getTasks());

			return new ResponseEntity<>(this.projectRepository.save(updatedProject), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/project/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		this.projectRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/project/{id}/task")
	public ResponseEntity<Project> addTaskToProject(@PathVariable String id, @RequestBody List<String> taskIds) {
		Optional<Project> optionalProject = this.projectRepository.findById(id);

		if (optionalProject.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}

		Project updatedProject = optionalProject.get();

		Set<Task> tasksToAdd = taskIds.stream().map(taskId -> this.taskRepository.findById(taskId))
				.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());

		updatedProject.setTasks(tasksToAdd);

		return new ResponseEntity<>(this.projectRepository.save(updatedProject), HttpStatus.OK);
	}
}
