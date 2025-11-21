package ru.yandex.practicum;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



public class TimetableTest {
    // Покрытие тестами класса Timetable 100% по методам и по строкам

    @Test // Тест номер 1.
    // Не понимаю смысла этого теста. Этот же функционал тестирует тест 2, но первые три теста - из ТЗ скопированы
    public void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assert.assertEquals(1, countTrainsPerDay(timetable, DayOfWeek.MONDAY)); //Проверить, что за понедельник вернулось одно занятие
        Assert.assertEquals(0, countTrainsPerDay(timetable, DayOfWeek.TUESDAY)); //Проверить, что за вторник не вернулось занятий
    }

    @Test // Тест номер 2.
    public void testGetTrainingSessionsForDayMultipleSessions() {
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
        Assert.assertEquals(1, countTrainsPerDay(timetable, DayOfWeek.MONDAY));
        // Проверить, что за вторник не вернулось занятий
        Assert.assertEquals(0, countTrainsPerDay(timetable, DayOfWeek.TUESDAY));
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assert.assertEquals(2, countTrainsPerDay(timetable, DayOfWeek.THURSDAY));

        List<TimeOfDay> thursdayTrains = new ArrayList<>(timetable.getTimetable().get(DayOfWeek.THURSDAY).keySet());
        Assert.assertEquals(new TimeOfDay(13, 0), thursdayTrains.getFirst());
        Assert.assertEquals(new TimeOfDay(20, 0), thursdayTrains.getLast());


    }

    @Test // Тест номер 3
    //Не понимаю смысла этого теста. Этот же функционал тестирует тест 2, но первые три теста - из ТЗ скопированы
    public void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assert.assertEquals(1, countTrainsPerDay(timetable, DayOfWeek.MONDAY));

        List<TimeOfDay> mondayTrains = new ArrayList<>(timetable.getTimetable().get(DayOfWeek.MONDAY).keySet());
        Assert.assertEquals(new TimeOfDay(13, 0), mondayTrains.getFirst());
        Assert.assertNotEquals(new TimeOfDay(14, 0), mondayTrains.getFirst());

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    public void shouldReturnCorrectDailyTrainMapFromInternalTimetableMethod() {
        Timetable timetable = new Timetable();

        Group group = new Group("<Бокс>", Age.CHILD, 60);
        Coach coach = new Coach("Иванов", "Иван", "Иваныч");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Map<TimeOfDay, List<TrainingSession>> testMap = new TreeMap<>();

        List<TrainingSession> testList = new ArrayList<>(List.of(singleTrainingSession));

        testMap.put(new TimeOfDay(13, 0), testList);
        Assert.assertEquals(testMap, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
    }

    @Test
    public void shouldReturnCorrectListForDayAndTimeFromInternalTimetableMethod() {
        Timetable timetable = new Timetable();

        TimeOfDay timeOfDay = new TimeOfDay(13, 0);

        Group group = new Group("<Бокс>", Age.CHILD, 60);
        Coach coach = new Coach("Иванов", "Иван", "Иваныч");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, timeOfDay);

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> testList = new ArrayList<>(List.of(singleTrainingSession));

        Assert.assertEquals(testList, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, timeOfDay));
    }

    @Test
    public void shouldReturnThreeTrainsAtSameDayAndSameTime() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("<Бокс>", Age.CHILD, 60);
        Coach coach1 = new Coach("Иванов", "Иван", "Иваныч");
        TrainingSession firstTS = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstTS);

        Group group2 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach2 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession secondTS = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(secondTS);

        Coach coach = new Coach("Петров", "Пётр", "Петрович");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Assert.assertEquals(3, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());
    }

    @Test
    public void shouldReturnCorrectTrainSessionsCount() {
        Timetable timetable = new Timetable();

        autoFillTimetableByChatGPT(timetable);

        // Достаю трёх созданных тренеров и у каждого проверяю сколько тренировок за на
        Coach testCoachA = timetable.getTimetable().get(DayOfWeek.MONDAY).get(new TimeOfDay(9,0)).getFirst().getCoach();
        Coach testCoachB = timetable.getTimetable().get(DayOfWeek.MONDAY).get(new TimeOfDay(14,0)).getFirst().getCoach();
        Coach testCoachC = timetable.getTimetable().get(DayOfWeek.MONDAY).get(new TimeOfDay(14,0)).getLast().getCoach();

        Assert.assertEquals(5, testCoachA.getTrainSessionsCount());
        Assert.assertEquals(4, testCoachB.getTrainSessionsCount());
        Assert.assertEquals(3, testCoachC.getTrainSessionsCount());
    }

    @Test
    public void shouldReturnCorrectCoachSetArrangedWithTrainSessionsCount() {
        Timetable timetable = new Timetable();

        autoFillTimetableByChatGPT(timetable);

        Coach testCoachA = new Coach("Иванов", "Алексей", "Петрович");
        Coach testCoachB = new Coach("Смирнов", "Борис", "Игоревич");
        Coach testCoachC = new Coach("Кузнецов", "Владимир", "Сергеевич");

        testCoachA.setTrainSessionsCount(5);
        testCoachA.setTrainSessionsCount(4);
        testCoachA.setTrainSessionsCount(3);

        // Проверяем, что порядок вывода тренеров в соответствии с количеством тренировок работает верно
        var coachSetList = new ArrayList<>(timetable.getCountByCoaches());

        Assert.assertEquals(testCoachA, coachSetList.getFirst());
        Assert.assertEquals(testCoachC, coachSetList.getLast());
        Assert.assertEquals(testCoachB, coachSetList.get(1));

    }

    // Вспомогательные методы
    public void autoFillTimetableByChatGPT(Timetable timetable) {
        /* Я попросил чат ГПТ создать и заполнить реалистичное расписание, потому что выдумывать самому было лень.
        Из запросов было три разных тренера у каждого по 5, 4, 3 тренировки в неделю соответственно.
        Попросил, чтобы было наложение по времени, но у разных тренеров (он назвал наложения параллелями)
         */

        // ----------- ТРЕНЕРЫ -----------
        Coach coachA = new Coach("Иванов", "Алексей", "Петрович");      // 5 тренировок
        Coach coachB = new Coach("Смирнов", "Борис", "Игоревич");       // 4 тренировки
        Coach coachC = new Coach("Кузнецов", "Владимир", "Сергеевич");  // 3 тренировки

        // ----------- ГРУППЫ -----------
        Group g0 = new Group("Бокс — дети", Age.CHILD, 45);
        Group g1 = new Group("Бокс — взрослые", Age.ADULT, 60);
        Group g2 = new Group("Кикбоксинг", Age.ADULT, 70);
        Group g3 = new Group("Гимнастика — дети", Age.CHILD, 50);
        Group g4 = new Group("Йога", Age.ADULT, 60);
        Group g5 = new Group("Кроссфит", Age.ADULT, 70);
        Group g6 = new Group("MMA", Age.ADULT, 90);
        Group g7 = new Group("Пилатес", Age.ADULT, 60);
        Group g8 = new Group("ОФП — подростки", Age.CHILD, 60);

        // ----------- ФИКСИРОВАННОЕ ВРЕМЯ -----------
        TimeOfDay t0900 = new TimeOfDay(9, 0);
        TimeOfDay t1100 = new TimeOfDay(11, 0);
        TimeOfDay t1400 = new TimeOfDay(14, 0);
        TimeOfDay t1600 = new TimeOfDay(16, 0);
        TimeOfDay t1800 = new TimeOfDay(18, 0);

        // ======================== РАСПИСАНИЕ ========================

        // ===== MONDAY =====
        // Параллельные тренировки в 14:00 (два тренера)
        add(timetable, g0, coachA, DayOfWeek.MONDAY, t0900);
        add(timetable, g1, coachB, DayOfWeek.MONDAY, t1400); // параллель 1
        add(timetable, g2, coachC, DayOfWeek.MONDAY, t1400); // параллель 1
        add(timetable, g3, coachA, DayOfWeek.MONDAY, t1800);

        // ===== TUESDAY =====
        // Параллель в 16:00
        add(timetable, g4, coachB, DayOfWeek.TUESDAY, t1100);
        add(timetable, g5, coachA, DayOfWeek.TUESDAY, t1600); // параллель 2
        add(timetable, g6, coachC, DayOfWeek.TUESDAY, t1600); // параллель 2

        // ===== THURSDAY =====
        // Параллель в 18:00
        add(timetable, g7, coachA, DayOfWeek.THURSDAY, t0900);
        add(timetable, g8, coachB, DayOfWeek.THURSDAY, t1800); // параллель 3
        add(timetable, g1, coachC, DayOfWeek.THURSDAY, t1800); // параллель 3

        // ===== SATURDAY =====
        // Параллель в 11:00
        add(timetable, g2, coachA, DayOfWeek.SATURDAY, t1100); // параллель 4
        add(timetable, g3, coachB, DayOfWeek.SATURDAY, t1100); // параллель 4
    }

    public void add(Timetable t, Group g, Coach c, DayOfWeek d, TimeOfDay time) {
        t.addNewTrainingSession(new TrainingSession(g, c, d, time));
    }

    public int countTrainsPerDay(Timetable timetable, DayOfWeek dayOfWeek) {
        int trainsPerDay = 0;
        // Достаём дневную мапу:
        Map<TimeOfDay, List<TrainingSession>> dailyTrains = timetable.getTimetable().get(dayOfWeek);
        // Итерируемся по всем ключам-значениям и в значениях достаём списки тренировок в указанное время
        for (var entry : dailyTrains.entrySet()) {
            trainsPerDay += entry.getValue().size();
        }
        return trainsPerDay;
    }
}
