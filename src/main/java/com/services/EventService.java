package com.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.entity.Event;

public interface EventService {

	public void addEvent(HttpServletRequest req) throws Exception;

	public List<Event> getEvents();
	
	public void deleteEvent(Long id) throws Exception;

}
