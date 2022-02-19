import Vue from "vue"
import Vuex from "vuex"
import algorithms from "./algorithms";
import tasks from "./tasks";
import constructorGraph from "./constructorGraph";
import profile from "./profile.js";

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        profile: profile,
        plugins: algorithms,
        tasks: tasks,
        constructorGraph: constructorGraph
    }
})