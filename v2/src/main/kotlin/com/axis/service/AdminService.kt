package com.axis.service

import com.axis.model.Admin
import com.axis.repo.AdminRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AdminService {

    @Autowired
    val adminRepo: AdminRepo? = null

    fun getAllAdmins(): MutableList<Admin>? {
        return adminRepo?.findAll()
    }

    fun addAdmin(admin: Admin): Admin? {
        return adminRepo?.save(admin)
    }

    fun findAdminByAdminId(adminId: String): Admin? {
        return adminRepo?.findByAdminId(adminId)
    }
}