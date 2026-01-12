package com.example.shopping.security

import com.example.shopping.service.CustomUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter (
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader?.startsWith("Bearer ") == true) {
            val token = authHeader.substring(7)
            try {
                if (jwtTokenProvider.validate(token)) {
                    val email = jwtTokenProvider.getEmail(token)
                    val userDetails = userDetailsService.loadUserByUsername(email)

                    val role = jwtTokenProvider.getRole(token)
                    val authorities = userDetails.authorities.toMutableList()
                    authorities.add(SimpleGrantedAuthority(role.name))

                    val auth = UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities
                    )
                    SecurityContextHolder.getContext().authentication = auth
                }
            } catch (e:Exception) {  // clear context if token is invalid
                SecurityContextHolder.clearContext()
            }
        }
        filterChain.doFilter(request, response)
    }
}