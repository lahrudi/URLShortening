package urlshortener.app.repository;

import ai.grakn.redismock.RedisServer;
import com.mbdev.api.common.IdConverter;
import com.mbdev.api.repository.UrlRepository;
import com.mbdev.api.service.UrlConverterService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
public class URLRepositoryTest {
    private static RedisServer server;

    @BeforeClass
    public static void setupServer() throws IOException {
        int port = new Random().nextInt(9999 - 6000 + 1) + 6000;
        server = RedisServer.newRedisServer(port);
        server.start();
    }

    @AfterClass
    public static void shutdownServer() {
        server.stop();
    }

    @Test
    public void test_incrementID_StartsAt0AndIncrements() {
        UrlRepository urlRepository = new UrlRepository(new Jedis(server.getHost(), server.getBindPort())
                , "linkAddress");

        Long countLimitation = 10l;
        Long viewCount = 0l;

        for(int i = 0; i <= countLimitation ; i++)
        {
            viewCount = urlRepository.incrementID();
        }

        assertEquals(viewCount,countLimitation);
    }
}
