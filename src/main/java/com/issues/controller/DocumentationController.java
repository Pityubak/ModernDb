package com.issues.controller;

import java.util.List;
import java.util.Optional;

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

import com.issues.model.Documentation;
import com.issues.model.Task;
import com.issues.repository.DocumentationRepository;
import com.issues.repository.TaskRepository;

@RestController
public class DocumentationController {

	@Autowired
	private DocumentationRepository documentationRepository;
	
	@Autowired
	private TaskRepository taskRepository;

	@GetMapping("/documents")
	public ResponseEntity<List<Documentation>> list() {
		return new ResponseEntity<>(this.documentationRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/document/{id}")
	public ResponseEntity<Documentation> get(@PathVariable String id) {
		Optional<Documentation> doc = this.documentationRepository.findById(id);
		if (doc.isPresent()) {
			return new ResponseEntity<>(doc.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/document")
	public ResponseEntity<Documentation> create(@RequestBody Documentation doc) {
		return new ResponseEntity<>(this.documentationRepository.save(doc), HttpStatus.CREATED);
	}

	@PutMapping("/document/{id}")
	public ResponseEntity<Documentation> update(@PathVariable String id, @RequestBody Documentation doc) {
		Optional<Documentation> originalDoc = this.documentationRepository.findById(id);
		if (originalDoc.isPresent()) {
			Documentation updatedDoc = originalDoc.get()
					.setApproved(doc.getApproved())
					.setContent(doc.getContent())
					.setDate(doc.getDate())
					.setTask(doc.getTask())
					.setTitle(doc.getTitle());

			return new ResponseEntity<>(this.documentationRepository.save(updatedDoc), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/document/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		this.documentationRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/document/{docId}/task/{taskId}")
	public ResponseEntity<Documentation> addStaffToTask(@PathVariable String docId, @PathVariable String taskId) {
		Optional<Documentation> optionalDoc = this.documentationRepository.findById(docId);

		if (optionalDoc.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}

		Documentation updatedDocumentation = optionalDoc.get();

		Optional<Task> taskToAdd = this.taskRepository.findById(taskId);

		if(taskToAdd.isPresent()) {
			updatedDocumentation.setTask(taskToAdd.get());

			return new ResponseEntity<>(this.documentationRepository.save(updatedDocumentation), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
