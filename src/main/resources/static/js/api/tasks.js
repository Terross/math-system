import Vue from "vue";

const addTask = Vue.resource('/task/addNewTask')

export default {
    add: task => addTask.save(({}, task)),
}