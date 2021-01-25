package com.axis.model

import java.io.Serializable


class AuthenticationRequest : Serializable {
    var username: String? = null
    var password: String? = null

    //need default constructor for JSON Parsing
    constructor() {}
    constructor(username: String?, password: String?) {
        this.username = username
        this.password = password
    }
}