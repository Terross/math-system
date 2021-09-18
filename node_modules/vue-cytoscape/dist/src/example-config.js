const config = {
    elements: [],
    style: [
        {
            selector: 'node',
            style: {
                'background-color': '#666',
                label: 'data(label)'
            }
        },
        {
            selector: 'edge',
            style: {
                width: 7,
                'line-color': 'blue',
                'target-arrow-color': '#aaaaaa',
                'target-arrow-shape': 'triangle'
            }
        }
    ],
    layout: {
        animate: true,
        animationDuration: 500,
        animationEasing: undefined,
        name: 'grid',
        rows: 1
    }
};
export default config;
//# sourceMappingURL=example-config.js.map