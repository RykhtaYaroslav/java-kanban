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
        // увеличиваем количество тренировок у тренера:
        trainingSession.getCoach().setTrainsCount(trainingSession.getCoach().getTrainsCount()+1);
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

    // для сравнения коучей по количеству тренировок
    Comparator<Coach> trainComparator = new Comparator<Coach>() {
        @Override
        public int compare(Coach c1, Coach c2) {
            int diff = Integer.compare(c2.getTrainsCount(), c1.getTrainsCount());
            if (diff != 0) return diff;

            diff = c1.getSurname().compareTo(c2.getSurname());
            if (diff != 0) return diff;

            diff = c1.getName().compareTo(c2.getName());
            if (diff != 0) return diff;

            return c1.getMiddleName().compareTo(c2.getMiddleName());
        }
    };

    /* Пройтись по каждому дню и достать мапу дэйлиТрэйнМап
    пройтись по каждому времени и достать из дэйлиТрэйнМап список тренировок.
    Пройтись по списку тренировок и достать тренера, складывая его в триСет

     */

    public void getCountByCoaches() {
        Set<Coach> coachSet = new TreeSet<>(trainComparator); // сет тренеров с сортировкой по тренировкам
        for (var day : timetable.keySet()) { // получил каждый день поочереди
            Map<TimeOfDay, List<TrainingSession>> dayliTraisMap= timetable.get(day);
            for (TimeOfDay time : dayliTraisMap.keySet()) { // получил каждое время по очереди
                List<TrainingSession> trainsAtDayAndTime = dayliTraisMap.get(time);
                for (TrainingSession trainingSession : trainsAtDayAndTime) {
                    coachSet.add(trainingSession.getCoach()); // положил тренера в сет. Если он там есть уже- ничего не будет
                }
            }
        }
        System.out.println("Список тренеров (в порядке уменьшения количества тренировок)");
        for (Coach c: coachSet) {
            System.out.println(c);
        }
    }
}