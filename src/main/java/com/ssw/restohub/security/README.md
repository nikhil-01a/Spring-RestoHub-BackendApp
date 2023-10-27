JWT AUTHENTICATION PROCESS: COMPLETE FLOW

1. Add Spring Security Dependency : pom.xml
2. Create JwtAuthenticationEntryPoint : Security Package
3. Create JwtHelper : Security Package
4. Create JwtAuthenticationFilter : Security Package
5. Create JwtRequest and JwtResponse : pojo Package
6. Configure JWT in Spring Security Config : Create SecurityConfig file in 'config' package
7. Create AuthController and a 'login' API in it : Create AuthController in controllers package
8. Setup UserDetails Implementation in our Entity file: UserRole in data package
9. Setup UserDetailsService Implementation: Create UserDetailsServiceImpl in 'service' package that implements UserDetailsService
10. Implement doAuthenticate in AuthServiceImpl : in service package