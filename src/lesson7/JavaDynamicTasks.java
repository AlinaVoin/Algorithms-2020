package lesson7;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    //ресурсоемкость - O(N1*N2);
    //трудоемкость - O(N1*N2);
    public static String longestCommonSubSequence(String first, String second){
        int[][] data = new int[first.length() + 1][second.length() + 1];
        for (int i = first.length() - 1; i>=0; i--){
            for (int j = second.length() - 1; j >= 0; j--){
                if (first.charAt(i) == second.charAt(j)){
                    data[i][j] = 1+ data[i+1][j+1];
                }
                else{
                    data[i][j] = Math.max(data[i+1][j], data[i][j+1]);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 0; data[i][j] != 0 && i<first.length() && j < second.length();){
            if (first.charAt(i) == second.charAt(j)){
                sb.append(first.charAt(i));
                i++;
                j++;
            }
            else{
                if (data[i][j] == data[i+1][j]){
                    i++;
                }
                else j++;
            }
        }
        return sb.toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() == 0) return new ArrayList<>();
        int[] prev = new int[list.size()];
        int[] d = new int[list.size()];
        for (int i = 0; i<list.size(); i++){
            d[i] = 1;
            prev[i] = -1;
            for (int j = 0; j < i; j++){
                if (list.get(j) < list.get(i) && d[j] + 1 > d[i]){
                    d[i] = d[j] + 1;
                    prev[i] = j;
                }
            }
        }
        int pos = 0;
        int len = d[0];
        for (int i = 0; i< list.size(); i++){
            if (d[i] > len){
                pos = i;
                len = d[i];
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        while (pos != -1){
            result.add(list.get(pos));
            pos = prev[pos];
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
