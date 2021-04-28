package ru.codegen.emknbot

import ru.codegen.emknbot.domain.userinfo._
import ru.codegen.emknbot.MapUserRepository._
import ru.codegen.emknbot.domain.{EmknUser, UserRepository}

import cats.Applicative
import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters.ConcurrentMapHasAsScala

object MapUserRepository {
    trait UserRepositoryException extends Exception

    case class UserNotFound(message: String = "User not found") extends UserRepositoryException
}

class MapUserRepository[F[_] : Applicative] extends UserRepository[F] {

    private val F = Applicative[F]

    private val data = new ConcurrentHashMap[Id, EmknUser].asScala

    override def add(user: EmknUser): F[Unit] = {
        data.update(user.id, user)
        F.pure()
    }

    override def remove(userId: Id): F[Unit] = {
        data.remove(userId)
        F.pure()
    }

    override def getAll: F[List[EmknUser]] = {
        F.pure(data.values.toList)
    }

    override def getById(id: Id): F[Either[Throwable, EmknUser]] = {
        F.pure (data.get(id) match {
            case Some(x) => Right(x)
            case None => Left(UserNotFound())
        })
    }
}
