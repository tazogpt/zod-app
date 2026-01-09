package zod.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import zod.common.security.JwtAuthenticationFilter
import zod.common.security.JwtTokenProvider

@Configuration
class SecConfig {
    @Bean
    fun jwtAuthenticationFilter(
        tokenProvider: JwtTokenProvider,
        handlerExceptionResolver: HandlerExceptionResolver,
    ): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(tokenProvider, handlerExceptionResolver)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter,
    ): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/api/auth/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                ).permitAll()
                it.requestMatchers("/api/admin/**")
                    .hasAnyRole("GOD", "SUPER", "ADMIN")
                it.requestMatchers("/api/partner/**")
                    .hasAnyRole(
                        "GOD",
                        "SUPER",
                        "ADMIN",
                        "AGENCY1",
                        "AGENCY2",
                        "AGENCY3",
                        "AGENCY4",
                        "AGENCY5",
                        "AGENCY6"
                    )
                it.anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}
