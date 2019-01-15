package com.example.captain;

import com.example.captain.domain.Captain;
import com.example.captain.repository.CaptainRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CaptainApplication.class})
public class CaptainTest {

    @Autowired
    private CaptainRepository repository;

    @Test
    public void crudTest() {
        Captain create = new Captain(5L, 1L, "Иванов Иван");
        repository.insert(create);

        Captain read = repository.findById(5L).get();
        assertEquals(create, read);

        repository.deleteById(5L);
        assertFalse(repository.findById(5L).isPresent());
    }

    @Test(expected = Exception.class)
    public void oneCaptainTest() {
        Captain create = new Captain(5L, 1L, "Иванов Иван");
        repository.insert(create);

        Captain create2 = new Captain(5L, 2L, "Петров Петр");
        repository.insert(create2);
    }

}
