package info.esuarez.springdemoaop.dao;

import info.esuarez.springdemoaop.account.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDAO {

    public void addAccount(Account account) {
        System.out.println(getClass() + ": DOING MY DB WORK: Add Account");
    }
}
