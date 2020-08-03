package web.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

@Repository
public class RoleDaoImpl implements RoleDao {
    private SessionFactory sessionFactory;

    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role getRoleByName(String name) {
        Session session =this.sessionFactory.getCurrentSession();

        Role role = (Role) session.createQuery("from Role where name = :nameParam")
                .setParameter("nameParam", name).getSingleResult();
        return role;
    }
}
