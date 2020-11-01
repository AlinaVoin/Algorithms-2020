package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static java.lang.StrictMath.abs;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    // O(N*log(N)) - трудоемкость
    // O(N) - ресурсоемкость
    static class Person implements Comparable<Person>{
        public String street;
        public String firstName;
        public String secondName;
        public int house;
        public Person(String st, String fn, String sn, int h){
            street = st;
            firstName = fn;
            secondName = sn;
            house = h;
        }

        @Override
        public int compareTo(Person person) {
            int r = street.compareToIgnoreCase(person.street);
            if (r != 0) return r;
            if (house < person.house) return -1;
            if (house > person.house) return 1;
            r = secondName.compareToIgnoreCase(person.secondName);
            if (r !=0) return r;
            r = firstName.compareToIgnoreCase(person.firstName);
            return r;
        }
    }

    static public void sortAddresses(String inputName, String outputName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8));
        int total = 0;
        while (br.readLine() != null) {
            total++;
        }
        br.close();
        br = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8));
        Person[] allPersons = new Person[total];
        int i = 0;
        String str = null;
        while ((str = br.readLine()) != null) {
            String[] words = str.split(" ");
            if (words.length != 5){
                throw new UnsupportedOperationException("Wrong quantity of string parts");
            }
            int h = 0;
            try{
                h = Integer.parseInt(words[4]);
            }catch (NumberFormatException e){
                throw new UnsupportedOperationException("House number is not an integer");
            }
            allPersons[i] = new Person(words[3], words[1], words[0], h);
            i++;
        }
        br.close();
        Arrays.sort(allPersons);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputName), StandardCharsets.UTF_8));
        for (int k = 0; k< allPersons.length; k++){
            String street = allPersons[k].street;
            String fName = allPersons[k].firstName;
            String sName = allPersons[k].secondName;
            int house = allPersons[k].house;
            if (k==0 || (!street.equalsIgnoreCase(allPersons[k-1].street) || house != allPersons[k-1].house)){
                if (k > 0){
                    pw.println();
                }
                pw.format("%s %d - %s %s", street, house, sName, fName);
            } else{
                pw.format(", %s %s", sName, fName);
            }
        }
        pw.close();
    }


    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    // O(N*log(N)) - трудоемкость
    // O(N) - ресурсоемкость
    static public void sortTemperatures(String inputName, String outputName) throws IOException {

        int maxTemp = 5000;
        int minTemp = - 2730;

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8));
        int total = 0;
        while (br.readLine() != null) { total++;}
        br.close();

        br = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8));
        int[] temps = new int[total];

        String str;
        int i = 0;
        while ((str = br.readLine()) != null) {
            double num = 0;
            try {
                num = Double.parseDouble(str);
            }catch (NumberFormatException e){ }

            int temp = (int) (num * 10);
            if(maxTemp>=temp && minTemp <= temp) temps[i] = temp;
            i++;
        }

        br.close();
        Arrays.sort(temps);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputName), StandardCharsets.UTF_8));
        for (int j =0; j< i; j++){
            int a = temps[j] / 10;
            int b = temps[j] % 10;
            if (temps[j] < 0) pw.write("-");
            pw.write(abs(a)+"."+abs(b));
            pw.write("\n");
        }
        pw.close();
    }
    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
