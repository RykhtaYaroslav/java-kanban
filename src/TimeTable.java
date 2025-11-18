import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();
    Map<TimeOfDay, TrainingSession> dayTrainingSessionMap = new TreeMap<>();
    timetable.


    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
    }

    public /* непонятно, что возвращать */ getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public /* непонятно, что возвращать */ getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public Map<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> getTimetable() {
        return timetable;
    }

    public void setTimetable(Map<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable) {
        this.timetable = timetable;
    }
}