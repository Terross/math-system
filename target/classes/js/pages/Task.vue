<template>
  <v-container fluid>
      <v-row>
        <v-col>
          <graph-editor :config-elements="graph">
          </graph-editor>
        </v-col>
        <v-col>
          <task-solution :task="task"></task-solution>
        </v-col>
      </v-row>
  </v-container>
</template>

<script>
import graphEditor from "../components/cylc/graphEditor.vue";
import {mapGetters, mapMutations} from "vuex";
import TaskSolution from "../components/tasks/TaskSolution.vue";

export default {
  name: "Task",
  components: {
    TaskSolution,
    graphEditor
  },
  computed: {
    task() {
      return this.$store.getters["tasks/findTaskById"](this.$route.params.id)
    },
    graph() {
      return this.$store.getters["currentGraph/cytoscapeConfigElements"]
    }
  },
  methods: {
    ...mapMutations([
      'currentGraph/initMutation',
      'currentGraph/cleanGraphMutation',
      'tasks/initCurrentTaskMutation'
    ]),
    ...mapGetters('tasks', {
      taskGetter: 'findTaskById',
      graphDataGetter: 'findGraphDataByTaskId'
    })
  },
  beforeMount() {
    const graph = this.task.graph
    const permission = this.task.taskPermission
    if (this.task.graphIsPresent) {
      this['currentGraph/initMutation'] ({
        'edgeCount' : graph.edgeCount,
        'vertexCount': graph.vertexCount,
        'vertexList' : graph.vertexList,
        'edgeList': graph.edgeList
      })
    } else {
      this['currentGraph/initMutation'] ({
        'edgeCount' : 0,
        'vertexCount': 0,
        'vertexList' : [],
        'edgeList': []
      })
    }
    this['tasks/initCurrentTaskMutation'](this.task)
  }
}
</script>

<style scoped>

</style>