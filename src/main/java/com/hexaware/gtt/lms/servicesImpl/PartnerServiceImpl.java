package com.hexaware.gtt.lms.servicesImpl;
 
import java.util.List;
import java.util.UUID;
 
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.hexaware.gtt.lms.dto.PartnerDto;
import com.hexaware.gtt.lms.entities.Partner;
import com.hexaware.gtt.lms.exception.DuplicateDataException;
import com.hexaware.gtt.lms.exception.ResourceDeletionException;
import com.hexaware.gtt.lms.exception.ResourceNotFoundException;
import com.hexaware.gtt.lms.repositories.PartnerRepository;
import com.hexaware.gtt.lms.services.PartnerService;
 
@Service
public class PartnerServiceImpl implements PartnerService {
	@Autowired
	private PartnerRepository partnerRepository;
	@Autowired
	private ModelMapper modelmapper;

 
	public Partner createPartner(PartnerDto ptrdto)throws DuplicateDataException
	{
		if(partnerRepository.findByEmail(ptrdto.getEmail())!=null) {
			System.out.println("35");
			throw new DuplicateDataException("partner","email",ptrdto.getEmail());
		}
		else if(partnerRepository.findByContact(ptrdto.getContact())!=null) {
			throw new DuplicateDataException("partner","contact",ptrdto.getContact().toString());
		}
		else {
			System.out.println("else condition");
			Partner partner=modelmapper.map(ptrdto, Partner.class);
			Partner part = partnerRepository.save(partner);
			return part;
		}	
	}

 
	@Override
	public Partner getPartnerById(UUID id) throws ResourceNotFoundException {
		 if(id==null)
		 {
			 throw new ResourceNotFoundException("Partner", "id", id);
		 }
		 Partner part=partnerRepository.findById(id).get();
		return part;
	}
 
	@Override
	public long getPartnerCount() {
		 Long partcount=partnerRepository.count();
		return partcount;
	}
 
	@Override
	public List<Partner> getAllPartners() throws ResourceNotFoundException {
		List<Partner> ptlist=partnerRepository.findAll();
		if(ptlist==null) {
			throw new ResourceNotFoundException("Partners list is empty");
		}
		else {
			return ptlist;
		}
	}
 
	@Override
	public Partner updatePartner(String email, PartnerDto partnerDto) throws ResourceNotFoundException {
		Partner part=this.partnerRepository.findByEmail(email);
		if(part==null)
		{
			throw new ResourceNotFoundException("Partners", "email", email);
		}
		else {
			part.setPartnerName(partnerDto.getPartnerName());
			part.setEmail(partnerDto.getEmail());
			part.setContact(partnerDto.getContact());
			Partner savedPartner = partnerRepository.save(part);
			return savedPartner;
		}
	}
 
	@Override
	public boolean deletePartner(UUID id) throws ResourceDeletionException {
		Partner part=partnerRepository.findById(id).get();
		if(part.equals(null))
		{
			throw new ResourceDeletionException("Partner "+id+" not found to delete");
		}
		partnerRepository.deleteById(id);		
		if(partnerRepository.existsById(id))
			{
			return false;
			}
		else 
		{
			return true;
		}
	}
	
	
	public String loginPartner(String email,String pwd)
	{
		Partner part=partnerRepository.findByEmail(email);
		String emil=part.getEmail();
		String paswd=part.getPassword();
		if(email==emil&&pwd==paswd)
		{
			return "Login in Successful";
		}
		else
		{
			return "Login failed due to Invadlid Credentials";
			}
				
	}
 
 
}