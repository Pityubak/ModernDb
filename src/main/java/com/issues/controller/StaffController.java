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

import com.issues.model.Staff;
import com.issues.repository.StaffRepository;

@RestController
public class StaffController {

	@Autowired
	private StaffRepository staffRepo;

	@GetMapping("/staffs")
	public ResponseEntity<List<Staff>> list() {
		return new ResponseEntity<>(this.staffRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping("/staff/{id}")
	public ResponseEntity<Staff> get(@PathVariable String id) {
		Optional<Staff> staff = this.staffRepo.findById(id);
		if (staff.isPresent()) {
			return new ResponseEntity<>(staff.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/staff")
	public ResponseEntity<Staff> create(@RequestBody Staff staff) {
		return new ResponseEntity<>(this.staffRepo.save(staff), HttpStatus.CREATED);
	}

	@PutMapping("/staff/{id}")
	public ResponseEntity<Staff> update(@PathVariable String id, @RequestBody Staff staff) {
		Optional<Staff> originalStaff = this.staffRepo.findById(id);
		if (originalStaff.isPresent()) {
			Staff updatedStaff = originalStaff.get().setDisplayName(staff.getDisplayName())
					.setEmailAddress(staff.getEmailAddress()).setPassword(staff.getPassword())
					.setUserName(staff.getUserName());

			return new ResponseEntity<>(this.staffRepo.save(updatedStaff), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/staff/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		this.staffRepo.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
