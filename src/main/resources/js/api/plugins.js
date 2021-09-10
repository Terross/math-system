import Vue from "vue";

const plugins = Vue.resource('/plugin/api{/id}')

export default {
    add: plugin => plugins.save(({}, plugin)),
    update: plugin => plugins.update({id: plugin.id}, plugin),
    remove: plugin => plugins.remove({id: plugin.id})
}