package pl.com.bottega.hrs.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.model.commands.ValidationErrors;
import pl.com.bottega.hrs.model.repositories.UserRepository;

import javax.persistence.EntityManager;

/**
 * Created by freszczypior on 2017-11-28.
 */
@Component
public class JPAUserRepository implements UserRepository {

    private EntityManager entityManager;

    public JPAUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User get(String login) {
        User user = entityManager.find(User.class, login);
        if (user == null)
            throw new NoSuchEntityException();
        return user;
    }
}
