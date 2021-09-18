import CyElement from './components/CyElement';
import VueCytoscape from './components/Cytoscape';
export default {
    install(Vue) {
        Vue.component('cytoscape', VueCytoscape);
        Vue.component('cy-element', CyElement);
    }
};
export { VueCytoscape, CyElement };
//# sourceMappingURL=plugin.js.map