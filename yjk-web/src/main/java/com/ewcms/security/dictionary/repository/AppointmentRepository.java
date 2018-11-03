package com.ewcms.security.dictionary.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.security.dictionary.entity.Appointment;

public interface AppointmentRepository extends BaseRepository<Appointment, Long> {
	Appointment findByName(String name);
}
