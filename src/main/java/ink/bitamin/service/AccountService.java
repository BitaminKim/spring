package ink.bitamin.service;

import ink.bitamin.dao.AccountMapper;
import ink.bitamin.entity.Account;
import ink.bitamin.entity.AccountExample;
import ink.bitamin.entity.BasicDO;
import ink.bitamin.entity.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;

    public List<AccountVO> getAllWithDepartment() {
        return accountMapper.selectByExampleWithDepartment(new AccountExample());
    }

    public AccountVO getAccountWithDepartment(Long id) {
        return accountMapper.selectByPrimaryKeyWithDepartment(id);
    }

    public List<Account> getAll() {
        return Arrays.asList((Account[]) accountMapper.selectByExample(new AccountExample()).toArray());
    }

    public Account getAccount(Long id) {
        return (Account) accountMapper.selectByPrimaryKey(id);
    }
}
