package ru.yandex.practicum;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        // Про метод Мэйн в ТЗ ничего не было, поэтому добавил что-то от себя просто для проверок работоспособности
        // Но тесты всё равно пригодились, в процессе написания тестов нашёл (незначительный?) баг, поправил

        Timetable timetable = new Timetable();

        autoFillTimetableByChatGPT(timetable);
        printTimetable(timetable);

        // Получение всех тренировок, упорядоченных по времени начала, за конкретный день недели, например, понедельник
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsAtDay = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        for (TimeOfDay time : trainingSessionsAtDay.navigableKeySet()) {
            for (TrainingSession trainingSession : trainingSessionsAtDay.get(time)) {
                System.out.println(trainingSession);
            }
        }

        //Получение всех тренировок, начинающихся в конкретное время, за конкретный день недели, например, среда, 18:00
        int h = 18;
        int m = 0;

        List<TrainingSession> trainingSessionsAtDayAndTime = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.WEDNESDAY,
                new TimeOfDay(h, m));

        System.out.printf("\n\nТренировки в %s, начинающиеся в %02d:%02d:\n", DayOfWeek.WEDNESDAY, h, m);
        for (TrainingSession ts : trainingSessionsAtDayAndTime) {
            System.out.println(ts + "\n");
        }

        // Выводит список всех тренеров в порядке уменьшения количества тренировок в неделю
        Set<Coach> coachSet = timetable.getCountByCoaches();

        System.out.println("Список тренеров (в порядке уменьшения количества тренировок)");
        for (Coach c : coachSet) {
            System.out.println(c);
        }
    }

    static void autoFillTimetableByChatGPT(Timetable timetable) {
        /* В ТЗ нет задачи сделать консольный интерфейс. Я попросил чат ГПТ сделать заполнение расписание (Мне лень
        было придумывать тренировки и т.п.) вот с таким запросом:
        Напиши код для мэйн класса. Нужно создать нужные объекты и заполнить расписание. Пусть будет по 5-7 тренировок
        в день. Пусть минимум в 4-х днях будет по несколько тренировок в одно и то же время. 10 разных тренеров, 14
        разновидностей тренировок. Сделай расписания без рандома. Просто заполни расписание так, чтобы не было
        конфликтов тренеров
         */

        // ----------- СОЗДАЁМ ТРЕНЕРОВ -----------
        List<Coach> coaches = List.of(
                new Coach("Иванов", "Павел", "Игоревич"),     // 0
                new Coach("Сидоров", "Михаил", "Антонович"),   // 1
                new Coach("Кузнецов", "Егор", "Петрович"),     // 2
                new Coach("Смирнов", "Николай", "Витальевич"), // 3
                new Coach("Попов", "Андрей", "Дмитриевич"),    // 4
                new Coach("Соколов", "Максим", "Олегович"),    // 5
                new Coach("Орлов", "Денис", "Алексеевич"),     // 6
                new Coach("Волков", "Артур", "Сергеевич"),     // 7
                new Coach("Зайцев", "Глеб", "Романович"),      // 8
                new Coach("Фёдоров", "Кирилл", "Степанович")   // 9
        );

        // ----------- СОЗДАЁМ ГРУППЫ -----------
        List<Group> groups = List.of(
                new Group("Бокс — начинающие", Age.ADULT, 60),    // 0
                new Group("Бокс — дети", Age.CHILD, 45),          // 1
                new Group("MMA — взрослые", Age.ADULT, 90),       // 2
                new Group("MMA — подростки", Age.CHILD, 60),      // 3
                new Group("Йога — взрослые", Age.ADULT, 60),      // 4
                new Group("Йога — дети", Age.CHILD, 40),          // 5
                new Group("Кикбоксинг", Age.ADULT, 75),           // 6
                new Group("Гимнастика — дети", Age.CHILD, 50),    // 7
                new Group("Растяжка", Age.ADULT, 60),             // 8
                new Group("Кроссфит — взрослые", Age.ADULT, 70),  // 9
                new Group("Кроссфит — дети", Age.CHILD, 50),      // 10
                new Group("Тхэквондо", Age.CHILD, 55),            // 11
                new Group("Пилатес", Age.ADULT, 60),              // 12
                new Group("ОФП — подростки", Age.CHILD, 60)       // 13
        );

        // ----------- ФИКСИРОВАННЫЕ ВРЕМЕНА -----------
        TimeOfDay t0900 = new TimeOfDay(9, 0);
        TimeOfDay t1100 = new TimeOfDay(11, 0);
        TimeOfDay t1400 = new TimeOfDay(14, 0);
        TimeOfDay t1600 = new TimeOfDay(16, 0);
        TimeOfDay t1800 = new TimeOfDay(18, 0);
        TimeOfDay t1930 = new TimeOfDay(19, 30);

        // ----------- РАСПИСАНИЕ БЕЗ КОНФЛИКТОВ -----------
        // Каждый день 5–7 тренировок
        // 4 дня содержат по 2 тренировки в одно время (14:00 и 18:00)

        // ===== MONDAY =====
        add(timetable, groups.get(0), coaches.get(0), DayOfWeek.MONDAY, t0900);
        add(timetable, groups.get(1), coaches.get(1), DayOfWeek.MONDAY, t1100);
        add(timetable, groups.get(2), coaches.get(2), DayOfWeek.MONDAY, t1400);
        add(timetable, groups.get(3), coaches.get(3), DayOfWeek.MONDAY, t1400); // дубль времени
        add(timetable, groups.get(4), coaches.get(4), DayOfWeek.MONDAY, t1800);
        add(timetable, groups.get(5), coaches.get(5), DayOfWeek.MONDAY, t1930);

        // ===== TUESDAY =====
        add(timetable, groups.get(6), coaches.get(6), DayOfWeek.TUESDAY, t0900);
        add(timetable, groups.get(7), coaches.get(7), DayOfWeek.TUESDAY, t1100);
        add(timetable, groups.get(8), coaches.get(8), DayOfWeek.TUESDAY, t1400);
        add(timetable, groups.get(9), coaches.get(0), DayOfWeek.TUESDAY, t1600);
        add(timetable, groups.get(10), coaches.get(0), DayOfWeek.TUESDAY, t1800);
        add(timetable, groups.get(11), coaches.get(1), DayOfWeek.TUESDAY, t1800); // дубль времени

        // ===== WEDNESDAY =====
        add(timetable, groups.get(12), coaches.get(2), DayOfWeek.WEDNESDAY, t0900);
        add(timetable, groups.get(13), coaches.get(3), DayOfWeek.WEDNESDAY, t1100);
        add(timetable, groups.get(0), coaches.get(4), DayOfWeek.WEDNESDAY, t1400);
        add(timetable, groups.get(1), coaches.get(5), DayOfWeek.WEDNESDAY, t1600);
        add(timetable, groups.get(2), coaches.get(6), DayOfWeek.WEDNESDAY, t1800);
        add(timetable, groups.get(3), coaches.get(7), DayOfWeek.WEDNESDAY, t1800); // дубль времени

        // ===== THURSDAY =====
        add(timetable, groups.get(4), coaches.get(1), DayOfWeek.THURSDAY, t0900);
        add(timetable, groups.get(5), coaches.get(0), DayOfWeek.THURSDAY, t1100);
        add(timetable, groups.get(6), coaches.get(0), DayOfWeek.THURSDAY, t1400);
        add(timetable, groups.get(7), coaches.get(1), DayOfWeek.THURSDAY, t1400); // дубль времени
        add(timetable, groups.get(8), coaches.get(2), DayOfWeek.THURSDAY, t1600);
        add(timetable, groups.get(9), coaches.get(3), DayOfWeek.THURSDAY, t1930);

        // ===== FRIDAY =====
        add(timetable, groups.get(10), coaches.get(4), DayOfWeek.FRIDAY, t0900);
        add(timetable, groups.get(11), coaches.get(5), DayOfWeek.FRIDAY, t1100);
        add(timetable, groups.get(12), coaches.get(6), DayOfWeek.FRIDAY, t1400);
        add(timetable, groups.get(13), coaches.get(7), DayOfWeek.FRIDAY, t1600);
        add(timetable, groups.get(0), coaches.get(8), DayOfWeek.FRIDAY, t1800);
        add(timetable, groups.get(1), coaches.get(9), DayOfWeek.FRIDAY, t1930);

        // ===== SATURDAY =====
        add(timetable, groups.get(2), coaches.get(0), DayOfWeek.SATURDAY, t0900);
        add(timetable, groups.get(3), coaches.get(1), DayOfWeek.SATURDAY, t1100);
        add(timetable, groups.get(4), coaches.get(2), DayOfWeek.SATURDAY, t1400);
        add(timetable, groups.get(5), coaches.get(3), DayOfWeek.SATURDAY, t1800);
        add(timetable, groups.get(6), coaches.get(4), DayOfWeek.SATURDAY, t1800); // дубль времени
        add(timetable, groups.get(7), coaches.get(5), DayOfWeek.SATURDAY, t1930);

        // ===== SUNDAY =====
        add(timetable, groups.get(8), coaches.get(6), DayOfWeek.SUNDAY, t0900);
        add(timetable, groups.get(9), coaches.get(7), DayOfWeek.SUNDAY, t1100);
        add(timetable, groups.get(10), coaches.get(8), DayOfWeek.SUNDAY, t1400);
        add(timetable, groups.get(11), coaches.get(9), DayOfWeek.SUNDAY, t1600);
        add(timetable, groups.get(12), coaches.get(0), DayOfWeek.SUNDAY, t1800);
        add(timetable, groups.get(13), coaches.get(1), DayOfWeek.SUNDAY, t1930);
    }

    static void printTimetable(Timetable timetable) {
        for (var entry : timetable.getTimetable().entrySet()) {
            System.out.println("----------------------------------------------");
            for (var time : entry.getValue().entrySet()) {

                for (TrainingSession ts : time.getValue()) {
                    System.out.println(ts);
                }
            }
        }
    }


    private static void add(Timetable t, Group g, Coach c, DayOfWeek d, TimeOfDay time) {
        t.addNewTrainingSession(new TrainingSession(g, c, d, time));
    }
}

