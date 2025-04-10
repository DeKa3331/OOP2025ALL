package carrent.repositories;

import carrent.models.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserHibernateRepository implements UserRepository {
    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Query<User> query = session.createQuery("FROM User WHERE login = :login", User.class);
        query.setParameter("login", login);
        return query.uniqueResultOptional();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
    // Pozostałe metody
}
