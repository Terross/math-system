import Vue from "vue";

const tasks = Vue.resource('/task{/id}')

export default {
    add: task => tasks.save(({}, task)),
    update: task => tasks.update(({id: task.id}, task)),
    remove: id => tasks.remove({id : id})
}