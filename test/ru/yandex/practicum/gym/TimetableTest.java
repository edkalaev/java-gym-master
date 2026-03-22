package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertEquals(singleTrainingSession, mondaySessions.getFirst());

        //Проверить, что за вторник не вернулось занятий

        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());


    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertEquals(mondayChildTrainingSession, mondaySessions.getFirst());


        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        Assertions.assertEquals(2, thursdaySessions.size());

        Assertions.assertEquals(thursdayChildTrainingSession, thursdaySessions.get(0));
        Assertions.assertEquals(thursdayAdultTrainingSession, thursdaySessions.get(1));


        // Проверить, что за вторник не вернулось занятий

        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие

        List<TrainingSession> mondaySession13 = timetable.
                getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));

            Assertions.assertEquals(1, mondaySession13.size());
        Assertions.assertEquals(singleTrainingSession, mondaySession13.getFirst());

        //Проверить, что за понедельник в 14:00 не вернулось занятий

        List<TrainingSession> mondaySession14 = timetable.
                getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)
        );

        Assertions.assertTrue(mondaySession14.isEmpty());
    }

    @Test
    void testAddTwoSessionsSameDaySameTime() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Иванов", "Петр", "Сергеевич");

        Group group1 = new Group("Группа 1", Age.ADULT, 60);
        Group group2 = new Group("Группа 2", Age.CHILD, 45);

        TrainingSession session1 = new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group2, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        Assertions.assertEquals(2, sessions.size());
    }

    @Test
    void testEmptyTimetable() {
        Timetable timetable = new Timetable();

        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.SUNDAY).isEmpty());
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY).isEmpty());

        Assertions.assertTrue(
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(10, 0))
                        .isEmpty()
        );
    }

    @Test
    void testSessionsAreSortedWithinDay() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Сидоров", "Иван", "Иванович");
        Group group = new Group("Тест", Age.ADULT, 60);

        TrainingSession s12 = new TrainingSession(group, coach, DayOfWeek.FRIDAY, new TimeOfDay(12, 0));
        TrainingSession s09 = new TrainingSession(group, coach, DayOfWeek.FRIDAY, new TimeOfDay(9, 0));
        TrainingSession s18 = new TrainingSession(group, coach, DayOfWeek.FRIDAY, new TimeOfDay(18, 0));

        timetable.addNewTrainingSession(s12);
        timetable.addNewTrainingSession(s18);
        timetable.addNewTrainingSession(s09);

        List<TrainingSession> fridaySession = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);

        Assertions.assertEquals(List.of(s09, s12, s18), fridaySession);
    }

    @Test
    void testSingleCoachCount() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.CHILD, 60);
        Coach coach = new Coach("Иванов", "Иван", "Иванович");

        TrainingSession s1 = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession s2 = new TrainingSession(group, coach, DayOfWeek.TUESDAY, new TimeOfDay(11, 0));
        TrainingSession s3 = new TrainingSession(group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(s1);
        timetable.addNewTrainingSession(s2);
        timetable.addNewTrainingSession(s3);

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(3, result.getFirst().getCount());
        Assertions.assertEquals(coach, result.getFirst().getCoach());
    }

    @Test
    void testTwoCoachesSorted() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");
        Group group = new Group("Акробатика", Age.CHILD, 60);


        TrainingSession s1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession s2 = new TrainingSession(group, coach1,
                DayOfWeek.TUESDAY, new TimeOfDay(11, 0));
        TrainingSession s3 = new TrainingSession(group, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0));


        timetable.addNewTrainingSession(s1);
        timetable.addNewTrainingSession(s2);
        timetable.addNewTrainingSession(s3);


        TrainingSession s4 = new TrainingSession(group, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0));
        timetable.addNewTrainingSession(s4);


        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(coach1, result.get(0).getCoach());
        Assertions.assertEquals(3, result.get(0).getCount());

        Assertions.assertEquals(coach2, result.get(1).getCoach());
        Assertions.assertEquals(1, result.get(1).getCount());
    }

    @Test
    void testEmptyCounterOfTrainings() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertTrue(result.isEmpty());
    }

}
