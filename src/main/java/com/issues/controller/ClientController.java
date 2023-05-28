package com.issues.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.issues.model.Client;
import com.issues.model.Project;
import com.issues.repository.ClientRepository;
import com.issues.repository.ProjectRepository;

@RestController
public class ClientController {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@GetMapping("/clients")
	public ResponseEntity<List<Client>> list() {
		return new ResponseEntity<>(this.clientRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/client/{id}")
	public ResponseEntity<Client> get(@PathVariable String id) {
		Optional<Client> client = this.clientRepository.findById(id);
		if (client.isPresent()) {
			return new ResponseEntity<>(client.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/client")
	public ResponseEntity<Client> create(@RequestBody Client client) {
		return new ResponseEntity<>(this.clientRepository.save(client), HttpStatus.CREATED);
	}

	@PutMapping("/client/{id}")
	public ResponseEntity<Client> update(@PathVariable String id, @RequestBody Client client) {
		Optional<Client> originalClient = this.clientRepository.findById(id);
		if (originalClient.isPresent()) {
			Client updatedClient = originalClient.get().setActive(client.getActive()).setName(client.getName())
					.setContactInfo(client.getContactInfo()).setProjects(client.getProjects());

			return new ResponseEntity<>(this.clientRepository.save(updatedClient), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/client/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		this.clientRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/client/{id}/project")
	public ResponseEntity<Client> addProjectToClient(@PathVariable String id, @RequestBody List<String> projectIds) {
		Optional<Client> optionalClient = this.clientRepository.findById(id);

		if (optionalClient.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}

		Client updatedClient = optionalClient.get();

		Set<Project> projectToAdd = projectIds.stream().map(projectId -> this.projectRepository.findById(projectId))
				.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());

		updatedClient.setProjects(projectToAdd);

		return new ResponseEntity<>(this.clientRepository.save(updatedClient), HttpStatus.OK);
	}
}
