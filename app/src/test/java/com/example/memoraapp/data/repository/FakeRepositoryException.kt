package com.example.memoraapp.data.repository

class FakeRepositoryException(
    failure: RepositoryFailure
) : RuntimeException("Fake failure: $failure")