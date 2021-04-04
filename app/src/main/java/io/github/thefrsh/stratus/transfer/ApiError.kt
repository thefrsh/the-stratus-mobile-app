package io.github.thefrsh.stratus.transfer

import java.util.*

class ApiError(var timestamp: Date,
               var status: Int,
               var error: String,
               var message: String,
               var path: String)