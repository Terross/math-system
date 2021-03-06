import Vue from "vue"
import Vuex from "vuex"
import plugins from "./plugins";
import tasks from "./tasks";
import currentGraph from "./currentGraph.js";
import profile from "./profile.js";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

export default new Vuex.Store({
    plugins: [createPersistedState({
        paths: ['profile']
    })],
    modules: {
        profile: profile,
        plugins: plugins,
        tasks: tasks,
        currentGraph: currentGraph
    }
})