package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable =
            new HashMap<>();



    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> timeTableForDay = timetable.get(day);

        if (timeTableForDay == null) {
            timeTableForDay = new TreeMap<>();
            timetable.put(day, timeTableForDay);
        }

        List<TrainingSession> trainingSessionsAtTime = timeTableForDay.get(time);

        if (trainingSessionsAtTime == null) {
            trainingSessionsAtTime = new ArrayList<>();
            timeTableForDay.put(time, trainingSessionsAtTime);
        }

        trainingSessionsAtTime.add(trainingSession);

    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)

        TreeMap<TimeOfDay, List<TrainingSession>> timeTableForDay = timetable.get(dayOfWeek);
        if (timeTableForDay == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> result = new ArrayList<>();

        for (List<TrainingSession> list : timeTableForDay.values()) {
            result.addAll(list);
        }

        return result;

    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)

        TreeMap<TimeOfDay, List<TrainingSession>> timeTableForDay = timetable.get(dayOfWeek);

        if (timeTableForDay == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> list = timeTableForDay.get(timeOfDay);

        if (list == null) {
            return new ArrayList<>();
        }

        return list;
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachCountMap = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> dayMap : timetable.values()) {
            for (List<TrainingSession> sessions : dayMap.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    coachCountMap.put(coach, coachCountMap.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCountMap.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        result.sort(new CounterOfTrainingsComparator());

        return result;
    }


}
