<template>
  <v-app id="inspire">
    <v-dialog
        v-model="dialog"
        width="75%"
    >
      <v-card>
        <v-card-title>
          <span class="text-h4">Как пользоваться системой</span>
        </v-card-title>
        <v-card-text>

          <p class="text-h5">
            Конструктор задач
          </p>
          <p class="text-h6">
            Панель создания задачи
          </p>
          Главной на странице конструктора задач является панель создания задачи
          <br>
          <br>
          В самом начале панеле находится описание задачи - как будет выглядеть задача после создания
          <br>
          <br>
          Далее с помощью кнопки "Граф заранее построен" можно добавить граф в задачу. После нажатя этой кнопи появится
          окно редактора графа (см. п "Редактор графа")
          <br>
          <br>
          В полях ввода "Данные о задаче" требуется ввести название задачи и ее ктаегорию. Название не должно
          совпадать с уже имеющимися задачами
          <br>
          <br>
          В поле "Тип графа" с помощью кнопкок <v-icon>trending_up</v-icon> и
          <v-icon>show_chart</v-icon> устанавливается тип графа в задаче - ориентированный и неориентированный
          соответсвенно
          <br>
          <br>
          В поле "Ограничение конструктора" можно заблокировать некоторые функции в редакторе графа при решении задачи, а
          именно "строительсвто графа", "смену цвета", "смену весов и меток", "удаление элементов графа"
          <br>
          <p class="text-h6">
            Редактор графа
          </p>
          Редактор графа имеет несколько режимов работы:
          <br>
          1. <v-icon>open_with</v-icon> "режим перемешения". В этом режиме можно
          перемещать вершины графа, а при клике на вершину или ребро система выведет информацию о
          весе и метке элемента
          <br>
          2. <v-icon>gesture</v-icon> "режим рисования". В этом режиме при нажатии на пустой холст
          можно создать вершину, чтобы создать ребро надо нажать на вершину-источник и потянуть на вершину-цель
          <br>
          3. <v-icon>edit</v-icon> "режим редактирования". В этом режиме при нажатии на вершину или ребро можно задать
          метку или вес для ребра или вершины
          <br>
          4. <v-icon>delete</v-icon> "режим удаления". В этом режиме можно удалять элементы графа нажимая на них
          <br><br>
          Кнопка "отображение графа" поможет вам преестроить граф, если вам не нравится, как он отображен
          <br>
          "zoom" - приближает граф так, чтобы были видны все вершины
          <br>

          "random" - слуйчайная перестройка графа
          <br>
          "circle" - перестройка графа по кругу
          <br>
          "components" - разбитие графа по компонентам
          <br>
          "tree" - отображение графа в виде дерева
          <br>
          <br>
          Кнопка "скачать граф файлом" скачивает граф в виде txt файла, для проверки работы модулей до внедрения в систему
          <br>
          <p class="text-h6">
            Решение задач
          </p>
          В списке задач представлены все доступные задачи. Нажав на кнопку "решить задачу", можно открыть и решить выбранную задачу.
          <br>
          В окне задаче представлено описание задачи и редактор графа (см.п. редактор графа). Чтобы решить задачу требуется
          нарисовать граф, который удовлетворяет представленным требованиям (или изменить уже имеющийся граф).
          <br>
          <p class="text-h6">
            Загрузка плагинов
          </p>
          Данная страница используется для загрузки дополнительных плагинов. Чтобы загрузить плагин требуется ввести его название.
          В поле "описание плагина" требуется указать то, как плагин будет представлен в задачах, например,
          "количество вершин в графе". В поле тип плагина надо указать является ли плагин свойством (возвращает boolean)
          или характеристикой (возвращает целочисленное число). В поле тип графа требуется указать к какому типу графа относится
          плагин (ориентированный или неориентированный). Далее требуется поместить jar файл в поле ввода файла и нажать
          кнопку добавить плагин.

          <br>
          <br>
          Ниже карточки добавления плагина предствален список всеъ имеющиъся плагинов
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-app-bar
        app
        color="primary"
        dark
        height="64px"
    >
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      <v-toolbar-title
      class="ma-4">Модуль графы</v-toolbar-title>
      <div v-if="$route.path === '/'">
        Конструктор задач
      </div>
      <div v-if="$route.path === '/tasks'">
        Список задач
      </div>
      <div v-if="$route.path === '/plugins'">
        Плагины
      </div>
      <v-spacer>

      </v-spacer>
      <v-btn
          color="secondary"
          text
          dark
          @click="dialog = true"
      >
        <v-icon>help_outline</v-icon>
      </v-btn>

    </v-app-bar>

    <v-navigation-drawer
        v-model="drawer"
        fixed
        app
        temporary
    >
      <v-list
          nav
          dense
      >
        <v-list-item-group
            v-model="group"
            active-class="primary"
        >
          <v-list-item
              :disabled="$route.path === '/'"
              @click="showTaskConstructor"
          >
            <v-list-item-icon>
              <v-icon>edit</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Конструктор задач</v-list-item-title>
          </v-list-item>

          <v-list-item
              :disabled="$route.path === '/tasks'"
              @click="showTaskList">
            <v-list-item-icon>
              <v-icon>task</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Список задач</v-list-item-title>
          </v-list-item>

          <v-list-item
              :disabled="$route.path === '/plugins'"
              @click="showPlugins">
            <v-list-item-icon>
              <v-icon>extension</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Модули</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>


    <v-main>
      <router-view></router-view>
    </v-main>


  </v-app>
</template>

<script>
import GraphEditor from "../components/cylc/graphEditor.vue";
import TaskList from "./TaskList.vue";
import taskConstructor from "./TaskConstructor.vue";
import { mapState, mapGetters } from "vuex";
export default {
  components: {TaskList, GraphEditor, taskConstructor},
  computed: {
    profile: {
        ...mapState({get: 'profile'})
    },
    isAuth: {
        ...mapGetters({get: 'profile/isAuth'})
    }
  },
  data() {
    return {
      tab: null,
      drawer: false,
      group: null,
      dialog: false,
      items: [
          'Сборник задач', 'Конструктор задач', 'Новые модули'
      ]
    }
  },
  beforeMount() {
    if (!this.isAuth) {
      this.$router.replace('/auth/login')
    }
  },
  methods: {
    showTaskConstructor() {
      this.$router.push('/')
    },
    showTaskList() {
      this.$router.push('/tasks')
    },
    showPlugins() {
      this.$router.push('/plugins')
    }
  }
}
</script>

<style>
#main-container {
  width: 100%;
  height: 100%;
}
</style>