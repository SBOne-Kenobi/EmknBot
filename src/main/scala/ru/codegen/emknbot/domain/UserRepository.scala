package ru.codegen.emknbot.domain

import ru.codegen.emknbot.domain.userinfo._

trait UserRepository[F[_]] {

    def add(user: EmknUser): F[Unit]

    def remove(userId: Id): F[Unit]

    def getAll: F[List[EmknUser]]

    def findById(id: Id): F[Either[Throwable, EmknUser]]
}
