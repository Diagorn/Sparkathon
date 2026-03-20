package com.diagorn.sparkathon.auth.utils

object Messages {

    // Users

    fun userNotFoundByLogin(login: String) = "Could not find user by login $login"
    fun userNotFoundByID(id: Long) = "User not found by id $id"
    fun userCreationFailed() = "Could not save new user: person with such login or email already exists"
    fun userIdRequired() = "User ID is mandatory for this request"
    fun nullUserId() = "User ID is null"
    fun userLoggedOut() = "User is already logged out"

    // Roles

    fun roleNotFoundById(id: Long) = "Role not found by id $id"

    // Tokens

    fun tokenDoesNotExist(token: String) = "Token $token does not exist"
}