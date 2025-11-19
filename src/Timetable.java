import java.util.*;

public class Timetable {

    private static final Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //достаём день и время тренировки
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        // по дню достаём мапу с тренировками этого дня (если тренировок ещё нет, создаём новую мапу):
        Map<TimeOfDay, List<TrainingSession>> trainSessionsAtDay = timetable.getOrDefault(day, new TreeMap<>());
        // Из мапы достаём список тренировок в конкретное время. Если мапа пустая или в это время нет тренировок - делаем новый список:
        List<TrainingSession> trainSessionsAtTime = trainSessionsAtDay.getOrDefault(time, new ArrayList<>());
        // записываем тренировку во все коллекции:
        trainSessionsAtTime.add(trainingSession);
        trainSessionsAtDay.put(time, trainSessionsAtTime);
        timetable.put(day, trainSessionsAtDay);
    }

    // Получение всех тренировок, упорядоченных по времени начала, за конкретный день недели.
    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        // достаём из внешней мапы - внутреннюю по дню недели, достаём из дневной мапы список тренировок по времени
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> getTimetable() {
        return timetable;
    }
}