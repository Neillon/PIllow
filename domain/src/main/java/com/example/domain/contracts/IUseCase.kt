package com.example.domain.contracts

interface IUseCase<T, Params> {
    suspend fun execute(params: Params): T
}