package com.hexaware.gtt.lms.controllers;
 
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
 
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
import com.hexaware.gtt.lms.dto.PartnerDto;
import com.hexaware.gtt.lms.entities.Partner;
import com.hexaware.gtt.lms.exception.DuplicateDataException;
import com.hexaware.gtt.lms.exception.ResourceDeletionException;
import com.hexaware.gtt.lms.exception.ResourceNotFoundException;
import com.hexaware.gtt.lms.services.PartnerService;
 
import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/lms/api/v1/partner/")
@ResponseBody
public class PartnerController {
	private PartnerService partnerService;
	private ModelMapper modelmapper;
	@Autowired
	public PartnerController(PartnerService partnerService, ModelMapper modelmapper) {
		this.partnerService = partnerService;
		this.modelmapper = modelmapper;
	}	
	// http://localhost:8080/lms/api/v1/partner/getPartnerById/?id=
	@GetMapping("getPartnerById/")
	public ResponseEntity<?> getbyId(@RequestParam("id") UUID id)
	{
		try{
			Partner part=partnerService.getPartnerById(id);
			PartnerDto partDto=modelmapper.map(part, PartnerDto.class);
			return ResponseEntity.ok(partDto);
		}catch(ResourceNotFoundException e){
			return ResponseEntity.ok(e.getMessage());
		}		
	}
	// http://localhost:8080/lms/api/v1/partner/register/	
	@PostMapping("register/")
	public ResponseEntity<?> createPartner(@Valid @RequestBody PartnerDto prtDto) 
	{
		try {
			if(prtDto.getCountryCode()==0) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Please give country code");
			}
			Partner part;
			part = partnerService.createPartner(prtDto);
			PartnerDto partDto=modelmapper.map(part,PartnerDto.class);
			return ResponseEntity.ok(partDto);
		} catch (DuplicateDataException e) {
				return ResponseEntity.ok(e.getMessage());
		}
	}
	// http://localhost:8080/lms/api/v1/partner/partnercount/
	@GetMapping("partnercount/")
	public ResponseEntity<?> getPartnerCount(){
		long partnerCount = partnerService.getPartnerCount();
		return ResponseEntity.ok(partnerCount);
	}
	// http://localhost:8080/lms/api/v1/partner/getAllPartners/
	@GetMapping("getAllPartners/")
	public ResponseEntity<?> getAllPartners(){
		try {
			List<Partner> partList = partnerService.getAllPartners();
			List<PartnerDto> partDtoList = new ArrayList<>();
			for(Partner p: partList) {
				partDtoList.add(modelmapper.map(p, PartnerDto.class));
			}
			return ResponseEntity.ok(partDtoList);
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	// http://localhost:8080/lms/api/v1/partner/updatePartner/?email=amitabh.dei@example.com
	@PutMapping("updatePartner/")
	public ResponseEntity<?> updatePartner(@RequestParam("email") String email, @Valid @RequestBody PartnerDto partnerDto){
		try {
			Partner partner = partnerService.updatePartner(email, partnerDto);
			PartnerDto partDto = modelmapper.map(partner, PartnerDto.class);
			return ResponseEntity.ok(partDto);
		}catch(ResourceNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	// http://localhost:8080/lms/api/v1/partner/deletePartnerById/?id=ca95f0e3-b162-404f-9d32-f01d01dc6c68
	@DeleteMapping("deletePartnerById/")
	public ResponseEntity<?> deletePartner(@RequestParam("id") UUID id){
		try {
			boolean status = partnerService.deletePartner(id);
			if(status) {
				return ResponseEntity.ok("Successfully deleted partner");
			}
			else {
				return ResponseEntity.ok("Unsuccessfull deletion attempt");
			}
		}catch(ResourceDeletionException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("partnerLogin/")
	public ResponseEntity<String> Loginpartner(@RequestParam ("email") String email,@RequestParam("pwd") String pswd)
	{
		String part=partnerService.loginPartner(email, pswd);
		return ResponseEntity.ok(part);
	}
 

}