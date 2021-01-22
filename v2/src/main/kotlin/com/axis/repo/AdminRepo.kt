package com.axis.repo

import com.axis.model.Admin
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepo: MongoRepository<Admin, Any> {
    fun findByAdminId(adminId: String): Admin
}