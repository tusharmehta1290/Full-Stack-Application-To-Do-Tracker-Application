package com.niit.UserToDoTaskService.Repository;

import com.niit.UserToDoTaskService.Domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToDoRepository extends MongoRepository<User, String>
{
}
