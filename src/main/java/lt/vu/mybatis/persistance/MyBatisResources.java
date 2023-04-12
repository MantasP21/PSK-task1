package lt.vu.mybatis.persistance;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.cdi.SessionFactoryProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class MyBatisResources {

    @Produces
    @ApplicationScoped
    @SessionFactoryProvider
    private SqlSessionFactory produceSqlSessionFactory() throws IOException {
        InputStream resource = Resources.getResourceAsStream("MyBatisConfig.xml");
        return new SqlSessionFactoryBuilder().build(resource);
    }
}

