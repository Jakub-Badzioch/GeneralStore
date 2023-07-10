package com.general.store.service

import com.general.store.mapper.UserMapper
import com.general.store.model.dao.impl.User
import com.general.store.model.dto.entityanalogue.impl.UserDTO
import com.general.store.repository.UserRepository
import com.general.store.service.email.EmailService
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserServiceSpec extends Specification {
    def emailService = Mock(EmailService)
    def passwordEncoder = Mock(PasswordEncoder)
    def userMapper = Mock(UserMapper)
    def userRepository = Mock(UserRepository)
    def userService = new UserService(userRepository, userMapper, passwordEncoder, emailService)

    def 'should register new user'() {
        given:
        def userDTO = Mock(UserDTO)
        def user = Mock(User)
        def password = "abc"
        when:
        userService.register(userDTO)
        then:
        1 * userMapper.toEntity(userDTO) >> user
        1 * user.getPassword() >> password
        1 * passwordEncoder.encode('abc') >> 'cba'
        1 * user.setPassword('cba')
        1 * userRepository.save(user) >> user
        1 * user.getFirstName() >> 'adam'
        1 * user.getLastName() >> 'kowal'
        1 * user.getEmail() >> 'email'
        1 * emailService.sendWithAttachment('email', 'Register', ['firstName':'adam', 'lastName':'kowal'], null, null)
        1 * userMapper.toDto(user) >> userDTO
        0 * _
    }
}
