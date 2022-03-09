import Vue from "vue"
import Vuex from "vuex"
import algorithms from "./algorithms";
import tasks from "./tasks";
import constructorGraph from "./constructorGraph";
import profile from "./profile.js";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

export default new Vuex.Store({
    plugins: [createPersistedState({
        paths: ['profile']
    })],
    modules: {
        profile: profile,
        plugins: algorithms,
        tasks: tasks,
        constructorGraph: constructorGraph
    }
})