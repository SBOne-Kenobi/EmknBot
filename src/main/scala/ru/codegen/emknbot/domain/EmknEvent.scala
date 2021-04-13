package ru.codegen.emknbot.domain

sealed trait EventType

object EventType {
    case object LectureType extends EventType

    case object SeminarType extends EventType

    case object HomeworkType extends EventType
}

trait EmknEvent {

    val eventType: EventType
}
