package com.PharmacyPOs.Pharmacy_POS.user_account;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.PharmacyPOs.Pharmacy_POS.user_account.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
