package com.ewcms.security.dictionary.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.security.dictionary.entity.Appointment;
import com.ewcms.security.dictionary.repository.AppointmentRepository;

@Service
public class AppointmentService extends BaseService<Appointment, Long> {

	private AppointmentRepository getAppointmentRepository() {
		return (AppointmentRepository) baseRepository;
	}
	
	public Appointment findByName(String name) {
		return getAppointmentRepository().findByName(name);
	}
}
