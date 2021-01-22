/*
package com.axis.controller

import com.axis.model.Admin
import com.axis.model.AuthenticationResponse
import com.axis.service.AdminService
import com.axis.service.MyUserDetailsService
import com.axis.util.JwtUtil
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000", "http://localhost:3001")
@RestController
class AdminController {

    @Autowired
    val adminService: AdminService? = null

    @Autowired
    val bCryptPasswordEncoder: BCryptPasswordEncoder? = null

    @Autowired
    val authenticationManager: AuthenticationManager? = null

    @Autowired
    private val jwtTokenUtil: JwtUtil? = null;

    @Autowired
    private val userDetailsService: MyUserDetailsService? = null;

    */
/*@CrossOrigin("http://localhost:3000")
    @ApiOperation(value = "Authenticate admin")
    @PostMapping("/admins")
    fun authenticateAdmin(@RequestBody adminDetails: Admin): String {
        for(admin in adminService?.getAllAdmins()!!) {
            if(admin.adminId == adminDetails.adminId && admin.adminPassword == adminDetails.adminPassword)
                return "Authentication Succeeded"
        }
        return "Authentication Failed"
    }*//*



    @CrossOrigin("http://localhost:3000", "http://localhost:3001")
    @ApiOperation(value = "Validate admin details")
    @PostMapping("/admins")
    @Throws(Exception::class)
    open fun createAuthenticationToken(@RequestBody admin: Admin): String {
        println(admin)
        try {
            println(admin)
            authenticationManager!!.authenticate(
                UsernamePasswordAuthenticationToken(admin.adminId, admin.adminPassword)
            )
        } catch (e: BadCredentialsException) {
            return("Incorrect username or password")
        }
        val userDetails = userDetailsService?.loadUserByUsername(admin.adminId!!)
        val jwt = jwtTokenUtil!!.generateToken(userDetails!!)
        if(ResponseEntity.ok(AuthenticationResponse(jwt)).statusCode.equals("403")) {
                return "Wrong Credentials"
            }
        return jwt
    }

    @CrossOrigin("http://localhost:3000")
    @ApiOperation(value = "Add admin")
    @PostMapping("/admins/add")
    fun addAdmin(@RequestBody admin: Admin): Admin? {
        println(admin.adminPassword)
        //println(bCryptPasswordEncoder?.encode("admin"))
        admin.adminPassword = bCryptPasswordEncoder?.encode(admin.adminPassword).toString()
        return adminService?.addAdmin(admin)
    }

}
*/
