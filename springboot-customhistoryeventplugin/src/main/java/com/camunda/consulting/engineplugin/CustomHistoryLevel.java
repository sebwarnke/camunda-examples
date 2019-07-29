package com.camunda.consulting.engineplugin;

import org.camunda.bpm.engine.ActivityTypes;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.impl.history.HistoryLevelAudit;
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricExternalTaskLogEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricTaskInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.event.HistoryEventType;
import org.camunda.bpm.engine.impl.history.event.HistoryEventTypes;
import org.camunda.bpm.engine.impl.history.event.UserOperationLogEntryEventEntity;
import org.camunda.bpm.engine.impl.history.handler.HistoryEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CustomHistoryLevel extends HistoryLevelAudit {

  private static final Logger log = LoggerFactory.getLogger(CustomHistoryLevel.class);


  @Override
  public int getId() {
    return 42;
  }

  @Override
  public String getName() {
    return "claim-log-level";
  }

  @Override
  public boolean isHistoryEventProduced(final HistoryEventType eventType, final Object entity) {
    return super.isHistoryEventProduced(eventType,entity) || HistoryEventTypes.USER_OPERATION_LOG == eventType;
  }
}
