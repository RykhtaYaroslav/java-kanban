public class Main {

    public static void main(String[] args) {
        Group group1 = new Group("Скалодром", Age.ADULT, 60);
        Group group2 = new Group("Пауэрлифтинг", Age.ADULT, 60);
        Group group3 = new Group("Детский фитнес", Age.CHILD, 45);
        Group group4 = new Group("Плаванье", Age.CHILD, 45);
        // в ТЗ Ничего не говорилось о консольном интерфейе, поэтому я сам сделал разные тренировки

        Coach coach1 = new Coach("Иванов", "Иван", "Иваныч");
        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");
        Coach coach3 = new Coach("Александрова", "Алеквандра", "Александровна");

        TrainingSession trainingSession1 = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 30));
        TrainingSession trainingSession2 = new TrainingSession(group1, coach2, DayOfWeek.FRIDAY, new TimeOfDay(14, 0));
        TrainingSession trainingSession3 = new TrainingSession(group2, coach2, DayOfWeek.SUNDAY, new TimeOfDay(10, 30));
        TrainingSession trainingSession4 = new TrainingSession(group2, coach2, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0));
        TrainingSession trainingSession5 = new TrainingSession(group3, coach3, DayOfWeek.TUESDAY, new TimeOfDay(18, 0));
        TrainingSession trainingSession6 = new TrainingSession(group3, coach3, DayOfWeek.THURSDAY, new TimeOfDay(19, 45));
        TrainingSession trainingSession7 = new TrainingSession(group4, coach3, DayOfWeek.MONDAY, new TimeOfDay(20, 0));
        TrainingSession trainingSession8 = new TrainingSession(group4, coach1, DayOfWeek.SATURDAY, new TimeOfDay(15, 0));
        /* Для простоты тут каждый раз new TimeOfDay, в реальности же время начала тренировки может быть одинаковое для
        разных тренировок. Но раз нет консольно интерфейса, то, наверное, такое упрощение уместно
        Кроме того, в реальном спортзале не будет время начала типа "12:47". Все тренировки будут начинаться только в
        0 минут или в 30 ( ну в худшем случае в 15 или 45 минут), так что можно будет сделать объекты времена для каждого
        такого случая и использовать их при создании тренировки. Или Это нерационально было бы? Тут я не знаю как лучше */



    }
}
