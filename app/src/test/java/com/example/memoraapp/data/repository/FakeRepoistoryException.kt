package com.example.memoraapp.data.repository

class FakeRepoistoryException(
    failure: RepositoryFailure
) : RuntimeException("Fake failure: $failure")