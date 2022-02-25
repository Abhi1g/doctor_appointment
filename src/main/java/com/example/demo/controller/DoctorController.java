package com.example.demo.controller;

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

import com.example.demo.entity.Doctor;
import com.example.demo.repository.IDoctorRepo;

@RestController
public class DoctorController {
	
	@Autowired
	IDoctorRepo doctorRepo;
	
	@PostMapping("/doctors")
	public ResponseEntity<Doctor> save(@RequestBody Doctor doctor) {
		try {
			return new ResponseEntity<>(doctorRepo.save(doctor), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/doctors")
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		try {
			List<Doctor> list = doctorRepo.findAll();
			if (list.isEmpty() || list.size() == 0) {
				return new ResponseEntity<List<Doctor>>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<List<Doctor>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/doctors/{id}")
	public ResponseEntity<Doctor> getSingleDoctor(@PathVariable Long id) {
		Optional<Doctor> doctor = doctorRepo.findById(id);
		
		if (doctor.isPresent()) {
			return new ResponseEntity<Doctor>(doctor.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<Doctor>(HttpStatus.NOT_FOUND);
		
	}
	
	@PutMapping("/doctors/{id}")
	public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor doctor) {
		
		try {
			return new ResponseEntity<Doctor>(doctorRepo.save(doctor), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/doctors/{id}")
	public ResponseEntity<HttpStatus> deleteDoctor(@PathVariable Long id) {
		try {
			Optional<Doctor> doctor = doctorRepo.findById(id);
			if (doctor.isPresent()) {
				doctorRepo.delete(doctor.get());
			}
			
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
