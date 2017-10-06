package dao.impl.mysql;

import com.kukuruznyak.bettingcompany.dao.TournamentDao;
import com.kukuruznyak.bettingcompany.dao.impl.jdbc.mysql.MySqlTournamentDaoImpl;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class MySqlTournamentDaoTest {

    private TournamentDao tournamentDao;

    @Before
    public void init() throws SQLException {
        tournamentDao = MySqlTournamentDaoImpl.getInstance();
    }

    @Test
    public void getInstance() throws Exception {
    }

    /*

        @Test
        public void getById() throws Exception {
            clientDao.getById(13L);
        }

        @Test
        public void getAll() throws Exception {
            clientDao.getAll();
        }

        @Test
        public void add() throws Exception {
            User user = new UserBuilder()
                    .buildFirstName("Client")
                    .buildLastName("Client")
                    .buildEmail("client@gmail.com")
                    .buildLogin("Client2")
                    .buildPassword("1111")
                    .buildUserRole(UserRole.CLIENT)
                    .build();
            Client client = new Client(user);
            clientDao.add(client);

        }

        @Test
        public void update() throws Exception {
            Client client = clientDao.getById(13L);
            client.setBalance(new BigDecimal(900));
            clientDao.update(client);
        }

        @Test
        public void delete() throws Exception {
            clientDao.delete(13L);
        }
    */
    @Test
    public void getTournamentsByParticipantId() {
        System.out.println(tournamentDao.getTournamentsByParticipant(1l));
    }
}

