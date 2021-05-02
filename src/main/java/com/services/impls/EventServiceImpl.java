package com.services.impls;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Event;
import com.enums.DaysOfWeek;
import com.repository.EventRepository;
import com.services.EventService;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;

	@Transactional
	public void addEvent(HttpServletRequest req) throws Exception {

		LocalDate startDate = LocalDate.parse(req.getParameter("startDate"));
		LocalDate endDate = LocalDate.parse(req.getParameter("endDate"));
		Long period = ChronoUnit.DAYS.between(startDate, endDate);
		if (period < 1 || period > 30) {
			throw new Exception("Wrong period of days");
		}

		LocalTime startTime = LocalTime.parse(req.getParameter("startTime"));
		LocalTime endTime = LocalTime.parse(req.getParameter("endTime"));
		Long duration = Duration.between(startTime, endTime).toMinutes();
		if (duration < 30 || duration > 300) {
			throw new Exception("Wrong duration time");
		}

		List<String> list = new ArrayList<String>();
		list.add(req.getParameter("WeekEnd"));
		list.add(req.getParameter("WeekDays"));
		list.removeAll(Collections.singleton(null));
		String dow = StringUtils.join(list, ",");
		
		List<String> listOfDow = Stream.of(dow.split(",")).collect(Collectors.toList());
		
		List<LocalDate> listOfDates = Stream.iterate(startDate, date -> date.plusDays(1))
				.limit(ChronoUnit.DAYS.between(startDate, endDate)).collect(Collectors.toList());
		
		if (listOfDow.size() == 2) {
			
			int binaryDow = 0;
			// Find binary for DOW
			for (LocalDate localDate : listOfDates) {
				binaryDow = binaryDow + DaysOfWeek.fromDayValue(localDate.getDayOfWeek().toString());
			}

			Event event = new Event();
			event.setEventName(req.getParameter("eventName"));
			event.setStartDate(startDate);
			event.setEndDate(endDate);
			event.setStartTime(startTime);
			event.setEndTime(endTime);
			event.setDayOfWeek("M,T,W,T,F,S,S");
			event.setBinaryDOW(binaryDow);
			eventRepository.save(event);
			
		} else if (listOfDow.size() == 1) {

			if (list.contains("WeekDays")) {

				int perioddata = Period.between(startDate, endDate).getDays();
				int noOfWeeks = perioddata % 7 == 0 ? perioddata / 7 : (perioddata / 7) + 1;
				List<List<LocalDate>> datelist = new ArrayList<>();
				LocalDate date1 = startDate;
				LocalDate date2 = endDate;
				for (int i = 1; i <= noOfWeeks; i++) {

					List<LocalDate> dates = new ArrayList<>();
					switch (date1.getDayOfWeek()) {

					case MONDAY:
						date2 = date1.plusDays(4);
						break;

					case TUESDAY:
						date2 = date1.plusDays(3);
						break;

					case WEDNESDAY:
						date2 = date1.plusDays(2);
						break;

					case THURSDAY:
						date2 = date1.plusDays(1);
						break;

					case FRIDAY:
						date2 = date1;
						break;

					case SATURDAY:
						date1 = date1.plusDays(2);
						continue;

					case SUNDAY:
						date1 = date1.plusDays(1);
						continue;

					default:
						break;
					}
					if (!date2.isBefore(endDate)) {
						date2 = endDate;
					}
					dates.add(date1);
					dates.add(date2);
					date1 = date2.plusDays(3);
					datelist.add(dates);
				}

				for (int i = 0; i < datelist.size(); i++) {
					
					LocalDate sDate = datelist.get(i).get(0);
					LocalDate eDate = datelist.get(i).get(1);
					
					// Find binary for DOW
					LocalDate testDate = sDate;
					int binaryDow = 0;
					List<String> dowList = new ArrayList<String>();
					while (testDate.isBefore(eDate)) {
						
						binaryDow = binaryDow + DaysOfWeek.fromDayValue(testDate.getDayOfWeek().toString());
						dowList.add(Character.toString(testDate.getDayOfWeek().toString().charAt(0)));
					}
					dowList.removeAll(Collections.singleton(null));
					String dowdata = StringUtils.join(dowList, ",");

					Event event2 = new Event();
					event2.setEventName(req.getParameter("eventName"));
					event2.setStartDate(sDate);
					event2.setEndDate(eDate);
					event2.setStartTime(startTime);
					event2.setEndTime(endTime);
					event2.setDayOfWeek(dowdata);
					event2.setBinaryDOW(binaryDow);
					eventRepository.save(event2);
				}

			} else if (list.contains("WeekEnd")) {

				List<List<LocalDate>> lists = new ArrayList<>();
				listOfDates.forEach(date -> {
					List<LocalDate> dates = new ArrayList<>();
					if (date.getDayOfWeek().toString().equalsIgnoreCase("SATURDAY")) {

						dates.add(date);
						if (date.plusDays(1).isBefore(endDate)) {
							dates.add(date.plusDays(1));
						}
					} else if (date.getDayOfWeek().toString().equalsIgnoreCase("SUNDAY")
							&& date.isEqual(startDate)) {
						dates.add(date);
					}
					if (!dates.isEmpty()) {
						lists.add(dates);
					}
				});

				for (int i = 0; i < lists.size(); i++) {
										
					Event event2 = new Event();
					event2.setEventName(req.getParameter("eventName"));
					event2.setStartDate(lists.get(i).get(0));
					event2.setEndDate(lists.get(i).get(1));
					event2.setStartTime(startTime);
					event2.setEndTime(endTime);
					event2.setDayOfWeek("S S");
					event2.setBinaryDOW(130);
					eventRepository.save(event2);
				}
			}			
		}
	}

	@Override
	public List<Event> getEvents() {

		return eventRepository.findAll();
	}

	@Transactional
	public void deleteEvent(Long id) throws Exception {
		Optional<Event> event = eventRepository.findById(id);
		if (event.isPresent()) {
			eventRepository.delete(event.get());
		}
	}

}
