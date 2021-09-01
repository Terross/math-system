import Vue from "vue"
import Vuex from "vuex"
import graphsApi from 'api/graphs.js'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        graph: frontendData.graph
    },
    getters: {
        graphData: (state) => {
            let elements = [];
            let adj = state.graph.adjacencyList;
            console.log(adj)
            for (let i = 0; i < adj.length; i++) {
                elements.push(
                    {
                        group: 'nodes',
                        data: { id: adj[i].id, name: adj[i].id}
                    }
                )
                let edge = adj[i].adjacencyList;
                for (let j = 0; j < edge.length; j++) {
                    elements.push(
                        {
                            group: 'edges',
                            data: { id: edge[j].weight, source: edge[j].v, target: edge[j].w, name:edge[j].weight}
                        }
                    )
                }
            }
            return elements;
        }
    },
    mutations: {
        addVertexMutation(state, vertex) {
            console.log(state.adjacencyList)
            console.log(vertex)
            state.graph.adjacencyList = [
                ...state.graph.adjacencyList,
                vertex
            ]
        },
        updateVertexMutation(state, vertex) {

        }
    },
    actions: {
        async addVertexAction({commit, state}, vertex) {
            const result = await graphsApi.add(vertex)
            const data = await result.json()
            const index = state.graph.adjacencyList.findIndex(item => item.id === data.id)

            if (index > -1) {
                commit('updateVertexMutation', data)
            } else {
                commit('addVertexMutation', data)
            }
        }

    }
})