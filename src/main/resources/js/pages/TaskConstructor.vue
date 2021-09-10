<template>
    <v-container fluid>
      <v-row>
        <v-col v-if="graphEditorVisible" md="7" >
          <graph-editor :graph-exist="false"></graph-editor>
        </v-col>
        <v-col>
          <plugin-list :graph-vertexies="graphVertexies"
                        :graph-edges-count="edgeCount">
          </plugin-list>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
    import PluginList from "../components/taskCreating/pluginList.vue";
    import graphEditor from "../components/cylc/graphEditor.vue";
    export default {
      name: 'taskConstructor',
      components: {PluginList, graphEditor},
      data() {
        return {
          graphEditorVisible: false,
          vertexCount: 0,
          edgeCount: 0,
          graphVertexies: []
        }
      }
    };
</script>
