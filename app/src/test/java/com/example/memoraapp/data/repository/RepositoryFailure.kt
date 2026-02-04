package com.example.memoraapp.data.repository

sealed class RepositoryFailure {
    object Insert : RepositoryFailure()
    object Update : RepositoryFailure()
    object Delete : RepositoryFailure()
    object GetById : RepositoryFailure()
}