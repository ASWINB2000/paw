package com.paw.service;

import com.paw.dto.UserDto;
import com.paw.model.User;

public interface UserService {
	User save(UserDto userDto);

	User findById(Long id);

	User findByIdOrThrow(Long user_id);

}
