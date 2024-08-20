package com.hieucoder.coderlo.mapper;

import com.hieucoder.coderlo.dto.respone.UserProfileResponse;
import com.hieucoder.coderlo.dto.respone.UserResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class UserProfileMapperImpl implements UserProfileMapper {

    @Override
    public void update(UserResponse userResponse, UserProfileResponse userProfileResponse) {
        if ( userProfileResponse == null ) {
            return;
        }

        userResponse.setId( userProfileResponse.getId() );
        userResponse.setName( userProfileResponse.getName() );
        userResponse.setBirthday( userProfileResponse.getBirthday() );
        userResponse.setEmail( userProfileResponse.getEmail() );
    }
}
