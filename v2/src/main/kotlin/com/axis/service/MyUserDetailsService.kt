/*
package com.axis.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class MyUserDetailsService : UserDetailsService {

    @Autowired
    val adminService: AdminService? = null

    @Autowired
    val bCryptPasswordEncoder: BCryptPasswordEncoder? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(adminId: String): UserDetails {
        val admin = adminService?.findAdminByAdminId(adminId)
        println(admin)
        return User(admin?.adminId, admin?.adminPassword, ArrayList())
    }
}
*/
