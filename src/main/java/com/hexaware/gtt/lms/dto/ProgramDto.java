package com.hexaware.gtt.lms.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hexaware.gtt.lms.entities.Coupons;
import com.hexaware.gtt.lms.entities.Offers;

public class ProgramDto {
	private String programName;
	 private UUID partnerId;
	private boolean status;
	private List<Coupons> coupons;
	private List<Offers> offers;

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	public ProgramDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProgramDto(String programName, UUID partnerId, boolean status, List<Coupons> coupons, List<Offers> offers,
			LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.programName = programName;
		this.partnerId = partnerId;
		this.status = status;
		this.coupons = coupons;
		this.offers = offers;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<Coupons> getCoupons() {
		return coupons;
	}
	public void setCoupons(List<Coupons> coupons) {
		this.coupons = coupons;
	}
	public List<Offers> getOffers() {
		return offers;
	}
	public void setOffers(List<Offers> offers) {
		this.offers = offers;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	
	public UUID getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(UUID partnerId) {
		this.partnerId = partnerId;
	}

	@Override
	public String toString() {
		return "ProgramDto [programName=" + programName + ", partnerId=" + partnerId + ", status=" + status
				+ ", coupons=" + coupons + ", offers=" + offers + ", startDate=" + startDate + ", endDate=" + endDate
				+ "]";
	}

	
	
	
}
