import Vue from "vue"
import Vuex from "vuex"
import tasksApi from '../api/tasks.js'
import graphsApi from '../api/graphs.js'
import pluginApi from '../api/plugins.js'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        //graph: frontendData.graph,
        plugins: frontendData.algorithms,
        tasks: frontendData.tasks
    },
    getters: {
        findTaskById: (state) => (id) => {
            return state.tasks.find(task => task.id.toString() === id.toString())
        },
        findGraphDataByTaskId: (state) => (task) => {
            let elements = [];
            if (task.graph != null) {
                let adj = task.graph.vertexes;
                for (let i = 0; i < adj.length; i++) {
                    elements.push(
                        {
                            group: 'nodes',
                            data: { label: '', id: i, name: adj[i].name}
                        }
                    )
                }
                let idForEdge = adj.length
                for (let i = 0; i < adj.length; i++) {
                    let edge = adj[i].incomingEdges;
                    for (let j = 0; j < edge.length; j++) {
                        let edgeSource = elements.find(v => v.data.name === edge[j].fromV && v.group === 'nodes')
                        let edgeTarget = elements.find(v => v.data.name === edge[j].toV  && v.group === 'nodes')
                        if (edgeSource !== null && edgeTarget !== null) {

                            elements.push(
                                {
                                    group: 'edges',
                                    data: { id: idForEdge, source: edgeSource.data.id, target: edgeTarget.data.id, name:edge[j].weight}
                                }
                            )
                            idForEdge++;
                        }

                    }
                }


            }
            return elements;
        }
        // graphData: (state) => {
        //     let elements = [];
        //     let adj = state.graph.adjacencyList;
        //     console.log(adj)
        //     for (let i = 0; i < adj.length; i++) {
        //         elements.push(
        //             {
        //                 group: 'nodes',
        //                 data: { id: adj[i].id, name: adj[i].id}
        //             }
        //         )
        //         let edge = adj[i].adjacencyList;
        //         for (let j = 0; j < edge.length; j++) {
        //             elements.push(
        //                 {
        //                     group: 'edges',
        //                     data: { id: edge[j].weight, source: edge[j].v, target: edge[j].w, name:edge[j].weight}
        //                 }
        //             )
        //         }
        //     }
        //     return elements;
        // }
    },
    mutations: {
        addTaskMutation(state, task) {
            state.tasks =[
                ...state.tasks,
                task
            ]
        },
        removeTaskMutation(state, task) {
            const removeIndex = state.tasks.findIndex(item => item.id === task.id)

            if (removeIndex > -1) {
                state.tasks = [
                    ...state.tasks.slice(0, removeIndex),
                    ...state.tasks.slice(removeIndex + 1)
                ]
            }
        },
        updateTaskMutation(state, task) {

        },
        addPluginMutation(state, plugin) {
            state.plugins = [
                ...state.plugins,
                plugin
            ]
        },
        removePluginMutation(state, plugin) {
            const removeIndex = state.plugins.findIndex(item => item.id === plugin.id)

            if (removeIndex > -1) {
                state.plugins = [
                    ...state.plugins.slice(0, removeIndex),
                    ...state.plugins.slice(removeIndex + 1)
                ]
            }
        }
    },
    actions: {
        async removeTaskAction({commit}, task) {
            const result = await tasksApi.remove(task.id)

            if (result.ok) {
                commit('removeTaskMutation', task)
            }
        },
        async addTaskAction({commit, state}, task) {

            const result = await tasksApi.add(task)
            const data = await result.json()
            const index = state.tasks.findIndex(item => item.id === data.id)

            if (index > -1) {
                commit('updateTaskMutation', data)
            } else {
                commit('addTaskMutation', data)
            }

        },
        async updateTaskAction({commit}, task) {
            const result = await tasksApi.update(task)
            const data = await result.json()
            commit('updateTaskMutation', data)
        },
        async addPluginAction({commit, state}, plugin) {
            const result = await pluginApi.add(plugin)
            const data = await result.json()
            const index = state.plugins.findIndex()

            console.log(data)
            if (index > -1) {
                console.log(1234556)
            } else {
                commit('addPluginMutation', data)
            }
        }
    }
})