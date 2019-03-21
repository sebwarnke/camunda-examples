package org.camunda.consulting.examples.modelapi;

import java.io.File;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.Definitions;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;

public class ModelBuilder {

	private BpmnModelInstance modelInstance = null;
	private Definitions definitions = null;

	public ModelBuilder() {
		modelInstance = Bpmn.createEmptyModel();
		definitions = modelInstance.newInstance(Definitions.class);
		definitions.setTargetNamespace("http://camunda.org/examples");
		modelInstance.setDefinitions(definitions);

		Process process = modelInstance.newInstance(Process.class);
		process.setId("process");
		definitions.addChildElement(process);
	}

	public static void main(String[] args) throws Exception {

		ModelBuilder modelBuilder = new ModelBuilder();

		Process process = modelBuilder.createElement(modelBuilder.definitions, "process-with-one-task", Process.class);

		// create start event, user task and end event
		StartEvent startEvent = modelBuilder.createElement(process, "start", StartEvent.class);
		UserTask task1 = modelBuilder.createElement(process, "task1", UserTask.class);
		task1.setName("User Task");
		EndEvent endEvent = modelBuilder.createElement(process, "end", EndEvent.class);

		// create the connections between the elements
		modelBuilder.createSequenceFlow(process, startEvent, task1);
		modelBuilder.createSequenceFlow(process, task1, endEvent);

		// validate and write model to file
		Bpmn.validateModel(modelBuilder.modelInstance);
		File file = File.createTempFile("bpmn-model-api-", ".bpmn");
		Bpmn.writeModelToFile(file, modelBuilder.modelInstance);
		
		System.out.println(file.getAbsolutePath());

	}

	protected <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement, String id,
			Class<T> elementClass) {
		T element = modelInstance.newInstance(elementClass);
		element.setAttributeValue("id", id, true);
		parentElement.addChildElement(element);
		return element;
	}

	public SequenceFlow createSequenceFlow(Process process, FlowNode from, FlowNode to) {
		String identifier = from.getId() + "-" + to.getId();
		SequenceFlow sequenceFlow = createElement(process, identifier, SequenceFlow.class);
		process.addChildElement(sequenceFlow);
		sequenceFlow.setSource(from);
		from.getOutgoing().add(sequenceFlow);
		sequenceFlow.setTarget(to);
		to.getIncoming().add(sequenceFlow);
		return sequenceFlow;
	}

}
