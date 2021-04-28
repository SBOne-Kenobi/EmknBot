package ru.codegen.emknbot

import domain.userinfo._
import ru.codegen.emknbot.domain.{EmknEvent, EventStorage}

import cats.Applicative
import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters.ConcurrentMapHasAsScala

class MapEventStorage[F[_]: Applicative] extends EventStorage[F] {

    private val F = Applicative[F]

    private val data = new ConcurrentHashMap[Id, List[EmknEvent]].asScala

    override def add(userId: Id, event: EmknEvent): F[Unit] = {
        data.updateWith(userId) {
            case None => Some(List(event))
            case Some(list) => Some(event :: list)
        }
        F.pure(Unit)
    }

    override def remove(userId: Id, event: EmknEvent): F[Unit] = {
        data.updateWith(userId)(
            _.map(_.filter(_ != event))
        )
        F.pure(Unit)
    }

    override def getByUserId(id: Id): F[List[EmknEvent]] = {
        F.pure(data.getOrElse(id, List.empty))
    }
}