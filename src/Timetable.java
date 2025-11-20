import java.util.*;

public class Timetable {

    private static final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //достаём день и время тренировки
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        if (!timetable.containsKey(day)) {
            timetable.put(day, new TreeMap<TimeOfDay, List<TrainingSession>>());
        }
        // по дню достаём мапу с тренировками этого дня
        TreeMap<TimeOfDay, List<TrainingSession>> trainSessionsAtDay = timetable.get(day);
        // Из мапы достаём список тренировок в конкретное время
        if (!trainSessionsAtDay.containsKey(time)) {
            trainSessionsAtDay.put(time, new ArrayList<TrainingSession>());
        }
        List<TrainingSession> trainSessionsAtTime = trainSessionsAtDay.get(time);
        // записываем тренировку во все коллекции:
        trainSessionsAtTime.add(trainingSession);
        /*trainSessionsAtDay.put(time, trainSessionsAtTime);
        timetable.put(day, trainSessionsAtDay);*/
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
}