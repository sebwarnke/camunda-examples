package com.camunda.consulting.engineplugin;

import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class CustomHistoryEventHandler implements HistoryEventHandler {

    public static final Logger log = LoggerFactory.getLogger(CustomHistoryEventHandler.class);

    @Override
    public void handleEvent(HistoryEvent historyEvent) {

       if (historyEvent instanceof HistoricActivityInstanceEventEntity) {
           HistoricActivityInstanceEventEntity activityInstanceEventEntity = (HistoricActivityInstanceEventEntity) historyEvent;

           if (activityInstanceEventEntity.getActivityType().equals("userTask")) {
               log.info("++++++++++++++++++++");
               log.info("UserTask detected :)");
               log.info(activityInstanceEventEntity.getEventType());
               log.info("++++++++++++++++++++");
           }
       }


    }

    @Override
    public void handleEvents(List<HistoryEvent> historyEvents) {
        for (HistoryEvent historyEvent : historyEvents) {
            handleEvent(historyEvent);
        }
    }
}
