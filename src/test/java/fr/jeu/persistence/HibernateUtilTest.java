
package fr.jeu.persistence;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HibernateUtilTest {

    @Test
    void shouldConnectToDatabaseAndExecuteQuery() {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            assertTrue(session.isOpen());

            Integer result = session
                    .createNativeQuery("SELECT 1", Integer.class)
                    .getSingleResult();

            assertEquals(1, result);
        }
    }
}
