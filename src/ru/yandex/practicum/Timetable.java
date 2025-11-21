package ru.yandex.practicum;

import java.util.*;

public class Timetable {
    private static final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public Timetable() {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //достаём день и время тренировки
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        // по дню достаём мапу с тренировками этого дня
        TreeMap<TimeOfDay, List<TrainingSession>> trainSessionsAtDay = timetable.get(dayOfWeek);

        // Из мапы достаём список тренировок в конкретное время
        if (!trainSessionsAtDay.containsKey(timeOfDay)) {
            trainSessionsAtDay.put(timeOfDay, new ArrayList<TrainingSession>());
        }

        List<TrainingSession> trainSessionsAtTime = trainSessionsAtDay.get(timeOfDay);

        // записываем тренировку во все коллекции:
        trainSessionsAtTime.add(trainingSession);
        // увеличиваем количество тренировок у тренера:
        trainingSession.getCoach().setTrainSessionsCount(trainingSession.getCoach().getTrainSessionsCount() + 1);
        // если создавать метод по удалению тренировки, то тамм так же, но будет минус 1
    }

    // Получение всех тренировок, упорядоченных по времени начала, за конкретный день недели.
    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        // достаём из внешней мапы - внутреннюю по дню недели, достаём из дневной мапы список тренировок по времени
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> getTimetable() {
        return timetable;
    }

    // для сравнения коучей по количеству тренировок в порядке уменьшения тренировок
    Comparator<Coach> trainComparator = new Comparator<Coach>() {
        @Override
        public int compare(Coach c1, Coach c2) {
            int diff = Integer.compare(c2.getTrainSessionsCount(), c1.getTrainSessionsCount());
            if (diff != 0) return diff;

            diff = c1.getSurname().compareTo(c2.getSurname());
            if (diff != 0) return diff;

            diff = c1.getName().compareTo(c2.getName());
            if (diff != 0) return diff;

            return c1.getMiddleName().compareTo(c2.getMiddleName());
        }
    };

    public Set<Coach> getCountByCoaches() {
        Set<Coach> coachSet = new TreeSet<>(trainComparator); // сет тренеров с сортировкой по тренировкам
        for (var day : timetable.keySet()) { // получил каждый день поочереди
            TreeMap<TimeOfDay, List<TrainingSession>> dailyTrainsMap = timetable.get(day);
            for (TimeOfDay time : dailyTrainsMap.navigableKeySet()) { // получил каждое время по очереди
                List<TrainingSession> trainsAtDayAndTime = dailyTrainsMap.get(time);
                for (TrainingSession trainingSession : trainsAtDayAndTime) {
                    coachSet.add(trainingSession.getCoach()); // Положил тренера в сет. Если он там есть уже - ничего не будет
                }
            }
        }
        return coachSet;
    }
}