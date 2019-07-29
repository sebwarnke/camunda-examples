# Creating Custom History Events
## Quick Info
This plugin registers a custom history level and a custom history event producer. 

`CustomHistoryLevel` extends `HistoryLevelAudit` in order to produce events also when event type is `USER_OPERATION_LOG`. `OperationLogEventProducer` then overrides `DefaultHistoryEventProducer#createUserOperationLogEvents(UserOperationLogContext context)` to eventually only produce the event when the event notifies on a change of `TaskEntity.ASSIGNEE`, e.g. claim or unclaim. 