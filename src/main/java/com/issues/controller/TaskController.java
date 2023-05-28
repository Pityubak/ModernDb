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

import com.issues.model.Task;
import com.issues.model.Staff;
import com.issues.repository.StaffRepository;
import com.issues.repository.TaskRepository;

@RestController
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private StaffRepository staffRepository;

	@GetMapping("/tasks")
	public ResponseEntity<List<Task>> list() {
		return new ResponseEntity<>(this.taskRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/task/{id}")
	public ResponseEntity<Task> get(@PathVariable String id) {
		Optional<Task> task = this.taskRepository.findById(id);
		if (task.isPresent()) {
			return new ResponseEntity<>(task.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/task")
	public ResponseEntity<Task> create(@RequestBody Task task) {
		return new ResponseEntity<>(this.taskRepository.save(task), HttpStatus.CREATED);
	}

	@PutMapping("/task/{id}")
	public ResponseEntity<Task> update(@PathVariable String id, @RequestBody Task task) {
		Optional<Task> originalTask = this.taskRepository.findById(id);
		if (originalTask.isPresent()) {
			Task updatedTask = originalTask.get()
					.setPriority(task.getPriority())
					.setStaffs(task.getStaffs())
					.setStatus(task.getStatus())
					.setTitle(task.getTitle());

			return new ResponseEntity<>(this.taskRepository.save(updatedTask), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/task/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		this.taskRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/task/{id}/staff")
	public ResponseEntity<Task> addStaffToTask(@PathVariable String id, @RequestBody List<String> staffIds) {
		Optional<Task> optionalTask = this.taskRepository.findById(id);

		if (optionalTask.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}

		Task updatedTask = optionalTask.get();

		Set<Staff> staffToAdd = staffIds.stream().map(taskId -> this.staffRepository.findById(taskId))
				.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());

		updatedTask.setStaffs(staffToAdd);

		return new ResponseEntity<>(this.taskRepository.save(updatedTask), HttpStatus.OK);
	}
}
