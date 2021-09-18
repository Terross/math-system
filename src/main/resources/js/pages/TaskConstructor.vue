<template>
    <v-container fluid>
      <v-row>
        <v-col v-if="graphEditorVisible" >
          <graph-editor></graph-editor>
        </v-col>
        <v-col>
          <element-redactor v-if="graphEditorVisible">
          </element-redactor>
          <plugin-list >
          </plugin-list>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
    import PluginList from "../components/taskCreating/pluginList.vue";
    import graphEditor from "../components/cylc/graphEditor.vue";
    import ElementRedactor from "../components/taskCreating/elementRedactor.vue";
    export default {
      name: 'taskConstructor',
      components: {ElementRedactor, PluginList, graphEditor},
      data() {
        return {
          selectedData: this.$store.state.constructorGraph.selectedData
        }
      },
      computed: {
        graphEditorVisible() {
          return this.$store.state.constructorGraph.graphPresent
        }
      }
    };
</script>
