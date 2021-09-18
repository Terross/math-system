import { Vue } from 'vue-property-decorator';
import { VNode } from 'vue';
import { CytoscapeOptions, Core } from 'cytoscape';
export default class VueCytoscape extends Vue {
    config: CytoscapeOptions;
    preConfig: (x: any) => void;
    afterCreated: (x: any) => void;
    instance: Core | undefined;
    resolve: any;
    reject: any;
    cy: Promise<Core>;
    mounted(): void;
    render(h: (arg0: string, vnodes: VNode[]) => void, context: any): void;
}
