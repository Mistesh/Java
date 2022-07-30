import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;

public class FirstSprint {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        StepTracker st = new StepTracker();
        Handler.printMenu();
        int userInput = scanner.nextInt();


        while (userInput != 4) {
            switch (userInput){
                case 1:{
                    int monthNumber = Handler.inputMonth();
                    int dayNumber = Handler.inputDay();
                    st.showStepsOfTheDay(monthNumber, dayNumber);
                    break;
                }
                case 2:{
                    int monthNumber = Handler.inputMonth();
                    st.showMonthStatistic(monthNumber);
                    break;
                }
                case 3:{ //
                    System.out.println("Текущая цель: "+st.goal+" шагов.");
                    System.out.print("Введите новую цель: ");
                    int newGoal = scanner.nextInt();
                    while (true){
                        if (newGoal>0){
                            st.goal = newGoal;
                            System.out.println();
                            break;
                        }
                        else{
                            System.out.println("Неверное значение. Попробуйте снова");
                        }
                    }
                    break;
                }
                default:{ //
                    System.out.println("Неверное значение. Попробуйте снова");
                    break;
                }

            }
            Handler.printMenu(); // печатем меню ещё раз перед завершением предыдущего действия
            userInput = scanner.nextInt(); // повторное считывание данных от пользователя
        }
        System.out.println("Программа завершена");
    }

    public static class StepTracker {
        HashMap<Integer, MonthData> monthToData = new HashMap<Integer, MonthData>();
        int goal = 10_000;

        public StepTracker() {
            for (int i = 0; i < 12; i++) {
                monthToData.put(i, new MonthData());
            }
        }



        public void showStepsOfTheDay(int month, int day){
            System.out.println("Шагов пройдено за " + (day+1) + " день " + (month+1) + " месяца: " +monthToData.get(month).stepsOfTheDay[day]);
            System.out.println();
        }

        public void showMonthStatistic(int month){
            printSteps(month);
            printSumOfSteps(month);
            printMaxSteps(month);
            printSrednee(month);
            printDistance(month);
            printEnergy(month);
            printBestSeries(month);
            System.out.println();
            System.out.println();
        }

        private void printSteps(int month){
            MonthData currentMonth = monthToData.get(month);
            for (int i = 0; i<currentMonth.stepsOfTheDay.length;i++) {
                System.out.print("День " + (int)(i+1) + ": " + currentMonth.stepsOfTheDay[i] + " шагов. ");
            }
            System.out.println();
        }

        private void printSumOfSteps(int month){
            MonthData currentMonth = monthToData.get(month);
            int sumSteps = 0;
            for (int i = 0; i<currentMonth.stepsOfTheDay.length;i++) {
                sumSteps+= currentMonth.stepsOfTheDay[i];
            }
            System.out.println("Общее число шагов за " + (month+1) + " месяц: " + sumSteps);
        }

        private void printMaxSteps(int month){
            MonthData currentMonth = monthToData.get(month);
            int maxSteps = Arrays.stream(currentMonth.stepsOfTheDay).max().getAsInt();
            System.out.println("Максимальное число шагов: " + maxSteps);
        }

        private void printSrednee(int month){
            MonthData currentMonth = monthToData.get(month);
            int sredneeSteps = 0;
            for (int i = 0; i<currentMonth.stepsOfTheDay.length;i++) {
                sredneeSteps+= currentMonth.stepsOfTheDay[i];
            }
            sredneeSteps/=currentMonth.stepsOfTheDay.length;
            System.out.println("Среднее число шагов за " + (month+1) + " месяц: " + sredneeSteps);
        }

        private void printDistance(int month){
            MonthData currentMonth = monthToData.get(month);
            int sumSteps = 0;
            for (int i = 0; i<currentMonth.stepsOfTheDay.length;i++) {
                sumSteps+= currentMonth.stepsOfTheDay[i];
            }
            System.out.println("Пройденная дистанция за " + (month+1) + " месяц: " + Converter.getDistance(sumSteps));
        }

        private void printEnergy(int month){
            MonthData currentMonth = monthToData.get(month);
            int sumSteps = 0;
            for (int i = 0; i<currentMonth.stepsOfTheDay.length;i++) {
                sumSteps+= currentMonth.stepsOfTheDay[i];
            }
            System.out.println("Потрачено калорий за " + (month+1) + " месяц: " + Converter.getEnergy(sumSteps) + " калорий (или " + (double)(Converter.getEnergy(sumSteps)/1000)+ " килокалорий).");
        }

        private void printBestSeries(int month){
            MonthData currentMonth = monthToData.get(month);
            int startNumber = 0;
            int endNumber = 0;
            int currentStartNumber = 0;
            int currentEndNumber = 0;

            for (int i = 0; i<currentMonth.stepsOfTheDay.length-1;i++) {
                if (currentMonth.stepsOfTheDay[i]<currentMonth.stepsOfTheDay[i+1]){
                    currentEndNumber = i+1;
                }
                else{
                    if ((currentEndNumber-currentStartNumber)>=(endNumber-startNumber)){
                        startNumber=currentStartNumber;
                        endNumber = currentEndNumber;
                    }
                    currentStartNumber = i+1;
                    currentEndNumber = i+1;

                }

            }
            System.out.println("Самая длинная последовательность с "+ (1+startNumber) + " по " + (1+endNumber) + " день.");
            for (int i = startNumber;i<=endNumber;i++){
                System.out.print(currentMonth.stepsOfTheDay[i] + " ");
            }
        }

        public void showMonthData(Integer keyMap){

            MonthData currentMonth = monthToData.get(keyMap);
            System.out.println("Месяц: " + (int)(keyMap+1));

            for (int i = 0; i<currentMonth.stepsOfTheDay.length;i++) {
                System.out.print("День " + (int)(i+1) + ": " + currentMonth.stepsOfTheDay[i] + " шагов. ");
            }

        }

        class MonthData {
            int[] stepsOfTheDay = new int[30];

            public MonthData(){
                Random random = new Random();
                for (int i = 0; i < stepsOfTheDay.length; i++){
                    stepsOfTheDay[i] = random.nextInt(20_000);  // Заполняет количество шагов в день числом от 0 до 20000
                }
            }
        }
    }

    public static class Converter {

        public static double getDistance(int steps){
            return steps*0.75;
        }

        public static double getEnergy(int steps){
            return steps*50;
        }

    }

    public static class Handler {

        static Scanner scannerInHandler = new Scanner(System.in);

        public static int inputMonth(){

            int monthNumber;
            Handler.printChooseMonth();
            while (true){
                monthNumber = scannerInHandler.nextInt();
                if (monthNumber>0 && monthNumber<=12){
                    monthNumber-=1;
                    break;
                }
                else {
                    System.out.print("Неверное значение месяца. Попробуйте снова: ");
                }
            }
            return monthNumber;
        }

        public static int inputDay() {

            int dayNumber;
            Handler.printChooseDay();
            while (true){
                dayNumber = scannerInHandler.nextInt();
                if (dayNumber>0 && dayNumber<=30){
                    dayNumber-=1;
                    break;
                }
                else {
                    System.out.print("Неверное значение дня. Попробуйте снова: ");
                }
            }
            return dayNumber;
        }


        public static void printMenu() {
            System.out.print("Выберите действие:\n" +
                    "1. Ввести количество шагов за определённый день;\n" +
                    "2. Напечатать статистику за определённый месяц;\n" +
                    "3. Изменить цель по количеству шагов в день;\n" +
                    "4. Выйти из приложения.\n" +
                    "Ваш выбор: ");
        }

        public static void printChooseMonth() {
            System.out.print("Выберите месяц (по номеру месяца от 1 до 12):\n" +
                    "1. Январь;\n" +
                    "2. Февраль;\n" +
                    "3. Март;\n" +
                    "4. Апрель;\n" +
                    "5. Май;\n" +
                    "6. Июнь;\n" +
                    "7. Июль;\n" +
                    "8. Август;\n" +
                    "9. Сентябрь;\n" +
                    "10. Октябрь;\n" +
                    "11. Ноябрь;\n" +
                    "12. Декабрь;\n");
            System.out.print("Ваш выбор: ");
        }

        public static void printChooseDay() {
            System.out.println("Выберите день (по номеру дня от 1 до 30): ");
            System.out.print("Ваш выбор: ");
        }

    }

}
