package info.esuarez.springdemoaop;

import info.esuarez.springdemoaop.account.Account;
import info.esuarez.springdemoaop.config.AppConfig;
import info.esuarez.springdemoaop.dao.AccountDAO;
import info.esuarez.springdemoaop.dao.MembershipDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class SpringDemoAopApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringDemoAopApplication.class, args);

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        AccountDAO accountDAO = context.getBean("accountDAO", AccountDAO.class);

        accountDAO.addAccount(new Account("jackson.smith", "admin"));

        MembershipDAO membershipDAO = context.getBean("membershipDAO", MembershipDAO.class);

        membershipDAO.addAccount();

        context.close();

    }

}
