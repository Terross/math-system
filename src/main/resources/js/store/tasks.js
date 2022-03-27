import tasksApi from "../api/tasks";
import currentGraph from "./currentGraph";

const state = () => ({
    tasks: frontendData.tasks,
    currentTask : {
        description: '',
        name: '',
        category: '',
        plugins: [],
        permission: {
            draw: true,
            color: true,
            edit: true,
            remove: true
        },
        graphDirect: true,
        graphIsPresent: false,
        graph: currentGraph.state()
    }
})

const getters = {
    generatedDescription: (state) => {
        let text = 'Постройте ' + (state.currentTask.graphDirect ? 'ориентированный': 'неориентированный') +
            ' удовлетворяющий следующим условиям:' + '\n'
        state.currentTask.plugins.forEach(plugin => {
            const pluginType = plugin.plugin.pluginType
            switch (pluginType) {
                case 'CHARACTERISTIC':
                    text = text + plugin.plugin.description + ' ' + plugin.value + '\n'
                    break
                case 'PROPERTY':
                    text = text + plugin.plugin.description + ' ' + plugin.value ? 'выполнено' : 'невыполнено' + '\n'
                    break
                default:
                    break
            }
        })
        return text
    },
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
}

const mutations = {
    addPluginToCurrentTask(state, plugin) {
        const index = state.currentTask.plugins.findIndex(item => item.plugin.id === plugin.plugin.id)
        if (index > -1) {
            state.currentTask.plugins[index] = plugin
        } else {
            state.currentTask.plugins = [
                ...state.currentTask.plugins,
                plugin
            ]
        }
    },
    removePluginFormCurrentTask(state, plugin) {
        const removeIndex = state.currentTask.plugins.findIndex(item => item.plugin.id === plugin.plugin.id)

        if (removeIndex > -1) {
            state.currentTask.plugins = [
                ...state.currentTask.plugins.slice(0, removeIndex),
                ...state.currentTask.plugins.slice(removeIndex + 1)
            ]
        }
    },
    editCurrentTaskDescriptionMutation(state, description) {
        state.currentTask.description = description
    },
    editCurrentTaskNameMutation(state, name) {
        state.currentTask.name = name;
    },
    editCurrentTaskCategoryMutation(state, category) {
        state.currentTask.category = category
    },
    editCurrentTaskGraphEnableMutation(state, enable) {
        state.currentTask.graphIsPresent = enable
    },
    editCurrentTaskGraphTypeMutation(state, direct) {
        state.currentTask.graphDirect = direct
    },
    editCurrentTaskPermissionMutation(state, permission) {
        state.currentTask.permission = permission
    },
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
    cleanCurrentTaskMutation(state) {
        state.currentTask = {
            description: '',
            name: '',
            category: '',
            plugins: [],
            permission: {
                draw: true,
                color: true,
                edit: true,
                remove: true
            },
            graphDirect: true,
            graphIsPresent: false,
            graph: currentGraph.state()
        }
    },
    initCurrentTaskMutation(state, task) {
        state.currentTask.description = task.taskDescription
        state.currentTask.name = task.name
        state.currentTask.category = task.category
        state.currentTask.plugins = task.pluginValues
        state.currentTask.permission = task.taskPermission
        state.currentTask.graphDirect = task.graphType === "DIRECTED"
        state.currentTask.graphIsPresent = task.graphIsPresent
    }
}

const actions = {
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
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}