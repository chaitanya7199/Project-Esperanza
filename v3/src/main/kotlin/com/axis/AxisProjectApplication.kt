package com.axis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import com.axis.filters.JwtRequestFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@SpringBootApplication
class AxisProjectApplication

fun main(args: Array<String>) {
	runApplication<AxisProjectApplication>(*args)
}
@EnableWebSecurity
internal class WebSecurityConfig : WebSecurityConfigurerAdapter() {
@Autowired
private val myUserDetailsService: UserDetailsService? = null
@Autowired
private val jwtRequestFilter: JwtRequestFilter? = null

@Bean
fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
return BCryptPasswordEncoder()
}

 @Throws(Exception::class)
 fun configureGlobal(auth: AuthenticationManagerBuilder) {
 auth.userDetailsService(myUserDetailsService).passwordEncoder(bCryptPasswordEncoder())
 }

 @Bean
 @Throws(Exception::class)
 override fun authenticationManagerBean(): AuthenticationManager {
 return super.authenticationManagerBean()
 }
 //.authorizeRequests().antMatchers("/authenticate","/*").permitAll().anyRequest().authenticated().and()
 @Throws(Exception::class)
 override fun configure(httpSecurity: HttpSecurity) {
 httpSecurity.csrf().disable()
 .authorizeRequests().antMatchers("/forms","/forms/*","/endPoints","/endPoints/*","/endPoints/*/*","/partners/*","/partner*","/products","/products/*","/carousel*","/admins").permitAll().anyRequest().authenticated().and()
 .exceptionHandling().and().sessionManagement()
 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
 httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
 }
 }

// /*	@Bean
// fun passwordEncoder(): PasswordEncoder? {
// return NoOpPasswordEncoder.getInstance()
// }