import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Timetable {

    private static final Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    Map<TimeOfDay, List<TrainingSession>> dayTrainingSessionMap = new TreeMap<>();



    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        timetable.put(DayOfWeek.MONDAY, dayTrainingSessionMap);
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public /* непонятно, что возвращать */ getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> getTimetable() {
        return timetable;
    }
}