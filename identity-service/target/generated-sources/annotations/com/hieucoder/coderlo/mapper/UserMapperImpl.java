package com.hieucoder.coderlo.mapper;

import com.hieucoder.coderlo.dto.request.UserCreationRequest;
import com.hieucoder.coderlo.dto.request.UserUpdateRequest;
import com.hieucoder.coderlo.dto.respone.UserResponse;
import com.hieucoder.coderlo.entity.Role;
import com.hieucoder.coderlo.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreationRequest userCreationRequest) {
        if ( userCreationRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        Set<Role> set = userCreationRequest.getRoles();
        if ( set != null ) {
            user.roles( new LinkedHashSet<Role>( set ) );
        }
        user.userName( userCreationRequest.getUserName() );
        user.password( userCreationRequest.getPassword() );

        return user.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId( user.getId() );
        userResponse.setUserName( user.getUserName() );
        Set<Role> set = user.getRoles();
        if ( set != null ) {
            userResponse.setRoles( new LinkedHashSet<Role>( set ) );
        }

        return userResponse;
    }

    @Override
    public void updateUser(User user, UserUpdateRequest userUpdateRequest) {
        if ( userUpdateRequest == null ) {
            return;
        }

        user.setId( userUpdateRequest.getId() );
        user.setUserName( userUpdateRequest.getUserName() );
        user.setPassword( userUpdateRequest.getPassword() );
        if ( user.getRoles() != null ) {
            Set<Role> set = userUpdateRequest.getRoles();
            if ( set != null ) {
                user.getRoles().clear();
                user.getRoles().addAll( set );
            }
            else {
                user.setRoles( null );
            }
        }
        else {
            Set<Role> set = userUpdateRequest.getRoles();
            if ( set != null ) {
                user.setRoles( new LinkedHashSet<Role>( set ) );
            }
        }
    }
}
