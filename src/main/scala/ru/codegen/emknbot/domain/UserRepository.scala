package ru.codegen.emknbot.domain

import ru.codegen.emknbot.domain.userinfo._

trait UserRepository[F[_]] {

    def save(user: EmknUser): F[Unit]

    def get(): F[List[EmknUser]]

    def findById(id: Id): F[Either[Throwable, EmknUser]]
}
