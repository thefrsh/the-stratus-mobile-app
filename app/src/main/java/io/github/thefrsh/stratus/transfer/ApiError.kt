package io.github.thefrsh.stratus.transfer

import java.util.*

class ApiError(
    val timestamp: Date,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)