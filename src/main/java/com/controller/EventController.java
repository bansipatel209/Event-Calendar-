package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.services.EventService;

@Controller
public class EventController {

	@Autowired
	EventService eventService;

	@RequestMapping("/")
	public ModelAndView defaultHome() {
		return new ModelAndView("home");
	}

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@PostMapping("/add-event")
	public String addEvent(HttpServletRequest req, HttpServletResponse response) throws Exception {

		eventService.addEvent(req);
		response.sendRedirect("/get-event");
		return null;
	}

	@GetMapping("/get-event")
	public String getEvent(Model model) {
		model.addAttribute("events", eventService.getEvents());
		return "get-event";
	}

	@GetMapping("/delete-event/{id}")
	public String deleteEvent(@PathVariable Long id, HttpServletResponse response) throws Exception {
		eventService.deleteEvent(id);
		response.sendRedirect("/get-event");
		return null;
	}

}
