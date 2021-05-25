export default {
    label : "Hello World",
	id: "HelloWorldPlugin",
	pluginPoint: "tasklist.task.detail",
	priority: 10,
	render: (node, { api }) => {
		node.innerHTML = `Hello World!`;
	}
};