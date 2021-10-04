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

В поле **описание плагина** вводим ту информацию, которая будет генирировать система при создании задачи с этим плагином,
в нашем случаае это **"Красные ребра дополняют исходный граф до квадрата"**

В поле **тип плагина** следует выбрать является ли плагин характеристикой или свойством. Важно указать именно
тот тип, который реализует ваш плагин, в противном случае будет выдана ошибка. В нашем случае тип является свойством,
так как возвращает булевский тип.

В поле **тип графа** следует указать с каким графом работает модуль - с ориентированным
или с неориентированным. В примере граф неориентированный.

В поле **файл** загрузите сделанный ранее jar файл и нажмите кнопку добавить плагин. Если
все сделано правильно, то появится сообщение об успехе, иначе сообщение об ошибке.

![Image alt](https://github.com/Terross/math-system/raw/main/images-for-doc/loadInterface2.png)

В сообщение
об ошибке будет приведина информация о том, как от нее избавится. Существуют следующие ошибки: 
* Ошибка в коде плагина - вернет текст ошибки в Java коде (например выход за пределы массива).
* Долгое время выполнения - если ваш плаигн не справится с тестовым графом за 3 секунды, то вызовется
   данная ошибка.
* В jar файле не обнаружен класс - вызывается в том случае, если в jar файле нет класса, который
    совпадает с именем самого файла
* Класс обнаружен, но реализует не тот тип - в этом случае нужно либо реализовать другой тип 
    плагина, либо указать другой тип плагина на сайте
## Дополнительные возможности


