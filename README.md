# Math-System

## Описание системы

Работа выполнена в качестве учебного проекта. Система используется для
создания и решения различных задач на графы. В условия задачи можно добавлять 
различные внешние модули, которые могут быть загружены в систему.
[Ссылка на сайт](http://math-system.ru). 


Сама система состоит из двух частей:

- **Серверная часть**. Использует **Java Spring** и служит для работы
с базой данных и работе с внешними модулями.
- **Клиентская часть**. Использует **Vue JS** и служит для предоставления
пользователю интерфейса в браузере.

## Написание внешних модулей

Важным свойстом системы является возможность написания дополнительных модулей.
Как это сделать рассказывается  в этой главе. Как пример будет использоваться среда
**IntelliJ IDEA**.

### Пример
#### Написание модуля на  Java
1. Создаем новый maven проект.
   ![Image alt](https://github.com/Terross/math-system/raw/main/images-for-doc/maven-project.png)
   ![Image alt](https://github.com/Terross/math-system/raw/main/images-for-doc/project-name.png)
   


2. В файле **pom.xml** добавляем в зависимости библиотеку для работы с графами. Есть два 
    способа это сделать
   * Загрузить библиотеку в качестве jar файла, где вместо 
     **path-to-jar** следует указать путь к jar файлу **graph-lib.jar**, 
     который можно скачать из этого репозитория.
      ```maven
     <dependencies>
          <dependency>
              <groupId>com.example</groupId>
              <artifactId>math-system</artifactId>
              <version>1.0-SNAPSHOT</version>
              <scope>system</scope>
              <systemPath>path-to-jar</systemPath>
          </dependency>
     </dependencies>
      ```
   * Воспользоваться [jitpack](https://jitpack.io/) (сейчас не работает)
      ```maven
     <repositories>
              <repository>
                  <id>jitpack.io</id>
                  <url>https://jitpack.io</url>
              </repository>
     </repositories>
   
     <dependencies>
        <dependency>
             <groupId>com.github.Terross</groupId>
             <artifactId>math-system</artifactId>
             <version>v1.1</version>
        </dependency>
     </dependencies>
      ```
3. Создаем класс вашей задачи, а затем реализовать необходимый 
    интерфейс
    ```java
   public class GraphSquare implements GraphProperty {
       @Override
       public boolean execute(AbstractGraph abstractGraph) {
           ArrayList<PrimitiveEdge> primitiveEdges = new ArrayList<>();

           for (int j = 0; j < abstractGraph.getVertexCount(); j++) {
               BFS bfs = new BFS(j, abstractGraph);
               bfs.bfs();
               for (int i = 0; i < abstractGraph.getVertexCount(); i++) {
                   if (bfs.pathTo(i) - 1 == 2) {
                       primitiveEdges.add(new PrimitiveEdge(j, i));
                   }
               }
           }

           int grayEdgeCount = 0;
           List<Vertex> vertices = abstractGraph.getVertices();
           List<AbstractEdge> answer = new ArrayList<>();
           for (int i = 0; i < abstractGraph.getVertexCount(); i++) {
            Vertex vertex = vertices.get(i);
               for (AbstractEdge abstractEdge: vertex.getEdgeList()) {
                   if (abstractEdge.getColor() == Color.red) {
                       answer.add(abstractEdge);
                   }
               }
           }

           if (answer.size() != primitiveEdges.size()) {
               return false;
           }

           for (int i = 0; i < answer.size(); i++) {
               int v = answer.get(i).getV().getIndex();
               int w = answer.get(i).getW().getIndex();
               boolean flag = false;
               for (int j = 0; j < answer.size(); j++) {
                   int from = primitiveEdges.get(j).getFrom();
                   int to = primitiveEdges.get(j).getTo();
                   if (((v == from && w == to) || (v == to && w == from))) {
                       flag = true;
                   }
               }
               if (!flag) {
                   return false;
               }
           }
           return true;
       }
   }
   ```
4. Настраиваем проект для создания jar файла модуля
   ![Image alt](https://github.com/Terross/math-system/raw/main/images-for-doc/createJar.png)
5. Собираем проект и итоговый jar файл готов!

#### Загрузка модуля в систему
 Чтобы выгрузить ваш модуль в систему нужно воспользоваться интерфейсом на сайте

 ![Image alt](https://github.com/Terross/math-system/raw/main/images-for-doc/loadInterface.png)

В поле **название плагина** введите желаемое имя плагина. В качестве примера используем **"Квадрат графа"**.

В поле **описание плагина** вводим ту информацию, которая будет генерировать система при создании задачи с этим плагином,
в нашем случае это **"Красные ребра дополняют исходный граф до квадрата"**

В поле **тип плагина** следует выбрать является ли плагин характеристикой или свойством. Важно указать именно
тот тип, который реализует ваш плагин, в противном случае будет выдана ошибка. В нашем случае тип является свойством,
так как возвращает булевский тип.

В поле **тип графа** следует указать с каким графом работает модуль - с ориентированным
или с неориентированным. В примере граф неориентированный.

В поле **файл** загрузите сделанный ранее jar файл и нажмите кнопку добавить плагин. Если
все сделано правильно, то появится сообщение об успехе, иначе сообщение об ошибке.

![Image alt](https://github.com/Terross/math-system/raw/main/images-for-doc/loadInterface2.png)

В сообщение
об ошибке будет приведена информация о том, как от нее избавится. Существуют следующие ошибки: 
* Ошибка в коде плагина - вернет текст ошибки в Java коде (например выход за пределы массива).
* Долгое время выполнения - если ваш плаигн не справится с тестовым графом за 3 секунды, то вызовется
   данная ошибка.
* В jar файле не обнаружен класс - вызывается в том случае, если в jar файле нет класса, который
    совпадает с именем самого файла
* Класс обнаружен, но реализует не тот тип - в этом случае нужно либо реализовать другой тип 
    плагина, либо указать другой тип плагина на сайте
## Дополнительные возможности
Чтобы писать плагины, нужно разобраться как устроен класс **AbstractGraph** и все что с ним связано.

В абстрактном классе графа 3 поля - количество ребер, количество вершин и список всех вершин.
Чтобы получить доступ к любому из полей можно использовать геттеры.
```java
/**
 * Абстрактный класс графа
 */
@Data
public abstract class AbstractGraph {
    /**Количество вершин в графе*/
    protected int vertexCount;
    /**Количество ребер в графе*/
    protected int edgeCount;
    /**Список вершин Vertex */
    protected List<Vertex> vertices;

    @Override
    public String toString() {
        return  "\nvertexCount = " + vertexCount +
                "\nedgeCount = " + edgeCount +
                "\nСписок смежности = " + vertices;
    }
}
```

От абстрактного графа наследуются два класса - класс ориентированного графа и неориентированного графа

**Ориентированный граф**, как и неориентированный имеет все поля родителя, а кроме того имеет два
конструктора. Первый конструктор имеет множество параметров, но он используется в контроллерах, поэтому
для написания своих модулей он не требуется. Второй конструктор принимает в качестве параметра файл, который
можно получить на сайте в режиме **конструктора графа**. Ниже представлен код ориентированного графа, код 
неориентированного аналогичен. 
```java
/**
 * Класс ориентированного графа
 */
@Data
public class DirectedGraph extends AbstractGraph {

    /**
     * Конструктор - создание нового объекта
     * ориентированного графа с определенными значениями.
     * Используется в контроллерах - не используется в плагинах
     * @param vertexCount - количество вершин
     * @param edgeCount - количество ребер
     * @param edges - список ребер
     * @param vertices - список вершин
     */
    public DirectedGraph(int vertexCount,
                         int edgeCount,
                         List<Edge> edges,
                         List<com.mathsystem.entity.graph.Vertex> vertices) {
        ...
    }

    /**
     * Конструктор - создание нового объекта
     * ориентированного графа с определенными значениями из файла.
     * Используется в плагинах
     * @param file - объект файла с графом
     * @throws FileNotFoundException
     */
    public DirectedGraph(File file)  {
        ...
    }

    @Override
    public String toString() {
        return "DirectedGraph = {" + super.toString()
                + "\n}";
    }
}
```

Для того чтобы получить граф из файла, рекомендуется использовать фабрику графа выбрав одну
из двух требуемых функций - **loadUndirectedGraphFromFile** или **loadDirectedGraphFromFile**
соответственно для неориентированного и ориентированного графа. Данные действия требуется проводить
для тестирования работы модуля. **Итоговый модуль должен работать с AbstractGraph, который
он получает в системе**. Не пытайтесь загрузить граф из файла в коде результирующего класса.
Делайте это в main и передавайте абстрактный граф в метод execute вашего модуля.

```java
GraphSquare graphSquare = new GraphSquare();
System.out.println(graphSquare.
execute(GraphFactory.loadUndirectedGraphFromFile(
new File("/home/dmitry/Downloads/graph.txt"))));
```

**Класс вершины Vertex**. Имеет поля, которые задают вершину - индекс, имя (совпадает с
индексом, только является строкой, а не числом), цвет, вес, дополнительная метка. Также
имеет список ребер, которые выходят из данной вершины.

```java
/**
 * Класс вершины в графе
 * @see Vertex#Vertex(Integer, String, Color, Integer, String, List)
 */
@Data
public class Vertex {
    private final Integer index;
    /**Имя вершины. Поле используется как индекс */
    private final String name;
    /**Цвет вершины */
    private final Color color;
    /**Вес вершины */
    private final Integer weight;
    /**Метка для дополнительной информации о вершине */
    private final String label;
    /**Список выходящих из вершины ребер */
    private List<AbstractEdge> edgeList;
    /**
     * Конструктор - создание нового объекта вершины с определенными значениями
     * @param index - производитель
     * @param label - цена
     * @param color - цвет
     * @param weight - вес
     * @param name - имя
     * @param edgeList - список выходящих ребер @see AbstractEdge
     * @see AbstractEdge#AbstractEdge(Vertex, Vertex, Integer, Color, String, String)
     */
    public Vertex(
            Integer index,
            String name,
            Color color,
            Integer weight,
            String label,
            List<AbstractEdge> edgeList
    ) {
        this.index = index;
        this.color = color;
        this.name = name;
        this.weight = weight;
        this.label = label;
        this.edgeList = edgeList;
    }

    @Override
    public String toString() {
        return String.format("\nVertex %s = {" +
                "\ncolor = %s" +
                "\nweight = %s" +
                "\nlabel = %s" +
                "\nedgeList = %s",
                name, color, weight, label, edgeList);
    }
}
```

**Клас ребра**. Абстрактный класс ребра имеет следующие поля:

* v - исходная вершина
* w - целевая вершина (важно для ориентированного ребра)
* weight - вес
* color - цвет
* label - метка
* name - имя

Метод **eihter** возвращает первую вершину v, а метод **other** принимая одну вершину
вернет вторую.

```java
/**
 * Абстрактный класс ребра
 * @see AbstractEdge#AbstractEdge(Vertex, Vertex, Integer, Color, String, String)
 */
@Data
public abstract class AbstractEdge implements Comparable<AbstractEdge> {
    /**Начальная вершина*/
    protected final Vertex v;
    /**Целевая вершина*/
    protected final Vertex w;
    /**Вес ребра*/
    protected final Integer weight;
    /**Цвет ребра*/
    protected final Color color;
    /**Метка ребра*/
    protected final String label;
    /**Имя ребра. Поле используется как индекс */
    protected final String name;

    /**
     * Конструктор - создание нового объекта ребра с определенными значениями
     * @param v - начальная вершина
     * @param w - целевая вершина
     * @see Vertex#Vertex(Integer, String, Color, Integer, String, List)
     * @param weight - вес ребра
     * @param color - цвет ребра
     * @param label - метка ребра
     * @param name - имя ребра
     */
    public AbstractEdge(
            Vertex v,
            Vertex w,
            Integer weight,
            Color color,
            String label,
            String name
    ) {
        this.v = v;
        this.w = w;
        this.weight = weight;
        this.color = color;
        this.label = label;
        this.name = name;
    }

    /**
     *
     * @return одна из вершин данного ребра
     */
    public Vertex either() {
        return v;
    }

    public Vertex other(Vertex vertex) {
        if (vertex.getName().equals(v.getName())) {
            return w;
        } else {
            if (vertex.getName().equals(w.getName())) {
                return v;
            } else {
                throw new RuntimeException("Недопустимое ребро");
            }
        }
    }

    @Override
    public int compareTo(AbstractEdge abstractEdge) {
        return this.weight.compareTo(abstractEdge.getWeight());
    }
}
```

Классы ориентированного и неориентированного ребра отличаются только выводом графа на экран,
а также ориентированное ребро имеет методы from и to, которые возвращают исходную
и целевую вершину соответственно.



