package ru.codegen.emknbot.domain

trait UserRepository[T[User]] {

    def save(user: EmknUser): Unit

    def getAll: T[EmknUser]

    def findById[User](id: Int): User
}
