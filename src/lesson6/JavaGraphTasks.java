package lesson6;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    // ресурсоемкость - O(V + E)
    // трудоемкость - O(V*log(V));
    public static List<Graph.Edge> findEulerLoop(Graph graph){
        List<Graph.Edge> result = new ArrayList<Graph.Edge>();
        if (graph.getVertices().size() == 0) return result;
        var allV = graph.getVertices();
        int totalOddDegree = 0;
        Graph.Vertex firstVertex = null;
        for (var v: allV){
            if (firstVertex == null) firstVertex = v;
            var neighbors = graph.getNeighbors(v);
            if (neighbors.size() % 2 != 0){
                totalOddDegree++;
            }
        }
        if (totalOddDegree > 2 || totalOddDegree == 1){
            return result;
        }
        ArrayList<Graph.Vertex> path = new ArrayList<>();
        HashSet<Graph.Edge> currentEdges = new HashSet<>();
        path.add(firstVertex);
        boolean success = continueEulerLoop(graph, path, graph.getEdges().size(), currentEdges);
        if (success){
            for (int i = 1; i < path.size(); i++){
                var e = graph.getConnection(path.get(i-1), path.get(i));
                result.add(e);
            }
            var e = graph.getConnection(path.get(path.size()-1), path.get(0));
            result.add(e);
            return result;
        }
        else{
            return result;
        }
    }

    public static boolean continueEulerLoop(Graph graph, ArrayList<Graph.Vertex> currentPath, int targetEdgeCount, HashSet<Graph.Edge> currentEdges){
        if (graph.getConnection(currentPath.get(0), currentPath.get(currentPath.size() - 1))!= null){
            if (currentEdges.size() == targetEdgeCount - 1){
                return true;
            }
        }
        //обойдем все возможные непосещенные пути
        var lastV = currentPath.get(currentPath.size() - 1);
        var neighbors = graph.getNeighbors(lastV);
        if (neighbors.size() == 0) return false;
        else{
            for (var n: neighbors){
                var e = graph.getConnection(lastV, n);
                if (e == null) continue;
                if (currentEdges.contains(e)) continue;
                currentPath.add(n);
                currentEdges.add(e);
                boolean r = continueEulerLoop(graph, currentPath, targetEdgeCount, currentEdges);
                if (r) return true;
                //отменить выбор
                currentPath.remove(currentPath.size()-1);
                currentEdges.remove(e);
            }
            return false;
        }
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Если на входе граф с циклами, бросить IllegalArgumentException
     *
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    // ресурсоемкость - O(V);
    // трудоемкость - E+ E*log(V) + V*log(V) ->  O(logV(E + V));
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        HashSet<Graph.Vertex> copyVer = new HashSet<>(graph.getVertices());
        HashMap<Graph.Vertex, Integer> visitedV = new HashMap<>();
        ArrayList<Graph.Vertex> vertexList = new ArrayList<>(graph.getVertices());
        if (checkUnConnected(vertexList, graph)) return graph.getVertices();
        for (int i =0; i < vertexList.size(); i++){
            visitedV.put(vertexList.get(i), 0);
        }
        if (loopFound(copyVer.iterator().next(), copyVer, visitedV, graph)) throw new IllegalArgumentException();


        HashSet<Graph.Vertex> copyV = new HashSet<>(graph.getVertices());
        HashSet<Graph.Vertex> resultV = new HashSet<>();
        while (copyV.size() != 0){
            Graph.Vertex vertex = copyV.iterator().next();
            resultV.add(vertex);
            var ns = graph.getConnections(vertex);
            for (var n: ns.entrySet()){
                if (n.getValue().getBegin().equals(vertex)){
                    copyV.remove(n.getValue().getEnd());
                }
                else{
                    copyV.remove(n.getValue().getBegin());
                }
            }
            copyV.remove(vertex);
        }
        return resultV;
    }

    public static boolean checkUnConnected(ArrayList<Graph.Vertex> vertexList, Graph graph){
        boolean ok = true;
        for (int i =0; i< vertexList.size()-1; i++){
            for (int j = i+1; j< vertexList.size(); j++){
                if (graph.getConnection(vertexList.get(i), vertexList.get(j))!= null){
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean loopFound(Graph.Vertex start, Set<Graph.Vertex> copyV, Map<Graph.Vertex, Integer> visitedV, Graph graph){
        var ns = graph.getConnections(start);
        for (var n: ns.entrySet()){
            if (n.getValue().getBegin().equals(start)){
                if (copyV.contains(n.getValue().getEnd()) && visitedV.get(n.getValue().getEnd()) > 0){
                    return true;
                }
            }
            else{
                if (copyV.contains(n.getValue().getBegin()) && visitedV.get(n.getValue().getBegin()) > 0){
                    return true;
                }
            }
        }
        copyV.remove(start);
        for (var n: ns.entrySet()){
            if (n.getValue().getBegin().equals(start)){
                if (copyV.contains(n.getValue().getEnd())){
                    visitedV.put(n.getValue().getEnd(), 1);
                    return loopFound(n.getValue().getEnd(), copyV, visitedV, graph);
                }
            }
            else {
                if (copyV.contains(n.getValue().getBegin())) {
                    visitedV.put(n.getValue().getBegin(), 1);
                    return loopFound(n.getValue().getBegin(), copyV, visitedV, graph);
                }
            }
        }
        return false;
    }


    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        throw new NotImplementedError();
    }


    /**
     * Балда
     * Сложная
     *
     * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
     * поэтому задача присутствует в этом разделе
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
