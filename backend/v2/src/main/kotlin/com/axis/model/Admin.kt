package com.axis.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("admins")
class Admin {
    var adminId: String = ""
    var adminPassword: String = ""

    constructor()

    constructor(adminId: String, adminPassword: String) {
        this.adminId = adminId
        this.adminPassword = adminPassword
    }

    override fun toString(): String {
        return "Admin(adminId='$adminId', adminPassword='$adminPassword')"
    }


}