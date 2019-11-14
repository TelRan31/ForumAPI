package telran.java31.forum.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.java31.forum.model.UserAccount;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {

}
